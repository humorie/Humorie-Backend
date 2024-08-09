package com.example.humorie.consult_detail.controller;

import com.example.humorie.account.service.AccountService;
import com.example.humorie.consult_detail.dto.response.ConsultDetailPageDto;
import com.example.humorie.consult_detail.dto.response.LatestConsultDetailResDto;
import com.example.humorie.consult_detail.dto.response.SpecificConsultDetailDto;
import com.example.humorie.consult_detail.service.ConsultDetailService;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.humorie.account.jwt.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/consult-detail")
@RequiredArgsConstructor
@Slf4j
public class ConsultDetailController {
    private final AccountService accountService;
    private final ConsultDetailService consultDetailService;

    // 가장 최근에 받은 상담 조회
    @GetMapping("/latest")
    @Operation(summary = "가장 최근에 받은 상담 조회")
    public LatestConsultDetailResDto getLatestConsultDetail(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return consultDetailService.getLatestConsultDetailResponse(principalDetails);
    }

    // 상담 내역 전체 조회
    @GetMapping("/list")
    @Operation(summary = "전체 상담 내역 조회")
    public ConsultDetailPageDto getConsultDetails(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(defaultValue = "0") int page, // 페이지 번호를 0부터 시작
            @RequestParam(defaultValue = "9") int size
    ) {
        // 페이지 번호와 크기에 대한 유효성 검사
        if (page < 0 || size < 1 || size > 9) {
            // Size must be between 1 and 9
            throw new ErrorException(ErrorCode.REQUEST_ERROR);
        }

        Pageable pageable = PageRequest.of(page, size);
        return consultDetailService.findAllConsultDetail(principalDetails, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 상담 내역 조회")
    public Map<String, Object> getSpecificConsultDetail(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long id
    ) {
        // 사용자 아이디를 가져옴
        String accountName = principalDetails.getAccountDetail().getAccountName();
        log.info("User Account Name: {}", accountName);

        // 사용자 아이디에 별표 처리 추가
        String maskedAccountName = maskAccountName(accountName);

        // 상담 내역 DTO를 가져옴
        SpecificConsultDetailDto consultDetail = consultDetailService.getSpecificConsultDetail(id);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("accountName", maskedAccountName);
        response.put("consultDetail", consultDetail);

        return response;
    }

    // 사용자 아이디 별표 처리 메서드
    private String maskAccountName(String accountName) {
        // accountName이 null이거나 길이가 1 이하일 경우 그대로 반환
        if (accountName == null || accountName.length() <= 3) {
            return accountName;
        }
        int length = accountName.length();
        StringBuilder maskedName = new StringBuilder(accountName.substring(0, 3));
        for (int i = 3; i < length; i++) {
            maskedName.append('*');
        }
        return maskedName.toString();
    }
}
