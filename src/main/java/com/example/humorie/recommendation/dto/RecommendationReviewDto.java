package com.example.humorie.recommendation.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecommendationReviewDto {

    private final long id;

    private final String content;

    private final double rating;

    private final int recommendationCount;

    private final LocalDateTime createdAt;

    private final String accountName;

    public RecommendationReviewDto(long id, String content, double rating, int recommendationCount, LocalDateTime createdAt, String accountName) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.recommendationCount = recommendationCount;
        this.createdAt = createdAt;
        this.accountName = accountName;
    }
}
