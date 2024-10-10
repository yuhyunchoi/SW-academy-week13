package com.nhnacademy.demo.auth;

import com.nhnacademy.demo.domain.Member;
import com.nhnacademy.demo.service.MemberService;
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
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("SESSIONID".equals(cookie.getName())){
                    sessionId = cookie.getValue();
                }
            }
        }
        if(sessionId != null){
            Object o = sessionRedisTemplate.opsForValue().get(sessionId);
            String username = (String) o;
            if(username != null){
                Member baek = memberService.getMember(username);
                AcademyUser academyUser = new AcademyUser(baek);
                Authentication auth  = new PreAuthenticatedAuthenticationToken(academyUser, null, academyUser.getAuthorities());
                auth.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
