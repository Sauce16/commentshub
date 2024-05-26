package com.comment.Service.Impl;

import com.comment.Exceptions.CommentException;
import com.comment.Exceptions.PostException;
import com.comment.Exceptions.ReplyException;
import com.comment.Exceptions.UsersException;
import com.comment.Model.Comment;
import com.comment.Model.DTO.CommentDTO;
import com.comment.Model.Post;
import com.comment.Model.Reply;
import com.comment.Model.User;
import com.comment.Repository.CommentRepository;
import com.comment.Repository.PostRepository;
import com.comment.Repository.ReplyRepository;
import com.comment.Repository.UserRepository;
import com.comment.Service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReplyRepository replyRepository;

    /**
     * Saves a new comment associated with a specific user and post.
     * Validates the comment details, ensures the user and post exist, and records the comment's timestamp.
     *
     * @param comment  The comment to be saved.
     * @param username The username of the user making the comment.
     * @param postId    The ID of the post to which the comment is associated.
     * @return A CommentDTO representing the newly created comment.
     * @throws CommentException if the comment details are invalid or if the user or post is not found.
     */
    @Transactional
    @Override
    public CommentDTO postComment(Comment comment, String username, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostException("Post not found"));
        if (comment == null)
            throw new CommentException("Invalid comment Details");

        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsersException("User with name: " + username + " not found");

        comment.setCommentDatetime(LocalDateTime.now());
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);

        return new CommentDTO(comment);

    }

    @Transactional
    @Override
    public Reply addReplyToComment(Long commentId, String username, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsersException("User with name: " + username + " not found");

        Reply reply = new Reply();
        reply.setComment(comment);
        reply.setUser(user);
        reply.setMessage(content);
        reply.setReplyDatetime(LocalDateTime.now());
        return replyRepository.save(reply);
    }

    @Transactional
    @Override
    public Reply addReplyToReply(Long replyId, String username , String content) {
        Reply parentReply = replyRepository.findById(replyId).orElseThrow(() -> new RuntimeException("Reply not found"));

        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsersException("User with name: " + username + " not found");

        Reply reply = new Reply();
        reply.setParentReply(parentReply);
        reply.setComment(parentReply.getComment());
        reply.setUser(user);
        reply.setMessage(content);
        reply.setReplyDatetime(LocalDateTime.now());
        return replyRepository.save(reply);
    }


    /**
     * Retrieves a comment by its unique ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return The retrieved Comment.
     * @throws CommentException if the comment is not found.
     */
    @Override
    public Comment getCommentById(Long id) {
        if (id == null)
            throw new CommentException("Invalid comment id");
        return commentRepository.findById(id).orElseThrow(() -> new CommentException("Comment not found"));
    }

    @Override
    public List<Reply> getChildReplies(Long replyId) {
        if (replyId == null)
            throw new ReplyException("Invalid reply id");

        Reply parentReply = replyRepository.findById(replyId).orElse(null);

        List<Reply> childReplies = List.of();
        if (parentReply != null) {
            childReplies = replyRepository.findByParentReply(parentReply);
        }
        if (childReplies.isEmpty()) {
            throw new ReplyException("No child comments found for replyId: " + replyId);
        }
        return childReplies;
    }

    /*@Override
    public List<Reply> getRepliesForComment(Long commentId) {
        if (commentId == null)
            throw new CommentException("Invalid comment id");

        List<Reply> repliesList = replyRepository.findByCommentIdAndParentReplyIdIsNull(commentId);
        if(repliesList.isEmpty()) {
            throw new ReplyException("No replies found for commentId: " + commentId);
        }
         return repliesList;
    }*/
    /**
     * Deletes a comment by its unique ID.
     *
     * @param id The ID of the comment to delete.
     * @return The deleted Comment.
     * @throws CommentException if the comment is not found.
     */
    @Override
    public Comment DeleteCommentById(Long id) {
        if (id == null)
            throw new CommentException("Invalid comment id");
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentException("Comment not found"));
        commentRepository.deleteById(id);
        return comment;
    }

    /**
     * Retrieves a list of comments associated with a specific user's posts and maps them to CommentDTOs.
     *
     * @param username The username of the user whose comments are to be retrieved.
     * @return A list of CommentDTOs representing the user's comments.
     * @throws CommentException if the username is invalid, the user is not found, or no comments are found for the user.
     */
    @Override
    public List<CommentDTO> getAllCommentsByUserName(String username) {
        if (username == null || username.isEmpty()) {
            throw new UsersException("Invalid username");
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsersException("User with username: " + username + " not found");
        }


        Post post = postRepository.findById(user.getUserId()).orElseThrow(() -> new PostException("Post not found"));
        List<Comment> comments = post.getComments();


        log.info("Comments: {}", comments);

        if (comments.isEmpty()) {
            throw new CommentException("No comments found for user: " + username);
        }

        return comments.stream().map(CommentDTO::new).collect(Collectors.toList());
    }


    /**
     * Retrieves a list of comments associated with a specific post.
     *
     * @param id The ID of the post whose comments are to be retrieved.
     * @return A list of comments associated with the post.
     * @throws PostException if the post ID is invalid or if no comments are found for the post.
     */
    @Override
    public List<Comment> getAllCommentsByPostId(Long id) {
        if (id == null)
            throw new PostException("Invalid post id");

        Post post = postRepository.findById(id).orElseThrow(() -> new PostException("Post not found"));
        List<Comment> comments = post.getComments();
        if (comments.isEmpty())
            throw new CommentException("No comments found");
        return comments;
    }
}
