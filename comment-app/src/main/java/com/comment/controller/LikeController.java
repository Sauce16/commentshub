package com.comment.controller;

import com.comment.Model.PostLike;
import com.comment.Service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postlikes")
public class LikeController {
    @Autowired
    private PostLikeService postLikeService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<PostLike> likePost(@PathVariable Long postId, @RequestParam String username) {
        PostLike postLike = postLikeService.likePost(postId, username);
        return ResponseEntity.ok(postLike);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<PostLike> likeComment(@PathVariable Long commentId, @RequestParam String username) {
        PostLike postLike = postLikeService.likeComment(commentId, username);
        return ResponseEntity.ok(postLike);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostLike>> getLikesForPost(@PathVariable Long postId) {
        List<PostLike> postLikes = postLikeService.getLikesForPost(postId);
        return ResponseEntity.ok(postLikes);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<PostLike>> getLikesForComment(@PathVariable Long commentId) {
        List<PostLike> postLikes = postLikeService.getLikesForComment(commentId);
        return ResponseEntity.ok(postLikes);
    }
}
