package com.comment.Repository;

import com.comment.Model.DisLike;
import com.comment.Model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisLikeRepository extends JpaRepository<DisLike, Long> {
    @Query("SELECT d FROM DisLike d WHERE d.post.postId = :postId")
    List<DisLike> findByPostId(@Param("postId") Long postId);

    @Query("SELECT d FROM DisLike d WHERE d.comment.commentId = :commentId")
    List<DisLike> findByCommentId(@Param("commentId") Long commentId);
}
