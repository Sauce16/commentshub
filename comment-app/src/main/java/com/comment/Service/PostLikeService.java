package com.comment.Service;

import com.comment.Model.PostLike;

import java.util.List;

public interface PostLikeService {

    public PostLike likePost(Long postId, String username);
    public PostLike likeComment(Long commentId, String username);
    public List<PostLike> getLikesForPost(Long postId);
    public List<PostLike> getLikesForComment(Long commentId);

}
