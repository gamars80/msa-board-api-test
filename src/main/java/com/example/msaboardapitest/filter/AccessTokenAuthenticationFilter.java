//package com.example.msaboardapitest.filter;
//
//import com.example.msaboardapitest.dto.AccountFindResponse;
//import com.example.msaboardapitest.dto.AuthenticatedUser;
//import com.example.msaboardapitest.exception.UserNotFoundException;
//import com.example.msaboardapitest.provider.AccessTokenProvider;
//import com.example.msaboardapitest.provider.dto.AccessToken;
//import com.example.msaboardapitest.provider.dto.AccessTokenContent;
//import com.example.msaboardapitest.provider.dto.AccessTokenValidationResult;
//import com.example.msaboardapitest.provider.dto.JwtTokenErrorResponse;
//import com.example.msaboardapitest.service.AccountFindService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.JwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Optional;
//import java.util.regex.Pattern;
//
//import static org.springframework.util.StringUtils.hasText;
//
//@Component
//@RequiredArgsConstructor
//public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {
//    private final AccessTokenProvider accessTokenProvider;
//    private final AccountFindService accountFindService;
//
//    private Optional<AccessToken> extractTokenFromBearerToken(String authorization) {
//        if (hasText(authorization) && Pattern.matches("^Bearer .*", authorization)) {
//            String value = authorization.replaceAll("^Bearer( )*", "");
//
//            return hasText(value) ? Optional.of(new AccessToken(value)) : Optional.empty();
//        }
//
//        return Optional.empty();
//    }
//
//    private Authentication getAuthentication(AccessTokenContent content, Boolean isManager) {
////        AccountFindResponse account =
////                Boolean.TRUE.equals((isManager)) ?
////                        accountFindService.findAdminAccountByUserId(content.getUserId()) :
////                        accountFindService.findAccountByUserId(content.getUserId());
//
//        AccountFindResponse account = accountFindService.findAccountByUserId(content.getUserId());
//
//        AuthenticatedUser authenticatedUser = new AuthenticatedUser(account);
//        return new UsernamePasswordAuthenticationToken(authenticatedUser, "", authenticatedUser.getAuthorities());
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            Optional<AccessToken> optionalAccessToken = extractTokenFromBearerToken(request.getHeader(HttpHeaders.AUTHORIZATION));
//            if (optionalAccessToken.isPresent()) {
//                AccessToken accessToken = optionalAccessToken.get();
//                AccessTokenValidationResult validationResult = accessTokenProvider.validate(accessToken);
//                if (validationResult.isFailed()) {
//                    throw new JwtException(validationResult.getFailedMessage());
//                }
//                AccessTokenContent content = accessTokenProvider.getContent(accessToken);
//                Boolean isManager = (Boolean) accessTokenProvider.getClaimValue(accessToken, "isManager")
//                        .orElseGet((() -> Boolean.FALSE));
//
//                Authentication authentication = getAuthentication(content, isManager);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//            filterChain.doFilter(request, response);
//        } catch (UserNotFoundException uex) {
//            setErrorResponse(HttpStatus.UNAUTHORIZED, response, uex.getClientMessage());
//        } catch (IllegalArgumentException | JwtException ex) {
//            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex.getMessage());
//        }
//    }
//
//    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String errorMessage) throws IOException {
//        response.setStatus(status.value());
//        response.setContentType("application/json; charset=UTF-8");
//        response.getWriter().write(new ObjectMapper().writeValueAsString(JwtTokenErrorResponse.from(errorMessage)));
//    }
//}
