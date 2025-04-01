package spring.blogservice.article.service.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ArticleCreateRequest {
    private String title;
    private String content;
    private Long writerId;
    private Long boardId;

    public ArticleCreateRequest(String title, String content, Long writerId, Long boardId) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.boardId = boardId;
    }
}
