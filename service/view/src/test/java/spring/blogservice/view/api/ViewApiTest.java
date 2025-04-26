package spring.blogservice.view.api;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewApiTest {

    RestClient restClient = RestClient.create("http://localhost:9003");


    @DisplayName("")
    @Test
    void viewTest() throws InterruptedException {

        //given
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch = new CountDownLatch(10000);

        for (int i = 0; i < 10000; i++) {
            executorService.submit(() -> {
                restClient.post()
                        .uri("/v1/article-views/articles/{articleId}/users/{userId}", 3L, 1L)
                                .retrieve();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();


        Long count = restClient.get()
                .uri("/v1/article-views/articles/{articleId}/count", 3L)
                .retrieve()
                .body(Long.class);

        System.out.println("count = " + count);


        //when

        //then
    }
}
