package com.HPMS.HPMS;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // 로그인한 사용자의 권한(role) 가져오기
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        if ("ROLE_USER".equals(role)) {
            response.sendRedirect("/hpms/guest");   // 게스트 전용 페이지
        } else {
            response.sendRedirect("/"); // 기본 페이지
        }
    }

}
