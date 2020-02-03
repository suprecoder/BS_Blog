package com.liaocc.test.po;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Avatar {
    @Id
    Long userid;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
