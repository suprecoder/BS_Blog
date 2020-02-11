package com.liaocc.test.web;

import com.liaocc.test.dao.BlogRepository;
import com.liaocc.test.dao.FavouriteRepository;
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
@RequestMapping("/api/myfavourite")
public class myFavouriteController {
    @Autowired
    TagService tagService;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    FavouriteRepository favouriteRepository;
    @Autowired
    UserService userService;
    @Autowired
    PreferService preferService;
    @Autowired
    BlogService blogService;
    Long userid;
    Long getuserid(HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        if(user==null)return null;
        userid=user.getId();
        return user.getId();
    }
    @GetMapping("getmyfavourite")
    List<BlogTitleAndSummary> getmyfavourite(@RequestParam(value = "val",required = true) int val, HttpSession session){
        userid=getuserid(session);
        if(userid==null) return null;
        List<Blog> blogs=blogRepository.getFavouriteBlog(userid,(val-1)*8,8);
        List<BlogTitleAndSummary> ans=blogService.toTitleAndSummary(blogs,userid);
        return ans;
    }
    @GetMapping("countAll")
    int countAll(HttpSession session){
        userid=getuserid(session);
        return favouriteRepository.count(userid);
    }

    @Autowired
    FavouriteService favouriteService;

    @GetMapping("delete")
    String delete(@RequestParam(value = "blogid",required = true) Long blogid,HttpSession session){
        userid=getuserid(session);
        favouriteService.unfavourite(blogid,userid);
        return "ok";
    }

}
