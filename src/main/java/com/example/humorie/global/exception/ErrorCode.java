package com.example.humorie.global.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 1000 : 요청 성공
     */

    SUCCESS(true, 1000, "요청에 성공했습니다."),


    /**
     * 2000 : Request 오류
     */

    // common
    REQUEST_ERROR(false, 2000, "잘못된 입력입니다."),

    // user
    EMPTY_JWT(false, 2001, "토큰을 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 토큰입니다."),
    NONE_EXIST_USER(false, 2003, "존재하지 않는 사용자입니다."),
    ID_EXISTS(false, 2004, "중복된 아이디입니다."),
    PASSWORD_MISMATCH(false, 2005, "비밀번호가 일치하지 않습니다."),
    INVALID_EMAIL(false, 2006, "잘못된 이메일 형식입니다."),
    INVALID_PASSWORD(false, 2007, "잘못된 비밀번호 형식입니다. 비밀번호는 최소 8자 이상 16자 이하이며, 적어도 하나의 숫자와 알파벳, 특수문자가 포함되어야 합니다."),
    INVALID_ID(false, 2008, "잘못된 아이디 형식입니다. 아이디는 최소 6자 이상이어야 하며, 소문자 알파벳과 숫자로 구성되어야 합니다."),
    SEND_EMAIL_FAILED(false, 2009, "이메일 전송에 실패했습니다."),
    REVIEW_PERMISSION_DENIED(false, 2010, "본인이 작성한 리뷰만 수정, 삭제할 수 있습니다."),
    INVALID_NAME(false, 2011, "잘못된 이름 형식입니다."),
    REVIEW_PERMISSION_DENIED(false, 2012, "본인이 작성한 리뷰만 수정, 삭제할 수 있습니다."),
    INVALID_NAME(false, 2013, "잘못된 이름 형식입니다."),
    EMPTY_NAME(false, 2014, "이름을 입력해주세요"),
    EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요"),
    EMPTY_PASSWORD(false, 2016, "비밀번를 입력해주세요"),

    /**
     * 3000 : Response 오류
     */
    // counselor
    NON_EXIST_COUNSELOR(false, 3001, "존재하지 않는 상담사입니다."),
    BOOKMARK_EXISTS(false, 3002, "해당 상담사에 대한 북마크가 이미 존재합니다."),
    NONE_EXIST_BOOKMARK(false, 3003, "존재하지 않는 북마크입니다."),
    SEARCH_FAILED(false, 3004, "상담사에 대한 검색을 실패했습니다."),
    FAILED_PAYMENT(false, 3005, "결제 실패"),

    // consult_detail
    NONE_EXIST_CONSULT_DETAIL(false, 3006, "존재하지 않는 상담 내역입니다."),
    CONSULT_DETAIL_NOT_COMPLETED(false, 3007, "상담 내용을 작성하고 있는 중이에요")
      
    NONE_EXIST_RESERVATION(false, 3008, "존재하지 않는 예약입니다."),
    INCOMPLETE_PAYMENT(false, 3009, "완료되지 않은 결제입니다."),
    SUSPECTED_PAYMENT_FORGERY(false, 3010, "위변조 의심 결제입니다."),
    EXCEED_POINT(false, 3011, "포인트가 초과되었습니다."),
    NONE_EXIST_REVIEW(false, 3012, "존재하지 않는 리뷰 입니다.");

    private final boolean isSuccess;

    private final int code;

    private final String message;

}
