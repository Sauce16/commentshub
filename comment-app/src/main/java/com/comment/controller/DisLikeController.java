package com.comment.controller;

import com.comment.Model.DisLike;
import com.comment.Service.DisLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dislikes")
public class DisLikeController {
    @Autowired
    private DisLikeService dislikeService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<DisLike> dislikePost(@PathVariable Long postId, @RequestParam String username) {
        DisLike dislike = dislikeService.dislikePost(postId, username);
        return ResponseEntity.ok(dislike);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<DisLike> dislikeComment(@PathVariable Long commentId, @RequestParam String username) {
        DisLike dislike = dislikeService.dislikeComment(commentId, username);
        return ResponseEntity.ok(dislike);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<DisLike>> getDislikesForPost(@PathVariable Long postId) {
        List<DisLike> dislikes = dislikeService.getDisLikesForPost(postId);
        return ResponseEntity.ok(dislikes);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<DisLike>> getDislikesForComment(@PathVariable Long commentId) {
        List<DisLike> dislikes = dislikeService.getDislikesForComment(commentId);
        return ResponseEntity.ok(dislikes);
    }
}
