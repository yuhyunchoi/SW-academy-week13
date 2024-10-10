package com.nhnacademy.daily.auth;

import com.nhnacademy.daily.model.Member

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class AcademyUser implements UserDetails {
    private String memberName;
    private String password;
    private String role;

    public AcademyUser(Member member) {
        this.memberName = member.getName();
        this.password = member.getPassword();
        this.role = member.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        String role = "ROLE_" + this.role;
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return memberName;
    }
}
