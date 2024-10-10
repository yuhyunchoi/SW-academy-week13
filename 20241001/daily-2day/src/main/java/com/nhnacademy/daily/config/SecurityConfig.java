package com.nhnacademy.daily.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RedisTemplate<String, UserDetails> redisTemplate) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/private-project/**").hasAnyRole("MEMBER", "GOOGLE_USER")
                        .antMatchers("/public-project/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oauth2UserService(redisTemplate))
                        )
                )
                .formLogin(withDefaults())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("SESSION")
                        .logoutSuccessUrl("/public-projects")
                );

        return http.build();
    }

    // 구글 로그인을 위한 OAuth2 사용자 서비스 설정
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oauth2UserService(RedisTemplate<String, UserDetails> redisTemplate) {
        return userRequest -> {
            OidcUser oidcUser = (OidcUser) new DefaultOidcUserService().loadUser(userRequest);

            String email = oidcUser.getEmail();
            if (!email.endsWith("@example.com")) { // 특정 도메인 이메일만 허용
                throw new OAuth2AuthenticationException("Unauthorized email domain");
            }

            // 구글 로그인 사용자는 ROLE_GOOGLE_USER 권한을 부여
            List<GrantedAuthority> authorities = new ArrayList<>(oidcUser.getAuthorities());
            authorities.add(new SimpleGrantedAuthority("ROLE_GOOGLE_USER"));

            UserDetails googleUser = User.withUsername(email)
                    .authorities(authorities)
                    .password("") // 비밀번호는 사용하지 않음
                    .build();

            // Redis에 사용자 정보 저장
            redisTemplate.opsForValue().set(email, googleUser);

            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        };
    }

    @Bean
    public UserDetailsService userDetailsService(RedisTemplate<String, UserDetails> redisTemplate) {
        return username -> {
            UserDetails user = redisTemplate.opsForValue().get(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return user;
        };
    }
}
