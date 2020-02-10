package com.liaocc.test.table;

import com.liaocc.test.po.key.BlogidAndTagidKey;
import com.liaocc.test.po.key.UseridAndTagidKey;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
public class TagidAndNum {
    @EmbeddedId
    UseridAndTagidKey useridAndTagidKey;
    Integer num;

    public UseridAndTagidKey getUseridAndTagidKey() {
        return useridAndTagidKey;
    }

    @Override
    public String toString() {
        return "TagidAndNum{" +
                "useridAndTagidKey=" + useridAndTagidKey +
                ", num=" + num +
                '}';
    }

    public void setUseridAndTagidKey(UseridAndTagidKey useridAndTagidKey) {
        this.useridAndTagidKey = useridAndTagidKey;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
