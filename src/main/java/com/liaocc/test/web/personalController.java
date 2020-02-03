package com.liaocc.test.web;

import com.liaocc.test.dao.AvatarRepository;
import com.liaocc.test.dao.BlogRepository;
import com.liaocc.test.po.Avatar;
import com.liaocc.test.po.User;
import com.liaocc.test.service.AvatarService;
import com.liaocc.test.service.UserService;
import com.sun.xml.internal.ws.api.pipe.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

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
        System.out.println("userid: "+userid);
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
}
