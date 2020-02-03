package com.liaocc.test.po;

import javax.persistence.*;
import java.util.List;

@Entity
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String summary;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content_md;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content_html;
    private Long readnum;
    private Long prefer;
    private Boolean isdraft;
    private Integer publictype;
    @Transient
    private Boolean islike;
    @Transient
    private List<String> tags;

    public Integer getPublictype() {
        return publictype;
    }

    public void setPublictype(Integer publictype) {
        this.publictype = publictype;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isIslike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    @ManyToOne
    private User user;
    public Blog(){}

    public Boolean getIsdraft() {
        return isdraft;
    }

    public void setIsdraft(Boolean isdraft) {
        this.isdraft = isdraft;
    }

    public Boolean getIslike() {
        return islike;
    }

    public void setIslike(Boolean islike) {
        this.islike = islike;
    }

    public Long getReadnum() {
        return readnum;
    }

    public void setReadnum(Long readnum) {
        this.readnum = readnum;
    }

    public Long getPrefer() {
        return prefer;
    }

    public void setPrefer(Long prefer) {
        this.prefer = prefer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent_md() {
        return content_md;
    }

    public void setContent_md(String content_md) {
        this.content_md = content_md;
    }

    public String getContent_html() {
        return content_html;
    }

    public void setContent_html(String content_html) {
        this.content_html = content_html;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
