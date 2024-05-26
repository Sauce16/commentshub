package com.comment.Service.Impl;

import com.comment.Model.Comment;
import com.comment.Model.Post;
import com.comment.Model.PostLike;
import com.comment.Repository.CommentRepository;
import com.comment.Repository.PostLikeRepository;
import com.comment.Repository.PostRepository;
import com.comment.Service.PostLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostLikeServiceImpl implements PostLikeService {

        @Autowired
        private PostLikeRepository postLikeRepository;

        @Autowired
        private PostRepository postRepository;

        @Autowired
        private CommentRepository commentRepository;

        public PostLike likePost(Long postId, String username) {
            Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
            PostLike postLike = new PostLike();
            postLike.setPost(post);
            postLike.setUsername(username);
            return postLikeRepository.save(postLike);
        }

        public PostLike likeComment(Long commentId, String username) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
            PostLike postLike = new PostLike();
            postLike.setComment(comment);
            postLike.setUsername(username);
            return postLikeRepository.save(postLike);
        }

        public List<PostLike> getLikesForPost(Long postId) {
            return postLikeRepository.findByPostId(postId);
        }

        public List<PostLike> getLikesForComment(Long commentId) {
            return postLikeRepository.findByCommentId(commentId);
        }
}


