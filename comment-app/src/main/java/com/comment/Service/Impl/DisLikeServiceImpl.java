package com.comment.Service.Impl;

import com.comment.Model.Comment;
import com.comment.Model.DisLike;
import com.comment.Model.Post;
import com.comment.Repository.CommentRepository;
import com.comment.Repository.DisLikeRepository;
import com.comment.Repository.PostRepository;
import com.comment.Service.DisLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DisLikeServiceImpl implements DisLikeService {

    @Autowired
    private DisLikeRepository dislikeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public DisLike dislikePost(Long postId, String username) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        DisLike dislike = new DisLike();
        dislike.setPost(post);
        dislike.setUsername(username);
        return dislikeRepository.save(dislike);
    }

    public DisLike dislikeComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        DisLike dislike = new DisLike();
        dislike.setComment(comment);
        dislike.setUsername(username);
        return dislikeRepository.save(dislike);
    }

    public List<DisLike> getDisLikesForPost(Long postId) {
        return dislikeRepository.findByPostId(postId);
    }

    public List<DisLike> getDislikesForComment(Long commentId) {
        return dislikeRepository.findByCommentId(commentId);
    }
}

