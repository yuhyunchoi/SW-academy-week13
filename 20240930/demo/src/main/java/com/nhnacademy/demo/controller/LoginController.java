package com.nhnacademy.demo.controller;

import com.nhnacademy.demo.domain.Member;
import com.nhnacademy.demo.domain.MemberLoginRequest;
import com.nhnacademy.demo.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String loginPage() {
        return "login";
    }
    @PostMapping
    public ModelAndView processLogin(@ModelAttribute MemberLoginRequest loginRequest)  {
        Member memberResponse = memberService.login(loginRequest);
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("loginName", memberResponse.getName());
        return mav;
    }
}
