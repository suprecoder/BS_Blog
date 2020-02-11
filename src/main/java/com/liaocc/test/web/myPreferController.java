package com.liaocc.test.web;

import com.liaocc.test.dao.BlogRepository;
import com.liaocc.test.dao.FavouriteRepository;
import com.liaocc.test.dao.PreferRepository;
import com.liaocc.test.po.Blog;
import com.liaocc.test.po.Favourite;
import com.liaocc.test.po.User;
import com.liaocc.test.service.*;
import com.liaocc.test.table.BlogTitleAndSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/mylike")
public class myPreferController {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    UserService userService;
    @Autowired
    PreferRepository preferRepository;
    @Autowired
    FavouriteService favouriteService;
    @Autowired
    BlogService blogService;
    @Autowired
    TagService tagService;
    Long userid;
    Long getuserid(HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        if(user==null)return null;
        userid=user.getId();
        return user.getId();
    }
    @GetMapping("getmylike")
    List<BlogTitleAndSummary> getmylike(@RequestParam(value = "val",required = true) int val, HttpSession session){
        userid=getuserid(session);
        if(userid==null) return null;
        List<Blog> blogs=blogRepository.getPreferBlog(userid,(val-1)*8,8);
        List<BlogTitleAndSummary> ans=blogService.toTitleAndSummary(blogs,userid);
        return ans;
    }
    @GetMapping("countAll")
    int countAll(HttpSession session){
        userid=getuserid(session);
        return preferRepository.count(userid);
    }
    @Autowired
    PreferService preferService;

    @GetMapping("delete")
    String delete(@RequestParam(value = "blogid",required = true) Long blogid,HttpSession session){
        userid=getuserid(session);
        preferService.unLike(blogid,userid);
        return "ok";
    }
}
