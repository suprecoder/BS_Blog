package com.liaocc.test.po;

import com.liaocc.test.po.key.UseridAndFollowid;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Follow {
    @EmbeddedId
    UseridAndFollowid useridAndFollowid;

    public UseridAndFollowid getUseridAndFollowid() {
        return useridAndFollowid;
    }

    public void setUseridAndFollowid(UseridAndFollowid useridAndFollowid) {
        this.useridAndFollowid = useridAndFollowid;
    }
}
