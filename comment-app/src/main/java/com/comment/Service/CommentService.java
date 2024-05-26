package com.comment.Service;

import com.comment.Model.Comment;
import com.comment.Model.DTO.CommentDTO;
import com.comment.Model.Reply;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CommentService {
    public CommentDTO postComment(Comment comment, String username, Long postId);

    public Reply addReplyToComment(Long commentId, String username, String content);

    public Reply addReplyToReply(Long replyId, String username , String content);

    public Comment getCommentById(Long id);

    public List<Reply> getChildReplies(Long replyId);

    //public List<Reply> getRepliesForComment(Long commentId);

    public Comment DeleteCommentById(Long id);

    public List<CommentDTO> getAllCommentsByUserName(String name);

    public List<Comment> getAllCommentsByPostId(Long id);

}
