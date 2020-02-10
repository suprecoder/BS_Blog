package com.liaocc.test.web;

import com.liaocc.test.dao.TagRepository;
import com.liaocc.test.po.Blog;
import com.liaocc.test.po.User;
import com.liaocc.test.service.BlogService;
import com.liaocc.test.service.TagService;
import com.liaocc.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class publishController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @PostMapping("/publish")
    String publish(@RequestBody Blog blog, HttpSession session){

        String username=(String)session.getAttribute("username");
        if(username==null)
            return "no";
        User user=userService.getUserbyname(username);
        blog.setUser(user);
        blog.setPrefer(0l);blog.setReadnum(0l);blog.setIsdraft(false);
        System.out.println(blog.getTags());
        tagService.addtags(blog.getId(),blog.getTags());
        Blog temp=blogService.saveblog(blog);
        if(temp!=null)
            return temp.getId().toString();
        return "no";
    }

    @GetMapping("publish/getrecommand")
    List<String> getRecommand(@RequestParam(value = "tagname",required = false)String tagname){
        if(tagname==null || tagname=="")
            return new ArrayList<>();
        return tagService.getRecommandTagsName(tagname);
    }
}
