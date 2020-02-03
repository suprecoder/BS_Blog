package com.liaocc.test.service;

import com.liaocc.test.dao.BlogRepository;
import com.liaocc.test.dao.BlogTitleAndHtmlRepository;
import com.liaocc.test.po.Blog;
import com.liaocc.test.po.User;
import com.liaocc.test.table.BlogTitleAndHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    private static final int itemPerPage = 8;

    @Autowired
    BlogRepository blogRepository;
    @Autowired
    BlogTitleAndHtmlRepository blogTitleAndHtmlRepository;

    @Override
    public List<Blog> listblog() {
        return blogRepository.listblog();
    }

    @Override
    public Blog saveblog(Blog blog) {
        return blogRepository.saveAndFlush(blog);
    }

    @Override
    public Blog getblog(Long blogid) {
        return blogRepository.getblog(blogid);
    }

    @Override
    public List<Blog> listblogbyname(String username,int page) {
        return blogRepository.listblogbyusername(username,(page-1)*itemPerPage);
    }

    @Override
    public List<Blog> listbloginpage(int page) {
        return blogRepository.listinpage((page-1)*itemPerPage,itemPerPage);
    }

    @Override
    public Long countAll() {
        return blogRepository.countAll();
    }

    @Override
    public Long countAll(Long id) {
        return blogRepository.countAll(id);
    }

    @Override
    public Blog getBlogByUserAndTitle(User user,String title) {
        return blogRepository.getBlogByUserAndTitle(user,title);
    }

    @Override
    public BlogTitleAndHtml getHtmlContent(Long id) {
        return blogTitleAndHtmlRepository.getHtmlContentAndTitle(id);
    }

    @Override
    public String getSummary(Long blogid) {
        StringBuilder summary=new StringBuilder();
        Blog b=blogRepository.getblog(blogid);
        if(b.getSummary()==null || b.getSummary()==""){
            StringBuilder temp=new StringBuilder();
            for(int i=0;i<Math.min(90,b.getContent_md().length());i++)
                temp.append(b.getContent_md().charAt(i));
            if(b.getContent_md().length()>90)
                for(int i=0;i<3;i++)
                    temp.append(".");
            b.setSummary(temp.toString());
        }
        return b.getSummary();
    }
    @Override
    public String toSummary(Blog b) {
        if(b.getSummary()==null || b.getSummary()==""){
            StringBuilder temp=new StringBuilder();
            for(int i=0;i<Math.min(90,b.getContent_md().length());i++)
                temp.append(b.getContent_md().charAt(i));
            if(b.getContent_md().length()>90)
                for(int i=0;i<3;i++)
                    temp.append(".");
            b.setSummary(temp.toString());
        }
        return b.getSummary();
    }

    @Override
    public boolean delete(Long blogid, Long userid) {
        int temp= blogRepository.delete(blogid,userid);
        if(temp==0)return false;
        return true;
    }

}
