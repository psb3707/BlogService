package spring.blogservice.view.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.blogservice.view.repository.ArticleViewCountRepository;

@Service
@RequiredArgsConstructor
public class ArticleViewCountService {

    private final ArticleViewCountRepository articleViewCountRepository;
    private final ArticleViewCountBackUpProcessor articleViewCountBackUpProcessor;
    private static final int BACK_UP_BATCH_SIZE = 100;

    public Long increase(Long articleId, Long userId) {
        Long count = articleViewCountRepository.increase(articleId);
        if(count % BACK_UP_BATCH_SIZE == 0) {
            articleViewCountBackUpProcessor.backUp(articleId, count);
        }
        return count;
    }

    public Long read(Long articleId) {
        return articleViewCountRepository.read(articleId);
    }
}
