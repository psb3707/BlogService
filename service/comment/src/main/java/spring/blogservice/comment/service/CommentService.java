package spring.blogservice.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.blogservice.comment.entity.Comment;
import spring.blogservice.comment.repository.CommentRepository;
import spring.blogservice.comment.service.request.CommentCreateRequest;
import spring.blogservice.comment.service.response.CommentResponse;
import spring.blogservice.common.snowflake.Snowflake;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final Snowflake snowflake = new Snowflake();


    @Transactional
    public CommentResponse create(CommentCreateRequest request) {

        Comment parent = findParent(request);

        Comment comment = Comment.create(snowflake.nextId(), request.getContent(),
                parent == null ? null : parent.getParentCommentId(),
                request.getArticleId(), request.getWriterId());

        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    public CommentResponse read(Long commentId) {
        return CommentResponse.from(commentRepository.findById(commentId).orElseThrow());
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId)
                .filter(Comment::getDeleted)
                .ifPresent(comment -> {
                    if(hasChildren(comment)){
                        comment.delete();
                    }else{
                        hardDelete(comment);
                    }
                });
    }

    private void hardDelete(Comment comment) {
        commentRepository.delete(comment);

        if (!comment.isRoot()) {
            commentRepository.findById(comment.getParentCommentId())
                    .filter(Comment::getDeleted)
                    .filter(Predicate.not(this::hasChildren))
                    .ifPresent(this::hardDelete);
        }
    }

    private boolean hasChildren(Comment comment) {
        return commentRepository.countBy(comment.getArticleId(), comment.getCommentId(), 2L) == 2;
    }

    private Comment findParent(CommentCreateRequest request) {
        Long parentCommentId = request.getParentCommentId();

        if (parentCommentId == null) {
            return null;
        }

        return commentRepository.findById(parentCommentId)
                .filter(Predicate.not(Comment::getDeleted))
                .filter(Comment::isRoot)
                .orElseThrow();
    }


}
