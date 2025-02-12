package com.example.msaboardapitest.config;

import com.example.msaboardapitest.annotation.ApiFor;
import com.example.msaboardapitest.enums.RoleType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class ApiForAspect {

//    @Before("@annotation(ApiFor) || @within(ApiFor)") // 메소드 또는 클래스 레벨에서 @ApiFor 처리
//    public void checkRoleAccess() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new SecurityException("User is not authenticated");
//        }
//
//        // 현재 사용자 권한 가져오기
//        var authorities = authentication.getAuthorities();
//
//        // 메소드에 선언된 @ApiFor 어노테이션 가져오기
//        MethodSignature signature = (MethodSignature) org.aspectj.lang.Signature.class.cast(null);
//        Method method = signature.getMethod();
//        ApiFor apiFor = method.getAnnotation(ApiFor.class);
//
//        if (apiFor == null) { // 클래스 레벨에 적용된 @ApiFor 확인
//            apiFor = method.getDeclaringClass().getAnnotation(ApiFor.class);
//        }
//
//        // @ApiFor에서 요구하는 역할
//        RoleType[] requiredRoles = apiFor.roles();
//
//        // 현재 사용자의 권한이 요구하는 역할 중 하나라도 포함되어 있는지 확인
//        boolean hasAccess = Arrays.stream(requiredRoles)
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                .anyMatch(authorities::contains);
//
//        if (!hasAccess) {
//            throw new SecurityException("Access denied for this API");
//        }
//    }

    @Before("@annotation(ApiFor)") // @ApiFor 어노테이션 적용된 메서드에만 실행
    public void checkRole(JoinPoint joinPoint) throws Throwable {
        // 어노테이션 정보를 가져옴
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiFor apiFor = method.getAnnotation(ApiFor.class);

        // 현재 사용자 역할 가져오기
        RoleType userRole = getCurrentUserRole();

        // 필요한 역할 목록
        RoleType[] requiredRoles = apiFor.roles();

        // 사용자 역할이 허용된 역할 목록에 포함되지 않으면 예외 처리
        if (!Arrays.asList(requiredRoles).contains(userRole)) {
            throw new SecurityException("Access denied for role: " + userRole);
        }
    }

    private RoleType getCurrentUserRole() {
        // Spring Security를 사용해 현재 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String role = ((UserDetails) principal).getAuthorities().iterator().next().getAuthority();
            return RoleType.valueOf(role); // 예: ROLE_ADMIN -> ADMIN
        }

        throw new SecurityException("User role could not be determined.");
    }
}