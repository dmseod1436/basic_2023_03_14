package com.ll.basic.boundedContext.article.repository;

import com.ll.basic.boundedContext.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
