package com.liaocc.test.po.key;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UseridAndTagidKey implements Serializable {

    private Long tag_id;
    private Long user_id;

    public Long getTag_id() {
        return tag_id;
    }

    @Override
    public String toString() {
        return "UseridAndTagidKey{" +
                "tag_id=" + tag_id +
                ", user_id=" + user_id +
                '}';
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }

    public Long getBlog_id() {
        return user_id;
    }

    public void setBlog_id(Long user_id) {
        this.user_id = user_id;
    }
}
