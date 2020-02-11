package com.liaocc.test.web;

import com.liaocc.test.dao.BlogRepository;
import com.liaocc.test.dao.FollowRepository;
import com.liaocc.test.po.Blog;
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
@RequestMapping("/api/myfollow")
public class followBlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @Autowired
    FollowService followService;
    @Autowired
    PreferService preferService;
    @Autowired
    FavouriteService favouriteService;
    Long userid;
    Long getuserid(HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        if(user==null)return null;
        userid=user.getId();
        return user.getId();
    }
    @GetMapping("getmyfollow")
    List<BlogTitleAndSummary> getmyfavourite(@RequestParam(value = "val",required = true) int val, HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        if(user!=null)
            userid=user.getId();
        else return null;
        System.out.println(username);
        List<Blog> blogs=followService.getfollow(userid,val);
        List<BlogTitleAndSummary> ans=blogService.toTitleAndSummary(blogs,userid);
        return ans;
    }
    @GetMapping("countAll")
    public int countAll(HttpSession session){
        return followService.count(getuserid(session));
    }
}
