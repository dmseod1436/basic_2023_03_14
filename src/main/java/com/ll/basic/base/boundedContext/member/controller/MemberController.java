package com.ll.basic.base.boundedContext.member.controller;
import com.ll.basic.base.logData.LogData;
import com.ll.basic.base.boundedContext.member.entity.Member;
import com.ll.basic.base.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Arrays;
@Controller
public class MemberController {
    private final MemberService memberService;

    // 생성자 주입
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/login")
    @ResponseBody
    public LogData login(String username, String password, HttpServletResponse resp) {
        if (username == null || username.trim().length() == 0) {
            return LogData.of("F-3", "username(을)를 입력해주세요.");
        }

        if (password == null || password.trim().length() == 0) {
            return LogData.of("F-4", "password(을)를 입력해주세요.");
        }

        LogData rsData = memberService.tryLogin(username, password);

        if (rsData.isSuccess()) {
            Member member = (Member) rsData.getData();
            resp.addCookie(new Cookie("loginedMemberId", member.getId() + ""));
        }

        return rsData;
    }

    @GetMapping("/member/logout")
    @ResponseBody
    public LogData logout(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getCookies() != null) {
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginedMemberId"))
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
        }

        return LogData.of("S-1", "로그아웃 되었습니다.");
    }

    @GetMapping("/member/me")
    @ResponseBody
    public LogData showMe(HttpServletRequest req) {
        long loginedMemberId = 0;

        if (req.getCookies() != null) {
            loginedMemberId = Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals("loginedMemberId"))
                    .map(Cookie::getValue)
                    .mapToLong(Long::parseLong)
                    .findFirst()
                    .orElse(0);
        }

        boolean isLogined = loginedMemberId > 0;

        if (isLogined == false)
            return LogData.of("F-1", "로그인 후 이용해주세요.");

        Member member = memberService.findById(loginedMemberId);

        return LogData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
    }
}