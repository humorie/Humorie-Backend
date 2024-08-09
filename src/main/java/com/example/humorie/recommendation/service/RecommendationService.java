package com.example.humorie.recommendation.service;

import com.example.humorie.account.jwt.PrincipalDetails;

import com.example.humorie.consultant.counselor.entity.CounselingField;
import com.example.humorie.consultant.counselor.entity.Counselor;
import com.example.humorie.consultant.counselor.repository.CounselorRepository;
import com.example.humorie.consultant.review.entity.Review;
import com.example.humorie.consultant.review.repository.ReviewRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.example.humorie.recommendation.dto.RecommendationCounselorDto;
import com.example.humorie.recommendation.dto.RecommendationReviewDto;
import com.example.humorie.reservation.entity.Reservation;
import com.example.humorie.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final CounselorRepository counselorRepository;
    private final ReviewRepository reviewRepository;



    public List<RecommendationCounselorDto> recommendCounselor(String symptomTag){
        List<Counselor> counselors = counselorRepository.findTop4ByRating(symptomTag);
        return getRecommendCounselors(counselors);
    }

    public List<RecommendationReviewDto> recommendReview(String symptomTag){
        List<Counselor> counselors = counselorRepository.findTop4ByRating(symptomTag);
        List<Review> reviews = new ArrayList<>();

        for(Counselor counselor : counselors){
            Review review = reviewRepository.findTopRatingByCounselorId(counselor.getId())
                    .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_REVIEW));

            reviews.add(review);
        }

       return getRecommendReviews(reviews);
    }


    private List<RecommendationCounselorDto> getRecommendCounselors(List<Counselor> counselors) {
        return counselors.stream()
                .map(counselor -> new RecommendationCounselorDto(
                        counselor.getId(),
                        counselor.getName(),
                        counselor.getCounselingFields().stream()
                                .map(CounselingField::getField)
                                .collect(Collectors.toSet()),
                        counselor.getRating(),
                        counselor.getReviewCount()))
                .collect(Collectors.toList());
    }

//    private List<RecommendationCounselorDto> getRecommendCounselorsWithLogin(List<Counselor> counselors, List<Reservation> reservations) {
//        return counselors.stream()
//                .map(counselor -> new RecommendationCounselorDto(counselor.getId(),
//                        counselor.getName(),
//                        counselor.getCounselingFields().stream()
//                                .map(CounselingField::getField)
//                                .collect(Collectors.toSet()),
//                        counselor.getRating(),
//                        counselor.getReviewCount(),
//                        calculator.calculateCounselorRecommendRatingWithLogin(counselor, reservations)))
//                .sorted(Comparator.comparing(RecommendationCounselorDto::getRecommendationRating).reversed())
//                .collect(Collectors.toList());
//    }

    private List<RecommendationReviewDto> getRecommendReviews(List<Review> reviews) {
        return reviews.stream()
                .map(review -> new RecommendationReviewDto(review.getId(),
                        review.getContent(),
                        review.getRating(),
                        review.getRecommendationCount(),
                        review.getCreatedAt(),
                        review.getAccount().getAccountName()))
                .collect(Collectors.toList());
    }

//    private List<RecommendationReviewDto> getRecommendReviewsWithLogin(List<Review> reviews, List<Reservation> reservations) {
//        return reviews.stream()
//                .map(review -> new RecommendationReviewDto(review.getId(),
//                        review.getContent(),
//                        review.getRating(),
//                        review.getRecommendationCount(),
//                        review.getCreatedAt(),
//                        review.getAccount().getAccountName(),
//                        calculator.calculateReviewRecommendRatingWithLogin(review, reservations)))
//                .sorted(Comparator.comparing(RecommendationReviewDto::getRecommendationRating).reversed())
//                .collect(Collectors.toList());
//    }


}
