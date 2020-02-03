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
        System.out.println(username);
        List<Blog> blogs= blogService.listblogbyname(username,val);
        List<BlogTitleAndSummary> ans=new ArrayList<>();
        List<BigInteger> prefer=preferService.getPrefer(userid);
        List<BigInteger> favourite=favouriteService.getFavourite(userid);
        int ii=0;
        for(Blog b:blogs) {
            b.setSummary(blogService.getSummary(b.getId()));
            BlogTitleAndSummary temp = new BlogTitleAndSummary();
            temp.setId(b.getId());
            temp.setTitle(b.getTitle());
            temp.setSummary(b.getSummary());
            temp.setLike(false);
            temp.setFavourite(false);
            temp.setTags(tagService.gettags(b.getId()));
            for(int i=0;i<prefer.size();i++){
                if(prefer.get(i).longValue()==b.getId().longValue()){
                    temp.setLike(true);
                }
            }
            for(int i=0;i<favourite.size();i++){
                if(favourite.get(i).longValue()==b.getId().longValue()){
                    temp.setFavourite(true);
                }
            }
            ans.add(temp);
        }
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
