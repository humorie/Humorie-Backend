package com.example.humorie.consultant.review.service;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.jwt.JwtTokenUtil;
import com.example.humorie.account.repository.AccountRepository;
import com.example.humorie.consultant.counselor.entity.Counselor;
import com.example.humorie.consultant.counselor.repository.CounselorRepository;
import com.example.humorie.consultant.review.dto.ReviewReq;
import com.example.humorie.consultant.review.dto.ReviewRes;
import com.example.humorie.consultant.review.entity.Review;
import com.example.humorie.consultant.review.repository.ReviewRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JwtTokenUtil jwtTokenUtil;
    private final AccountRepository accountRepository;
    private final CounselorRepository counselorRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public String createReview(String accessToken, long counselorId, ReviewReq reviewReq) {
        String email = jwtTokenUtil.getEmailFromToken(accessToken);

        AccountDetail account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_USER));

        Counselor counselor = counselorRepository.findById(counselorId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NON_EXIST_COUNSELOR));


        Review review = Review.builder()
                .title(reviewReq.getTitle())
                .content(reviewReq.getContent())
                .rating(reviewReq.getRating())
                .createdAt(LocalDateTime.now())
                .account(account)
                .counselor(counselor)
                .build();

        reviewRepository.save(review);

        counselor.setReviewCount(counselor.getReviewCount() + 1);

        List<Review> reviews = reviewRepository.findByCounselorId(counselorId);
        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        counselor.setRating(averageRating);
        counselorRepository.save(counselor);

        return "Creation Success";
    }

    @Transactional
    public String modifyReview(String accessToken, long reviewId, ReviewReq reviewReq) {
        String email = jwtTokenUtil.getEmailFromToken(accessToken);

        AccountDetail account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_USER));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_REVIEW));

        if(!review.getAccount().equals(account)) {
            throw new ErrorException(ErrorCode.REVIEW_PERMISSION_DENIED);
        } else {
            if(reviewReq.getTitle() != null && !reviewReq.getTitle().isEmpty()) {
                review.setTitle(reviewReq.getTitle());
            }
            if(reviewReq.getContent() != null && !reviewReq.getContent().isEmpty()) {
                review.setContent(reviewReq.getContent());
            }
            if(reviewReq.getRating() != null) {
                review.setRating(reviewReq.getRating());
            }

            reviewRepository.save(review);
        }

        return "Modification Success";
    }

    @Transactional
    public String deleteReview(String accessToken, long reviewId) {
        String email = jwtTokenUtil.getEmailFromToken(accessToken);

        AccountDetail account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_USER));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_REVIEW));

        if(!review.getAccount().equals(account)) {
            throw new ErrorException(ErrorCode.REVIEW_PERMISSION_DENIED);
        } else {
            reviewRepository.deleteById(reviewId);
        }

        return "Deletion Success";
    }

    public List<ReviewRes> getReviewListByCounselor(long counselorId) {
        List<Review> reviews = reviewRepository.findByCounselorId(counselorId);

        return reviews.stream()
                .sorted(Comparator.comparingDouble(Review::getRating).reversed())
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ReviewRes convertToDto(Review review) {
        return ReviewRes.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }

}
