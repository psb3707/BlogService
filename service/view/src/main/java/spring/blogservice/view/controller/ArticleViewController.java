package spring.blogservice.view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.blogservice.view.service.ArticleViewCountService;

@RestController
@RequiredArgsConstructor
public class ArticleViewController {
    private final ArticleViewCountService articleViewCountService;

    @PostMapping("/v1/article-views/articles/{articleId}/users/{userId}")
    public Long increase(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ){
        return articleViewCountService.increase(articleId, userId);
    }

    @GetMapping("/v1/article-views/articles/{articleId}/count")
    public Long count(@PathVariable("articleId") Long articleId){
        return articleViewCountService.read(articleId);
    }
}
