package spring.blogservice.article.service;

import spring.blogservice.article.service.response.ArticlePageResponse;
import spring.blogservice.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.blogservice.article.entity.Article;
import spring.blogservice.article.repository.ArticleRepository;
import spring.blogservice.article.service.request.ArticleCreateRequest;
import spring.blogservice.article.service.request.ArticleUpdateRequest;
import spring.blogservice.article.service.response.ArticleResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();

    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = Article.create(snowflake.nextId(), request.getTitle(), request.getContent(),
                request.getBoardId(), request.getWriterId());
        articleRepository.save(article);

        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());

        return ArticleResponse.from(article);
    }

    public ArticleResponse read(Long articleId) {
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public ArticlePageResponse readAll(Long boardId, Long page, Long pageSize){
        return ArticlePageResponse.of(
                articleRepository.findAll(boardId, (page - 1) * pageSize, pageSize).stream()
                        .map(ArticleResponse::from).toList(),
                articleRepository.count(boardId, PageLimitCalculator.calculatePageLimit(page, pageSize, 10L))
        );
    }

    public List<ArticleResponse> readAllInfiniteScroll(Long boardId, Long pageSize, Long lastArticleId) {

        if (lastArticleId == null) {
            return articleRepository.findAllInfiniteScroll(boardId, pageSize).stream()
                    .map(ArticleResponse::from).toList();
        }
        return articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId).stream()
                .map(ArticleResponse::from).toList();
    }
}
