package com.liaocc.test.table;

import com.liaocc.test.po.key.BlogidAndTagidKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BlogAndTag {
    @EmbeddedId
    BlogidAndTagidKey blogidAndTagidKey;

    public BlogidAndTagidKey getBlogidAndTagidKey() {
        return blogidAndTagidKey;
    }

    public void setBlogidAndTagidKey(BlogidAndTagidKey blogidAndTagidKey) {
        this.blogidAndTagidKey = blogidAndTagidKey;
    }
}
