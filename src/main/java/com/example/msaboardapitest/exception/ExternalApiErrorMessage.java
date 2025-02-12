package com.example.msaboardapitest.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExternalApiErrorMessage {
    NO_RESPONSE("응답값이 없습니다. 잠시 후 다시 시도해주세요."),
    FAIL_API("API 통신에 실패 하였습니다."),
    NO_SEND_MSG_RESPONSE_VALUE("메세지 전송 응답값이 없습니다. 잠시 후 다시 시도해주세요."),
    NO_RS_SOLUTIONS_RESPONSE_VALUE("RS_SOLUTIONS API 응답값이 없습니다. 잠시 후 다시 시도해주세요."),
    FAILED_TO_OBTAIN_TOKEN_FROM_EXTERNAL_API("토큰 발급에 실패 하였습니다."),
    FAILED_ALIM_TALK_API("알림톡 연동에 실패 하였습니다."),
    FAILED_OPEN_API("OPEN API 연동에 실패 하였습니다.")
    ;

    private final String description;
}
