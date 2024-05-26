package com.comment.controller;

import com.comment.Model.Comment;
import com.comment.Model.DTO.CommentDTO;
import com.comment.Model.DTO.ReplyRequest;
import com.comment.Model.Reply;
import com.comment.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Creates a new comment associated with a specific post and user.
     *
     * @param comment   The comment to be created.
     * @param postId    The ID of the post to which the comment is associated.
     * @param username  The username of the user making the comment.
     * @return A ResponseEntity with the created CommentDTO and HTTP status CREATED.
     */
    @PostMapping("/{postId}/{username}")
    public ResponseEntity<CommentDTO> addCommentToPost(@RequestBody Comment comment, @PathVariable Long postId, @PathVariable String username) {
        return new ResponseEntity<>(commentService.postComment(comment, username, postId), HttpStatus.CREATED);
    }


    @PostMapping("/{commentId}/replies")
    public ResponseEntity<Reply> addReplyToComment(@PathVariable Long commentId, @RequestBody ReplyRequest replyRequest) {
        Reply reply = commentService.addReplyToComment(commentId, replyRequest.getUsername(), replyRequest.getContent());
        return ResponseEntity.ok(reply);
    }

    @PostMapping("/replies/{replyId}/replies")
    public ResponseEntity<Reply> addReplyToReply(@PathVariable Long replyId, @RequestBody ReplyRequest replyRequest) {
        Reply reply = commentService.addReplyToReply(replyId, replyRequest.getUsername(), replyRequest.getContent());
        return ResponseEntity.ok(reply);
    }

    /**
     * Retrieves a comment by its unique ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return A ResponseEntity with the retrieved Comment and HTTP status OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    /*@GetMapping("/{commentId}/replies")
    public ResponseEntity<List<Reply>> getRepliesForComment(@PathVariable Long commentId) {
        List<Reply> replies = commentService.getRepliesForComment(commentId);
        return ResponseEntity.ok(replies);
    }*/

    @GetMapping("/replies/{replyId}/replies")
    public ResponseEntity<List<Reply>> getChildReplies(@PathVariable Long replyId) {
        List<Reply> replies = commentService.getChildReplies(replyId);
        return ResponseEntity.ok(replies);
    }

    /**
     * Deletes a comment by its unique ID.
     *
     * @param id The ID of the comment to delete.
     * @return A ResponseEntity with the deleted Comment and HTTP status OK.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteCommentByIdHandel(@PathVariable Long id) {
        Comment deletedComment = commentService.DeleteCommentById(id);
        return ResponseEntity.ok(deletedComment);
    }

    /**
     * Retrieves a list of comments associated with a specific user by their username.
     *
     * @param username The username of the user whose comments are to be retrieved.
     * @return A ResponseEntity with a list of CommentDTOs representing the user's comments and HTTP status OK.
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUserNameHandel(@PathVariable String username) {
        List<CommentDTO> comments = commentService.getAllCommentsByUserName(username);
        return ResponseEntity.ok(comments);
    }

    /**
     * Retrieves a list of comments associated with a specific post by its ID.
     *
     * @param postId The ID of the post whose comments are to be retrieved.
     * @return A ResponseEntity with a list of Comments representing the post's comments and HTTP status OK.
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostIdHandel(@PathVariable Long postId) {
        List<Comment> comments = commentService.getAllCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

}
