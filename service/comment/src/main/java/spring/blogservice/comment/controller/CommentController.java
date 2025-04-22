package spring.blogservice.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.blogservice.comment.service.CommentService;
import spring.blogservice.comment.service.request.CommentCreateRequest;
import spring.blogservice.comment.service.response.CommentResponse;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/v1/comment/{commentId}")
    public CommentResponse read(@PathVariable("commnetId") Long commentId) {
        return commentService.read(commentId);
    }

    @PostMapping("/v1/comments")
    public CommentResponse create(@RequestBody CommentCreateRequest commentCreateRequest) {
        return commentService.create(commentCreateRequest);
    }

    @DeleteMapping("v1/comment/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }
}
