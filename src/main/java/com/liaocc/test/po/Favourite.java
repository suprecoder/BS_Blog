package com.liaocc.test.po;

import com.liaocc.test.po.key.UseridAndBlogidKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Favourite {
    @EmbeddedId
    private UseridAndBlogidKey useridAndBlogidKey;

    public UseridAndBlogidKey getUseridAndBlogidKey() {
        return useridAndBlogidKey;
    }

    public void setUseridAndBlogidKey(UseridAndBlogidKey useridAndBlogidKey) {
        this.useridAndBlogidKey = useridAndBlogidKey;
    }

    @Override
    public String toString() {
        return "Favourite{" +
                "useridAndBlogidKey=" + useridAndBlogidKey +
                '}';
    }
}
