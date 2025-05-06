package spring.blogservice.like.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "article_like")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleLike {

    @Id
    private Long articleLikeId;

    private Long articleId;

    private Long userId;

    private LocalDateTime createdAt;

    public static ArticleLike create(Long articleId, Long userId, Long articleLikeId) {
        ArticleLike articleLike = new ArticleLike();
        articleLike.articleId = articleId;
        articleLike.userId = userId;
        articleLike.articleLikeId = articleLikeId;
        return articleLike;
    }
}
