package com.comment.Model.DTO;

import com.comment.Model.Reply;
import lombok.Getter;

import java.util.List;

public class ReplyRequest {
    private Long replyId;
    private String username;
    @Getter
    private String content;
    private List<ReplyRequest> childReplies;

    public ReplyRequest(Reply reply) {
        this.replyId = reply.getReplyId();
        this.username = reply.getUser().getUsername();
        this.content = reply.getMessage();
    }

    // Getters and setters

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public List<ReplyRequest> getChildReplies() {
        return childReplies;
    }

    public void setChildReplies(List<ReplyRequest> childReplies) {
        this.childReplies = childReplies;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
