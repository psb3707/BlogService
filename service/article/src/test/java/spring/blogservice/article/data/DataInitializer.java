package spring.blogservice.article.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spring.blogservice.article.entity.Article;
import spring.blogservice.common.snowflake.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class DataInitializer {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionTemplate transactionTemplate;

    Snowflake snowflake = new Snowflake();

    CountDownLatch countDownLatch = new CountDownLatch(EXECUTE_COUNT);

    static final int BULK_INSERT_SIZE = 2000;
    static final int EXECUTE_COUNT = 6000;

    @Test
    void initialize() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executorService.submit(() -> {
                insert();
                countDownLatch.countDown();
                System.out.println("countDownLatch.getCount() = " + countDownLatch.getCount());
            });
        }

        countDownLatch.await();
        executorService.shutdown();
    }

    private void insert() {
        transactionTemplate.executeWithoutResult(status -> {
            for (int i = 0; i < BULK_INSERT_SIZE; i++) {
                Article article = Article.create(snowflake.nextId(), "title" + i,
                        "content" + i, 1L, 1L);
                em.persist(article);
            }
        });
    }
}
