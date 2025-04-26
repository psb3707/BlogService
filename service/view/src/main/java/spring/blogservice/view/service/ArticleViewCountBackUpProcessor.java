package spring.blogservice.view.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.blogservice.view.entity.ArticleViewCount;
import spring.blogservice.view.repository.ArticleViewCountBackUpRepository;
import spring.blogservice.view.repository.ArticleViewCountRepository;

@Component
@RequiredArgsConstructor
public class ArticleViewCountBackUpProcessor {

    private final ArticleViewCountBackUpRepository articleViewCountBackUpRepository;

    @Transactional
    public void backUp(Long articleId, Long viewCount) {

        int result = articleViewCountBackUpRepository.updateViewCount(articleId, viewCount);

        if(result == 0){
            articleViewCountBackUpRepository.findById(articleId)
                    .ifPresentOrElse(ignored -> {},
                            () -> articleViewCountBackUpRepository.save(
                                    ArticleViewCount.init(viewCount, articleId)
                            ));
        }
    }
}
