package com.example.msaboardapitest.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleType {
    ALL("모든 접속자"),
    CUSTOMER("이용자"),
    ADMIN("관리자"),
    SUPER_ADMIN("수퍼관리자");
    private final String description;
}
