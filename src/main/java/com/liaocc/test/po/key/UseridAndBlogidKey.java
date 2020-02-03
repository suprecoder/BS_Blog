package com.liaocc.test.po.key;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UseridAndBlogidKey implements Serializable {
    private Long user_id;
    private Long blog_id;

    @Override
    public String toString() {
        return "FavouriteKey{" +
                "user_id=" + user_id +
                ", blog_id=" + blog_id +
                '}';
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(Long blog_id) {
        this.blog_id = blog_id;
    }

    public UseridAndBlogidKey() {
    }
}
