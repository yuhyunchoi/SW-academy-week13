package com.nhnacademy.demo.controller;

import com.nhnacademy.demo.domain.*;
import com.nhnacademy.demo.domain.annotation.Auth;
import com.nhnacademy.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/name")
    public String getName(){
        return "신건영";
    }

    @GetMapping("/me")
    public Member getMe(){
        return new Member("mandoo","신건영", 20, ClassType.A, Role.ADMIN);
    }

    @PostMapping("/members")
    public ResponseEntity addMember(@RequestBody MemberCreateCommand memberCreateCommand,
                                    @Auth Requester requester){
        memberService.createMember(memberCreateCommand);
        System.out.println(memberCreateCommand);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/members")
    public List<Member> getMembers(){
        return memberService.getMembers();
    }

    @GetMapping("/members/{memberId}")
    public Member getMembers(@PathVariable String memberId){
        return memberService.getMember(memberId);
    }

    @PutMapping("/members/{memberId}")
    public Member updateMember(@PathVariable String memberId){
        return memberService.updateMember(memberId);
    }
}