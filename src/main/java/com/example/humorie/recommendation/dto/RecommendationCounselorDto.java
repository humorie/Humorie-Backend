package com.example.humorie.recommendation.dto;

import com.example.humorie.consultant.counselor.entity.CounselingField;
import lombok.Getter;

import java.util.Set;

@Getter
public class RecommendationCounselorDto {

    private final long id;

    private final String name;

    private final Set<String> counselingFields;

    private final double rating;

    private final int reviewCount;

    public RecommendationCounselorDto(long id, String name, Set<String> counselingFields,
                                      double rating, int reviewCount){
        this.id = id;
        this.name = name;
        this.counselingFields = counselingFields;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

}
