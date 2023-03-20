package com.ll.basic.boundedContext.article.controller;

import com.ll.basic.base.logData.LogData;
import com.ll.basic.boundedContext.article.entity.Article;
import com.ll.basic.boundedContext.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/write")
    @ResponseBody
    public LogData write(String title, String body) {
        if (title == null || title.trim().length() == 0) {
            return LogData.of("F-1", "title(을)를 입력해주세요.");
        }

        if (body == null || body.trim().length() == 0) {
            return LogData.of("F-2", "body(을)를 입력해주세요.");
        }

        Article createdArticle = articleService.write(title, body);

        return LogData.of("S-1", "1번 글이 생성되었습니다.", createdArticle);
    }
}