package com.liaocc.test.service;

import com.liaocc.test.po.Blog;
import com.liaocc.test.po.User;
import com.liaocc.test.table.BlogTitleAndHtml;

import java.util.List;

public interface BlogService {
    List<Blog> listblog();
    Blog saveblog(Blog blog);
    Blog getblog(Long blogid);
    List<Blog> listblogbyname(String username,int page);
    List<Blog> listbloginpage(int page);
    //总共有多少推荐博客
    public Long countAllRecommand(Long userid);
    //我有多少博客
    Long countAll(Long id);
    Blog getBlogByUserAndTitle(User user,String title);
    BlogTitleAndHtml getHtmlContent(Long id);
    String getSummary(Long blogid);
    public String toSummary(Blog b);
    boolean delete(Long blogid,Long userid);
    public List<Blog> getRecommandBlogInpage(Long userid,int page);
    List<Blog> getRecommandBlog(Long userid);
}
