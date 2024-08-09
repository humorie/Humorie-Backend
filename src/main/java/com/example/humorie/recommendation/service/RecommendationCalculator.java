//package com.example.humorie.recommendation.service;
//
//
//import com.example.humorie.consultant.counselor.entity.CounselingField;
//import com.example.humorie.consultant.counselor.entity.Counselor;
//import com.example.humorie.consultant.review.entity.Review;
//import com.example.humorie.reservation.entity.Reservation;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//public class RecommendationCalculator {
//
//    private final Double RECOMMENDATION_RATING_BY_REVIEW_COUNT = 0.02; // 리뷰 수에 따른 추천도
//    private final Double RECOMMENDATION_RATING_BY_COUNSELING_COUNT = 0.01; // 상담 수에 따른 추천도
//    private final Double RECOMMENDATION_RATING_BY_SAME_COUNSELOR = 0.05; // 같은 상담원에 따른 추천도
//    private final Double RECOMMENDATION_RATING_BY_SAME_FIELD = 0.05; // 같은 상담 분야에 따른 추천도
//
//    private final Double RECOMMENDATION_RATING_BY_RECOMMENDATION_COUNT = 0.03; // 리뷰 추천 수에 따른 추천도
//
//    public double calculateCounselorRecommendRatingWithoutLogin(Counselor counselor) {
//        return CounselorBaseRating(counselor);
//    }
//
//    public double calculateCounselorRecommendRatingWithLogin(Counselor counselor, List<Reservation> reservations) {
//        double baseRating = CounselorBaseRating(counselor);
//        double additionalRating = CounselorAdditionalRating(counselor, reservations);
//        return baseRating + additionalRating;
//    }
//
//    public double calculateReviewRecommendRatingWithoutLogin(Review review) {
//        return ReviewBaseRating(review);
//    }
//
//    public double calculateReviewRecommendRatingWithLogin(Review review, List<Reservation> reservations) {
//        double baseRating = ReviewBaseRating(review);
//        double additionalRating = ReviewAdditionalRating(review, reservations);
//        return baseRating + additionalRating;
//    }
//
//    private double CounselorBaseRating(Counselor counselor) {
//        return counselor.getRating()
//                + counselor.getReviewCount() * RECOMMENDATION_RATING_BY_REVIEW_COUNT
//                + counselor.getCounselingCount() * RECOMMENDATION_RATING_BY_COUNSELING_COUNT;
//        // 기본 평점에 리뷰 수와 상담 수를 기반으로 노출 점수 책정
//    }
//
//    private double CounselorAdditionalRating(Counselor counselor, List<Reservation> reservations) {
//        Set<CounselingField> counselingFields = reservations.stream()
//                .map(Reservation::getCounselor)
//                .flatMap(reservedCounselor -> reservedCounselor.getCounselingFields().stream())
//                .collect(Collectors.toSet());
//
//        return reservations.stream()
//                .mapToDouble(reservation -> {
//                    double rating = 0;
//                    if (reservation.getCounselor().getId() == counselor.getId()) {
//                        rating += RECOMMENDATION_RATING_BY_SAME_COUNSELOR; // 상담 전적과 같은 상담사 노출 점수 증가
//                    }
//                    rating += counselingFields.stream()
//                            .filter(counselor.getCounselingFields()::contains)
//                            .count() * RECOMMENDATION_RATING_BY_SAME_FIELD; // 상담 전적과 같은 상담 분야 상담사 노출 점수 증가
//                    return rating;
//                })
//                .sum();
//    }
//
//    private double ReviewBaseRating(Review review) {
//        return review.getRating()
//                + review.getRecommendationCount() * RECOMMENDATION_RATING_BY_RECOMMENDATION_COUNT;
//        // 기본적으로 평점 좋고 추천 수 높은 리뷰 노출 점수 증가
//    }
//
//    private double ReviewAdditionalRating(Review review, List<Reservation> reservations) {
//        Set<CounselingField> counselingFields = reservations.stream()
//                .map(Reservation::getCounselor)
//                .flatMap(reservedCounselor -> reservedCounselor.getCounselingFields().stream())
//                .collect(Collectors.toSet());
//
//        return reservations.stream()
//                .mapToDouble(reservation -> {
//                    double rating = 0;
//                    if (reservation.getCounselor().getId() == review.getCounselor().getId()) {
//                        rating += RECOMMENDATION_RATING_BY_SAME_COUNSELOR; // 상담 전적과 같은 상담사 리뷰 노출 점수 증가
//                    }
//                    rating += counselingFields.stream()
//                            .filter(review.getCounselor().getCounselingFields()::contains)
//                            .count() * RECOMMENDATION_RATING_BY_SAME_FIELD; // 상담 전적과 같은 상담 분야 상담사 리뷰 노출 점수 증가
//                    return rating;
//                })
//                .sum();
//    }
//}
