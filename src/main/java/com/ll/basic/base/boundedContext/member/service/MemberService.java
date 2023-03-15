package com.ll.basic.base.boundedContext.member.service;

import com.ll.basic.base.logData.LogData;
import com.ll.basic.base.boundedContext.member.entity.Member;
import com.ll.basic.base.boundedContext.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// @Component : 아래 클래스의 객체는 Ioc 컨테이너에 의해 생사소멸 관리된다.
// @Service : @Component 와 같은 의미, 가독성 때문에 이렇게 표기
@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public LogData tryLogin(String username, String password) {
        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            return LogData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        }

        if (!member.getPassword().equals(password)) {
            return LogData.of("F-1", "비밀번호가 일치하지 않습니다.");
        }

        return LogData.of("S-1", "%s 님 환영합니다.".formatted(username), member);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member findById(long id) {
        return memberRepository.findById(id);
    }
}