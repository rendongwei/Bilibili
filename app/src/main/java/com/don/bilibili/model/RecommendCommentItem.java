package com.don.bilibili.model;


import com.don.bilibili.Json.Json;

public class RecommendCommentItem extends Json{

    private boolean hot;
    private boolean top;
    private RecommendComment.Comment comment;

    public RecommendCommentItem() {
    }

    public RecommendCommentItem(boolean hot, boolean top, RecommendComment.Comment comment) {
        this.hot = hot;
        this.top = top;
        this.comment = comment;
    }

    @Override
    public Object getEntity() {
        return this;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public RecommendComment.Comment getComment() {
        return comment;
    }

    public void setComment(RecommendComment.Comment comment) {
        this.comment = comment;
    }
}
