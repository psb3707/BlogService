package spring.blogservice.comment.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.blogservice.comment.entity.Comment;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;


    @DisplayName("")
    @Test
    void findAllInfiniteScroll(){

        //given
        List<Comment> response = commentRepository.findAllInfiniteScroll(1L, 5L);

        for (Comment comment : response) {
            System.out.println(comment.getCommentId());
        }

        //when

        //then
    }
}