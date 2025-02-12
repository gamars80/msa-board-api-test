package com.example.msaboardapitest.utils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Setter
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AppLogger {
    private static final Logger logger = LoggerFactory.getLogger(AppLogger.class);
    private String uuid;
    private String requestURL;

    public void log(String message, String name) {
        logger.info("[{}] [{}] [{}] {}", uuid, requestURL, name, message);
        MDC.put("log_uuid", uuid);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        logger.info("[{}] request scope been create: {}", uuid, this);
    }

    @PreDestroy
    public void close() {
        logger.info("[{}] request scope been close: {}", uuid, this);
    }
}
