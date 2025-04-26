package spring.blogservice.view.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "article_view_count")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleViewCount {
    @Id
    private Long articleId;

    private Long viewCount;

    public static ArticleViewCount init(Long viewCount, Long articleId) {
        ArticleViewCount articleViewCount = new ArticleViewCount();
        articleViewCount.viewCount = viewCount;
        articleViewCount.articleId = articleId;
        return articleViewCount;
    }
}
