package com.example.humorie.consult_detail.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ConsultDetailPageDto {
    private final List<ConsultDetailListDto> consultDetails;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;

    public ConsultDetailPageDto(Page<ConsultDetailListDto> consultDetailPage) {
        this.consultDetails = consultDetailPage.getContent(); // 현재 페이지에 있는 데이터 목록
        this.pageNumber = consultDetailPage.getNumber(); // 현재 페이지 번호
        this.pageSize = consultDetailPage.getSize(); // 한 페이지에 표시되는 항목 수
        this.totalElements = consultDetailPage.getTotalElements(); // 전체 항목 수
        this.totalPages = consultDetailPage.getTotalPages(); // 총 페이지 수
    }

    public static ConsultDetailPageDto from(Page<ConsultDetailListDto> consultDetailPage) {
        return new ConsultDetailPageDto(consultDetailPage);
    }
}
