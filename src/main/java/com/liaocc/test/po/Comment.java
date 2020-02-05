package com.liaocc.test.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Comment {
    @Id
    Long id;
    Long blog_id;
    Long user_id;
    Long reply_user_id;
    String comment;

    @Transient
    String username;
    @Transient
    String reply_username;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", blog_id=" + blog_id +
                ", user_id=" + user_id +
                ", reply_user_id=" + reply_user_id +
                ", comment='" + comment + '\'' +
                ", username='" + username + '\'' +
                ", reply_username='" + reply_username + '\'' +
                '}';
    }

    public String getReply_username() {
        return reply_username;
    }

    public void setReply_username(String reply_username) {
        this.reply_username = reply_username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(Long blog_id) {
        this.blog_id = blog_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getReply_user_id() {
        return reply_user_id;
    }

    public void setReply_user_id(Long reply_user_id) {
        this.reply_user_id = reply_user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public Comment() {
    }
}
