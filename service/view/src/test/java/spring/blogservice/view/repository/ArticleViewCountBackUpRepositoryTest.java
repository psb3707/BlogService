package spring.blogservice.view.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.blogservice.view.entity.ArticleViewCount;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleViewCountBackUpRepositoryTest {
    @Autowired
    private ArticleViewCountBackUpRepository articleViewCountBackUpRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @DisplayName("")
    @Test
    @Transactional
    void updateViewCount(){

        //given
        articleViewCountBackUpRepository.save(
                ArticleViewCount.init(0L, 1L)
        );

        entityManager.flush();
        entityManager.clear();

        int result1 = articleViewCountBackUpRepository.updateViewCount(1L, 100L);
        int result2 = articleViewCountBackUpRepository.updateViewCount(1L, 300L);
        int result3 = articleViewCountBackUpRepository.updateViewCount(1L, 200L);


        Assertions.assertThat(result1).isEqualTo(1);
        Assertions.assertThat(result2).isEqualTo(1);
        Assertions.assertThat(result3).isEqualTo(0);

        ArticleViewCount articleViewCount = articleViewCountBackUpRepository.findById(1L).get();
        Assertions.assertThat(articleViewCount.getViewCount()).isEqualTo(300L);

        //when

        //then
    }
}