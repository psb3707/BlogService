package spring.blogservice.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.blogservice.like.service.ArticleLikeService;
import spring.blogservice.like.service.response.ArticleLikeResponse;

@RestController
@RequiredArgsConstructor
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    @GetMapping("/v1/articles-likes/articles/{articleId}/users/{userId}")
    public ArticleLikeResponse getUserLikes(@PathVariable Long articleId, @PathVariable Long userId) {
        return articleLikeService.read(userId, articleId);
    }

    @PostMapping("/v1/articles-likes/articles/{articleId}/users/{userId}")
    public void like(@PathVariable Long articleId, @PathVariable Long userId) {
        articleLikeService.like(userId, articleId);
    }

    @DeleteMapping("/v1/articles-likes/articles/{articleId}/users/{userId}")
    public void unlike(@PathVariable Long articleId, @PathVariable Long userId) {
        articleLikeService.unlike(userId, articleId);
    }
}
