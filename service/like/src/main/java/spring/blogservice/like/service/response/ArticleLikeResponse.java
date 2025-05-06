package spring.blogservice.like.service.response;

import lombok.Getter;
import lombok.ToString;
import spring.blogservice.like.entity.ArticleLike;

import java.time.LocalDateTime;

@Getter
@ToString
public class ArticleLikeResponse {

    private Long articleLikeId;
    private Long userId;
    private Long articleId;
    private LocalDateTime createdAt;

    public static ArticleLikeResponse from(ArticleLike articleLike) {
        ArticleLikeResponse response = new ArticleLikeResponse();
        response.articleId = articleLike.getArticleId();
        response.createdAt = articleLike.getCreatedAt();
        response.articleLikeId = articleLike.getArticleLikeId();
        response.userId = articleLike.getUserId();
        return response;
    }
}
