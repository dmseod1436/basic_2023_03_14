package com.ll.basic.base.initData;

import com.ll.basic.boundedContext.article.service.ArticleService;
import com.ll.basic.boundedContext.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    public CommandLineRunner initData(MemberService memberService, ArticleService articleService) {
        return args -> {
            memberService.join("user1", "1234");
            memberService.join("abc", "12345");
            memberService.join("test", "12346");
            memberService.join("love", "12347");
            memberService.join("like", "12348");
            memberService.join("giving", "12349");
            memberService.join("thanks", "123410");
            memberService.join("hello", "123411");
            memberService.join("good", "123412");
            memberService.join("peace", "123413");

            articleService.write("제목1", "내용1");
            articleService.write("제목2", "내용2");
        };
    }
}