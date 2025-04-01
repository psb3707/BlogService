package spring.blogservice.article.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.blogservice.article.entity.Article;
import spring.blogservice.article.service.PageLimitCalculator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;

    @DisplayName("")
    @Test
    void findAllTest(){

        //given
        List<Article> articles = articleRepository.findAll(1L, 1499970L, 30L);

        log.info("articles.size = {}", articles.size());

        for (Article article : articles) {
            log.info("article = {}", article);
        }
        //when

        //then

    }

    @DisplayName("")
    @Test
    void countTest(){

        //given
        Long count = articleRepository.count(1L, PageLimitCalculator.calculatePageLimit(1L, 30L, 10L));
        log.info("count = {}", count);

        //when

        //then

    }

    @DisplayName("")
    @Test
    void findAllInfiniteScroll(){

        List<Article> articles = articleRepository.findAllInfiniteScroll(1L, 30L);

        for (Article article : articles) {
            log.info("articleId = {}", article.getArticleId());
        }

        Long lastArticleId = articles.get(29).getArticleId();
        List<Article> articles1 = articleRepository.findAllInfiniteScroll(1L, 30L, lastArticleId);
        for (Article article : articles1) {
            log.info("articleId = {}", article.getArticleId());
        }


    }
}