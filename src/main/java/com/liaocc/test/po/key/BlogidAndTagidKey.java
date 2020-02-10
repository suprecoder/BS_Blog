package com.liaocc.test.po.key;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class BlogidAndTagidKey implements Serializable {

    private Long tag_id;
    private Long blog_id;

    public Long getTag_id() {
        return tag_id;
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }

    public Long getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(Long blog_id) {
        this.blog_id = blog_id;
    }
}
