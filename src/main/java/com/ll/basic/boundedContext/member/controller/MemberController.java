package com.ll.basic.boundedContext.member.controller;

import com.ll.basic.base.rq.Rq;
import com.ll.basic.base.logData.LogData;
import com.ll.basic.boundedContext.member.entity.Member;
import com.ll.basic.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/member/login")
    public String showLogin() {
        return "usr/member/login";
    }

    @PostMapping("/member/login")
    @ResponseBody
    public LogData login(String username, String password) {
        if (username == null || username.trim().length() == 0) {
            return LogData.of("F-3", "username(을)를 입력해주세요.");
        }

        if (password == null || password.trim().length() == 0) {
            return LogData.of("F-4", "password(을)를 입력해주세요.");
        }

        LogData logData = memberService.tryLogin(username, password);

        if (logData.isSuccess()) {
            Member member = (Member) logData.getData();
            rq.setSession("loginedMemberId", member.getId());
        }

        return logData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public LogData logout() {
        boolean cookieRemoved = rq.removeSession("loginedMemberId");

        if (cookieRemoved == false) {
            return LogData.of("S-2", "이미 로그아웃 상태입니다.");
        }

        return LogData.of("S-1", "로그아웃 되었습니다.");
    }

    @GetMapping("/member/me")
    public String showMe(Model model) {
        long loginedMemberId = rq.getLoginedMemberId();

        Member member = memberService.findById(loginedMemberId);

        model.addAttribute("member", member);

        return "usr/member/me";
    }

    // 디버깅용 함수
    @GetMapping("/member/session")
    @ResponseBody
    public String showSession() {
        return rq.getSessionDebugContents().replaceAll("\n", "<br>");
    }
}