package com.liaocc.test.table;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class PersonalMsg {
    @Id
    Long id;
    String job;
    String sex;
    String mydescribe;
    String mail;
    String phonenum;
    String address;




    public PersonalMsg(Long id, String job, String sex, String mydescribe, String mail, String phonenum, String address) {
        this.id = id;
        this.job = job;
        this.sex = sex;
        this.mydescribe = mydescribe;
        this.mail = mail;
        this.phonenum = phonenum;
        this.address = address;
    }

    public PersonalMsg() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMydescribe() {
        return mydescribe;
    }

    public void setMydescribe(String mydescribe) {
        this.mydescribe = mydescribe;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
