package spring.blogservice.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.blogservice.common.snowflake.Snowflake;
import spring.blogservice.like.entity.ArticleLike;
import spring.blogservice.like.repository.ArticleLikeRepository;
import spring.blogservice.like.service.response.ArticleLikeResponse;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {


    private final ArticleLikeRepository articleLikeRepository;

    private final Snowflake snowflake = new Snowflake();

    public ArticleLikeResponse read(Long articleId, Long userId) {
        return articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
                .map(ArticleLikeResponse::from)
                .orElseThrow();
    }

    @Transactional
    public void like(Long articleId, Long userId) {
        articleLikeRepository.save(ArticleLike.create(snowflake.nextId(), articleId, userId));
    }

    @Transactional
    public void unlike(Long articleId, Long userId) {
        articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
                .ifPresent(articleLikeRepository::delete);
    }
}
