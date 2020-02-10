package com.liaocc.test.po;

import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String psw;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", psw='" + psw + '\'' +
                '}';
    }

}
