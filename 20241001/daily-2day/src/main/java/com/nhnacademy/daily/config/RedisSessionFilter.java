package com.nhnacademy.daily.config;

import com.nhnacademy.daily.auth.AcademyUser;
import com.nhnacademy.daily.model.Member;
import com.nhnacademy.daily.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class RedisSessionFilter extends OncePerRequestFilter {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RedisTemplate<String, Object> sessionRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                }
            }
        }

        if (sessionId != null) {
            String username = (String) sessionRedisTemplate.opsForValue().get(sessionId);
            if (username != null) {
                Member member = memberService.getMember(username);
                AcademyUser academyUser = new AcademyUser(member);
                Authentication auth = new PreAuthenticatedAuthenticationToken(academyUser, null, academyUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
