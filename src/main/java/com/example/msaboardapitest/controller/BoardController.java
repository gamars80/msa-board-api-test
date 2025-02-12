package com.example.msaboardapitest.controller;

import com.example.msaboardapitest.annotation.ApiFor;
import com.example.msaboardapitest.enums.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/board")
@Slf4j
public class BoardController {
    @ApiFor(roles = RoleType.ADMIN)
    @GetMapping("")
    public String welcome() {
        return "Welcome Board API Server";
    }

    @ApiFor(roles = RoleType.CUSTOMER)
    @GetMapping("/welcome2")
    public String welcome2() {
        return "Welcome Board API Server";
    }
}
