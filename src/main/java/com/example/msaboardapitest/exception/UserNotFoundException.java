package com.example.msaboardapitest.exception;

import java.time.LocalDate;

public class UserNotFoundException extends NotFoundException {

    private static final String ERROR_CODE = "USER_NOT_FOUND";
    private static final String SERVER_MESSAGE = "존재하지 않는 유저 조회";
    private static final String CLIENT_MESSAGE = "사용자를 찾지 못했습니다.";

    public UserNotFoundException(final Long id) {
        super(String.format("%s -> user id: %d", SERVER_MESSAGE, id),
                "회원님의 계정이 탈퇴 처리됐어요.\n" +
                        "서비스 이용이 필요하다면 다시 회원가입해 주세요.",
                ERROR_CODE);
    }

    public UserNotFoundException(String phoneNumber) {
        super(String.format("%s -> phoneNumber : %s", SERVER_MESSAGE, phoneNumber), CLIENT_MESSAGE, ERROR_CODE);
    }

    public UserNotFoundException(String name, LocalDate birth, String phoneNumber) {
        super(String.format("%s -> name : %s, birth : %s, phoneNumber : %s", SERVER_MESSAGE, name, birth, phoneNumber), CLIENT_MESSAGE, ERROR_CODE);
    }
}
