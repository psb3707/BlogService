package spring.blogservice.comment.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import spring.blogservice.comment.service.response.CommentPageResponse;
import spring.blogservice.comment.service.response.CommentResponse;

import java.util.List;

public class CommentApiTest {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @DisplayName("")
    @Test
    void readAll(){

        //given
        CommentPageResponse response = restClient.get()
                .uri("v1/comments?articleId=1&page=1&pageSize=10")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());

        for (CommentResponse comment : response.getComments()) {
            if(!comment.getCommentId().equals(comment.getParentCommentId())){
                System.out.println("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }

        //when

        //then
    }


    @DisplayName("")
    @Test
    void readAllInfiniteScroll(){

        //given
        List<CommentResponse> response = restClient.get()
                .uri("v1/comments/infinite-scroll?articleId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("firstPage");

        for (CommentResponse comment : response) {
            if(!comment.getCommentId().equals(comment.getParentCommentId())){
                System.out.println("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }

        //when

        //then
    }

}
