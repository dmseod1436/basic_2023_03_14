package com.ll.basic.base.boundedContext.member.service;

import com.ll.basic.base.logData.LogData;
import com.ll.basic.base.boundedContext.member.entity.Member;
import com.ll.basic.base.boundedContext.member.repository.MemberRepository;

public class MemberService {
    private MemberRepository memberRepository;

    public MemberService() {
        memberRepository = new MemberRepository();
    }

    public LogData tryLogin(String username, String password) {
        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            return LogData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        }

        if (!member.getPassword().equals(password)) {
            return LogData.of("F-1", "비밀번호가 일치하지 않습니다.");
        }

        return LogData.of("S-1", "%s 님 환영합니다.".formatted(username));
    }
}