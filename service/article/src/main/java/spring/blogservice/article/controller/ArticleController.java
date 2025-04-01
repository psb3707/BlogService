package spring.blogservice.article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.blogservice.article.service.ArticleService;
import spring.blogservice.article.service.request.ArticleCreateRequest;
import spring.blogservice.article.service.request.ArticleUpdateRequest;
import spring.blogservice.article.service.response.ArticlePageResponse;
import spring.blogservice.article.service.response.ArticleResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/v1/articles/{articleId}")
    public ResponseEntity<ArticleResponse> read(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.read(articleId));
    }

    @GetMapping("/v1/articles")
    public ResponseEntity<ArticlePageResponse> readAll(@RequestParam("boardId") Long boardId,
                                                       @RequestParam("page") Long page,
                                                       @RequestParam("pageSize") Long pageSize) {
        return ResponseEntity.ok(articleService.readAll(boardId, page, pageSize));
    }

    @GetMapping("/v1/articles/infinite-scroll")
    public ResponseEntity<List<ArticleResponse>> readAllInfiniteScroll(@RequestParam("boardId") Long boardId,
                                                                       @RequestParam(value = "lastArticleId", required = false) Long lastArticleId,
                                                                       @RequestParam("pageSize") Long pageSize){
        return ResponseEntity.ok(articleService.readAllInfiniteScroll(boardId, pageSize, lastArticleId));
    }

    @PostMapping("/v1/articles")
    public ResponseEntity<ArticleResponse> create(@RequestBody ArticleCreateRequest request) {
        return ResponseEntity.status(201).body(articleService.create(request));
    }

    @PutMapping("/v1/articles/{articleId}")
    public ResponseEntity<ArticleResponse> update(@PathVariable Long articleId,
                                                  @RequestBody ArticleUpdateRequest request) {
        return ResponseEntity.ok(articleService.update(articleId, request));
    }

    @DeleteMapping("/v1/articles/{articleId}")
    public ResponseEntity<?> delete(@PathVariable Long articleId) {
        articleService.delete(articleId);
        return ResponseEntity.ok().build();
    }
}
