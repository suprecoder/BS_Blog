package com.liaocc.test.table;

import javax.persistence.*;
@Entity
public class BlogTitleAndHtml {
    @Id
    public String title;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    public String content_html;
    @Transient
    public String username;



}
