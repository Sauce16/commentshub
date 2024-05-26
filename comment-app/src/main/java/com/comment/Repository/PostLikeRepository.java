package com.comment.Repository;

import com.comment.Model.Comment;
import com.comment.Model.PostLike;
import com.comment.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("SELECT pl FROM PostLike pl WHERE pl.post.postId = :postId")
    List<PostLike> findByPostId(@Param("postId") Long postId);

    @Query("SELECT pl FROM PostLike pl WHERE pl.comment.commentId = :commentId")
    List<PostLike> findByCommentId(@Param("commentId") Long commentId);

}
