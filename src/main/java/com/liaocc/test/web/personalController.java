package com.liaocc.test.web;

import com.liaocc.test.dao.AvatarRepository;
import com.liaocc.test.dao.BlogRepository;
import com.liaocc.test.dao.PresonMsgRepository;
import com.liaocc.test.po.Avatar;
import com.liaocc.test.po.User;
import com.liaocc.test.service.*;
import com.liaocc.test.table.PersonalMsg;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.*;

@CrossOrigin
@RestController
@RequestMapping("/api/personal")
public class personalController {
    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;
    @Autowired
    FavouriteService favouriteService;
    @Autowired
    PreferService preferService;
    @Autowired
    PresonMsgRepository presonMsgRepository;
    Long userid;
    Long getuserid(HttpSession session){
        String username= (String) session.getAttribute("username");
        User user=userService.getUserbyname(username);
        if(user==null)return null;
        userid=user.getId();
        return user.getId();
    }
    @Autowired
    AvatarService avatarService;
    @Autowired
    AvatarRepository avatarRepository;
    @RequestMapping("upload")
    void upload(@RequestParam(value = "file")MultipartFile file,HttpSession session){
        userid=getuserid(session);
        avatarService.saveAvatar(userid,file);
    }
    @RequestMapping("getmyavatar")
    void getimg(HttpServletResponse response,HttpSession session){
        try{
            userid=getuserid(session);
            FileCopyUtils.copy(avatarService.getAvatar(userid), response.getOutputStream());
        }catch (Exception e){e.printStackTrace();}
    }
    //根据博客id查询作者头像
    @RequestMapping("getavatar/{id}")
    void getimg(@PathVariable("id") Long blogid,HttpServletResponse response){
        try{

            FileCopyUtils.copy(avatarService.getAvatarbyBlog(blogid), response.getOutputStream());
        }catch (Exception e){e.printStackTrace();}
    }
    //根据作者名字查询作者头像
    @RequestMapping("getavatarbyname/{name}")
    void getimgbyname(@PathVariable("name") String name,HttpServletResponse response){
        try{

            FileCopyUtils.copy(avatarService.getAvatar(userService.getUserbyname(name).getId()), response.getOutputStream());
        }catch (Exception e){e.printStackTrace();}
    }
    @RequestMapping("getusermsg")
    PersonalMsg getusermsg(@RequestParam(value = "username",required = true)String username,HttpSession session){
        Long userid=userService.getUserbyname(username).getId();
        PersonalMsg temp= presonMsgRepository.get(userid);
        temp.setIsfollow(followService.isfollow(getuserid(session),userid));
        return temp;
    }
    @PostMapping("save")
    String save(@RequestBody PersonalMsg p,HttpSession session){
        getuserid(session);
        int a=presonMsgRepository.save(p.getSex(),p.getMail(),p.getPhonenum(),p.getAddress(),p.getJob(),p.getMydescribe(),userid);
        if(a>0)
            return "ok";
        return "no";
    }

    @GetMapping("getmyget")
    String getmyget(@RequestParam(value = "username",required = true) String username){
        Long userid=userService.getUserbyname(username).getId();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("prefernum",preferService.countmygetprefer(userid));
        jsonObject.put("follownum",followService.countmygetfollow(userid));
        jsonObject.put("favouritenum",favouriteService.countmygetfavourite(userid));
        return jsonObject.toString();
    }

    //如果已经关注则取消关注，未关注则添加关注
    @GetMapping("follow")
    String follow(@RequestParam(value = "username",required = true) String username,HttpSession session){
        boolean flag;
        if(followService.isfollow(getuserid(session),userService.getUserbyname(username).getId()))
            flag=followService.unfollow(getuserid(session),userService.getUserbyname(username).getId());
        else
            flag=followService.follow(getuserid(session),userService.getUserbyname(username).getId());
        if(flag)return "ok";
        return "no";
    }
}
