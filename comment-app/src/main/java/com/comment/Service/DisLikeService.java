package com.comment.Service;

import com.comment.Model.DisLike;
import com.comment.Model.PostLike;

import java.util.List;

public interface DisLikeService {
    public DisLike dislikePost(Long postId, String username);
    public DisLike dislikeComment(Long commentId, String username);
    public List<DisLike> getDisLikesForPost(Long postId);
    public List<DisLike> getDislikesForComment(Long commentId);
}
