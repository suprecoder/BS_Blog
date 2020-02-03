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
            temp.setWriter(blogService.getblog(b.getId()).getUser().getUsername());
            ans.add(temp);
        }
        return ans;
    }
    @GetMapping("countAll")
    public int countAll(HttpSession session){
        return followService.count(getuserid(session));
    }
}
