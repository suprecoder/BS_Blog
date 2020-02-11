package com.liaocc.test.web;

import com.liaocc.test.po.Blog;
import com.liaocc.test.po.Favourite;
import com.liaocc.test.po.Prefer;
import com.liaocc.test.po.User;
import com.liaocc.test.service.*;
import com.liaocc.test.table.BlogTitleAndSummary;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/myblog")
public class myBlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    PreferService preferService;
    @Autowired
    FavouriteService favouriteService;
    @Autowired
    UserService userService;
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
    //val 页数
    @GetMapping("getblogs")
    List<BlogTitleAndSummary> getmyBlog(@RequestParam(value = "val",required = false) Integer val,HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        if(user!=null)
            userid=user.getId();
        else return null;
        List<Blog> blogs= blogService.listblogbyname(username,val);
        List<BlogTitleAndSummary> ans=blogService.toTitleAndSummary(blogs,userid);
        return ans;
    }

    @GetMapping("countAll")
    public Long countAll(HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        return blogService.countAll(user.getId());
    }

    @GetMapping("delete")
    String delete(@RequestParam(value = "blogid",required = true) Long blogid,HttpSession session){
        userid=getuserid(session);
        boolean flag=blogService.delete(blogid,userid);
        if(flag)
            return "ok";
        return "no";
    }
}
