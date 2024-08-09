package com.example.humorie.consult_detail.service;

import com.example.humorie.account.service.AccountService;
import com.example.humorie.consult_detail.dto.response.ConsultDetailPageDto;
import com.example.humorie.consult_detail.dto.response.LatestConsultDetailResDto;
import com.example.humorie.consult_detail.dto.response.ConsultDetailListDto;
import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.consult_detail.dto.response.SpecificConsultDetailDto;
import com.example.humorie.consult_detail.entity.ConsultDetail;
import com.example.humorie.consult_detail.repository.ConsultDetailRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultDetailService {
    private final AccountService accountService;
    private final ConsultDetailRepository consultDetailRepository;

    // 가장 최근에 받은 상담 조회
    public LatestConsultDetailResDto getLatestConsultDetailResponse(PrincipalDetails principalDetails) {
        AccountDetail accountDetail = principalDetails.getAccountDetail();
        if (accountDetail == null) {
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }
        log.info("Account ID: " + accountDetail.getId());

        // Pageable을 사용하여 결과를 하나로 제한
        List<ConsultDetail> consultDetails = consultDetailRepository.findLatestConsultDetail(accountDetail, PageRequest.of(0, 1));

        // 첫 번째 결과만 선택, 없으면 예외 발생
        ConsultDetail consultDetail = consultDetails.stream().findFirst().orElseThrow(() -> {
            log.info("No consult details found for account ID: {}", accountDetail.getId());
            return new ErrorException(ErrorCode.NO_RECENT_CONSULT_DETAIL);
        });

        return LatestConsultDetailResDto.fromEntity(consultDetail);
    }

    // 상담 내역 전체 조회
    public ConsultDetailPageDto findAllConsultDetail(PrincipalDetails principalDetails, Pageable pageable) {
        // PrincipalDetails 객체에서 AccountDetail 객체를 가져옴
        AccountDetail accountDetail = principalDetails.getAccountDetail();
        if (accountDetail == null) {
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }
        log.info("Account ID: " + accountDetail.getId());

        // Page 객체를 가져옴
        Page<ConsultDetail> consultDetails = consultDetailRepository.findAllConsultDetail(accountDetail, pageable);

        // 총 페이지 수보다 요청된 페이지 번호가 클 경우 예외 처리
        if (pageable.getPageNumber() >= consultDetails.getTotalPages()) {
            log.error("Page number {} exceeds total pages {}", pageable.getPageNumber(), consultDetails.getTotalPages());
            throw new ErrorException(ErrorCode.REQUEST_ERROR);
        }

        // 페이지가 비어 있는지 확인
        if (consultDetails.isEmpty()) {
            throw new ErrorException(ErrorCode.NO_RECENT_CONSULT_DETAIL);
        }

        // Page 객체를 ConsultDetailListDto로 변환
        Page<ConsultDetailListDto> consultDetailListDtos = consultDetails.map(ConsultDetailListDto::fromEntity);
        log.info("Requested page number (0-based): {}", pageable.getPageNumber());
        log.info("Total pages available: {}", consultDetailListDtos.getTotalPages());

        // Page 정보를 포함한 ConsultDetailPageDto로 반환
        return new ConsultDetailPageDto(consultDetailListDtos);
    }

    // 특정 상담 내역 조회
    public SpecificConsultDetailDto getSpecificConsultDetail(Long id) {
        // Spring Data JPA가 기본적으로 제공하는 메서드
        // 주어진 id에 해당하는 엔티티를 데이터베이스에서 조회
        ConsultDetail consultDetail = consultDetailRepository.findById(id)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_CONSULT_DETAIL));

        // content가 존재하면 status를 true로 변경
        if (consultDetail.getContent() != null && !consultDetail.getContent().isEmpty()) {
            consultDetail.setStatus(true);
            consultDetailRepository.save(consultDetail); // 상태 업데이트 저장
        } else {
            // content가 없을 경우 예외 발생
            throw new ErrorException(ErrorCode.CONSULT_DETAIL_NOT_COMPLETED);
        }

        return SpecificConsultDetailDto.fromEntity(consultDetail);
    }
}
