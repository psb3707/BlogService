package spring.blogservice.view.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class ArticleViewDistributedLockRepository {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String KEY_FORMAT = "view::article::%s::user::%s::lock";

    public boolean lock(Long articleId, Long userId, Duration timeout) {
        return stringRedisTemplate.opsForValue().setIfAbsent(KEY_FORMAT, articleId, timeout);
    }

    private String generateKey(Long userId, Long articleId) {
        return String.format(KEY_FORMAT, userId, articleId);
    }


}
