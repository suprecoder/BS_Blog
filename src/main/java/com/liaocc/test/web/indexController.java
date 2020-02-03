package com.liaocc.test.web;

import com.liaocc.test.po.Blog;
import com.liaocc.test.po.Favourite;
import com.liaocc.test.po.Prefer;
import com.liaocc.test.po.User;
import com.liaocc.test.redis.RedisUtils;
import com.liaocc.test.service.*;
import com.liaocc.test.table.BlogTitleAndSummary;
import com.liaocc.test.utils.CoookieUtils;
import com.liaocc.test.utils.Msg;
import com.liaocc.test.utils.UpPhotoNameUtils;
import com.sun.crypto.provider.BlowfishKeyGenerator;
import com.sun.deploy.net.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpCookie;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class indexController {
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    PreferService preferService;
    @Autowired
    FavouriteService favouriteService;
    @Autowired
    TagService tagService;
    Long userid;
    @GetMapping("islogin")
    Msg islogin(HttpSession session){
        String username= (String) session.getAttribute("username");
        if(username!=null)
            return new Msg(username);
        return new Msg("reject");
    }

    @Autowired
    RedisUtils redisUtils;
    @ResponseBody
    @PostMapping("login")
    Msg admin(@RequestBody String jsonstr, HttpSession session, HttpServletResponse response){
        JSONObject jsonObject=new JSONObject(jsonstr);

        String username=jsonObject.getString("username");
        String psw=jsonObject.getString("psw");
        redisUtils.add("username",username);
        //redisUtils.get("username");
        boolean isok=userService.login(username,psw);
        if(isok){
            String usersession= (String) session.getAttribute("user");
            if(usersession==null || !usersession.equals(username)) {
                session.setAttribute("username", username);
                System.out.println(username);
            }
            return new Msg("ok");
        }
        System.out.println("login request:"+username+" --- "+psw);

        return new Msg("gun");
    }

    @PostMapping("logout")
    String logout(HttpSession session){
        session.removeAttribute("username");
        return "ok";
    }

    @GetMapping("getblogs")
    @ResponseBody
    List<BlogTitleAndSummary> getblogs(@RequestParam(value = "val",required = false) Integer val,HttpSession session){
        User user=userService.getUserbyname((String) session.getAttribute("username"));
        if(user!=null)
            userid=user.getId();
        else return null;
        List<Blog> blogs=blogService.listbloginpage(val);
        List<BlogTitleAndSummary> ans=new ArrayList<>();
        List<BigInteger> prefer=preferService.getPrefer(userid);
        List<BigInteger> favourite=favouriteService.getFavourite(userid);
        System.out.println(favourite);

        int ii=0;
        for(Blog b:blogs){
            if(b.getPublictype()==2)
                continue;
            b.setSummary(blogService.getSummary(b.getId()));
            BlogTitleAndSummary temp=new BlogTitleAndSummary();
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
        //return blogService.listblog();
    }

    @GetMapping("unlike")
    public String unlike(@RequestParam(value = "blogid",required = true)Long blogid,HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        if(preferService.unLike(blogid,user.getId()))
            return "ok";
        return "no";
    }

    @GetMapping("like")
    public String like(@RequestParam(value = "blogid",required = true)Long blogid,HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        if(preferService.like(blogid,user.getId()))
            return "ok";
        return "no";
    }

    @GetMapping("unfavourite")
    public String unfavourite(@RequestParam(value = "blogid",required = true)Long blogid,HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        if(favouriteService.unfavourite(blogid,user.getId()))
            return "ok";
        return "no";
    }

    @GetMapping("favourite")
    public String favourite(@RequestParam(value = "blogid",required = true)Long blogid,HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        if(favouriteService.favourite(blogid,user.getId()))
            return "ok";
        return "no";
    }

    @GetMapping("countAll")
    public Long countAll(){
        return blogService.countAll();
    }
}