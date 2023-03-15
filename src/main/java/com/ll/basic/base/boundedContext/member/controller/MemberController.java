package com.ll.basic.base.boundedContext.member.controller;
import com.ll.basic.base.logData.LogData;
import com.ll.basic.base.boundedContext.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class MemberController {
    private final MemberService memberService;
    public MemberController() {
        memberService = new MemberService();
    }
    @GetMapping("/member/login")
    @ResponseBody
    public LogData login(String username, String password) {
        if ( username == null || username.trim().length() == 0 ) {
            return LogData.of("F-3", "username(을)를 입력해주세요.");
        }

        if ( password == null || password.trim().length() == 0 ) {
            return LogData.of("F-4", "password(을)를 입력해주세요.");
        }

        return memberService.tryLogin(username, password);
    }
}