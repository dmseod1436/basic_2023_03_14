package com.ll.basic.base.boundedContext.member.controller;

import com.ll.basic.base.rq.Rq;
import com.ll.basic.base.logData.LogData;
import com.ll.basic.base.boundedContext.member.entity.Member;
import com.ll.basic.base.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/login")
    @ResponseBody
    public LogData login(String username, String password, HttpServletRequest req, HttpServletResponse resp) {
        Rq rq = new Rq(req, resp);

        if (username == null || username.trim().length() == 0) {
            return LogData.of("F-3", "username(을)를 입력해주세요.");
        }

        if (password == null || password.trim().length() == 0) {
            return LogData.of("F-4", "password(을)를 입력해주세요.");
        }

        LogData rsData = memberService.tryLogin(username, password);

        if (rsData.isSuccess()) {
            Member member = (Member) rsData.getData();
            rq.setCookie("loginedMemberId", member.getId());
        }

        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public LogData logout(HttpServletRequest req, HttpServletResponse resp) {
        Rq rq = new Rq(req, resp);
        boolean cookieRemoved = rq.removeCookie("loginedMemberId");

        if (cookieRemoved == false){
            return LogData.of("S-2", "이미 로그아웃 상태입니다.");
        }

        return LogData.of("S-1", "로그아웃 되었습니다.");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public LogData showMe(HttpServletRequest req, HttpServletResponse resp){
        Rq rq = new Rq(req, resp);

        long loginedMemberId = rq.getCookieAsLong("loginedMemberId", 0);

        boolean isLogined = loginedMemberId > 0;

        if (isLogined == false)
            return LogData.of("F-1", "로그인 후 이용해주세요.");

        Member member = memberService.findById(loginedMemberId);

        return LogData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
    }
}