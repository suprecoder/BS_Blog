package com.liaocc.test.web;

import com.liaocc.test.po.Blog;
import com.liaocc.test.po.Comment;
import com.liaocc.test.service.CommentService;
import com.liaocc.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/comment")
public class commentController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @GetMapping("getcomment")
    List<Comment> getcomment(@RequestParam(value = "blogid",required = true) Long blogid){
        List<Comment> comments= commentService.getcomment(blogid);
        System.out.println(comments);
        for(Comment comment:comments){
            comment.setUsername(userService.getUserByid(comment.getUser_id()).getUsername());
            if(comment.getReply_user_id()!=null){
                comment.setReply_username(userService.getUserByid(comment.getReply_user_id()).getUsername());
            }
        }
        return comments;
    }
    @PostMapping("publishcomment")
    String publishcomment(@RequestBody Comment comment, HttpSession session){
        System.out.println("comment "+comment);
        Long userid= userService.getUserbyname((String)session.getAttribute("username")).getId();
        if(comment.getReply_username()==null || comment.getReply_username()=="")
            commentService.publishcomment(comment.getBlog_id(),userid
                    ,null,comment.getComment());
        else
            commentService.publishcomment(comment.getBlog_id(),userid
                    ,userService.getUserbyname(comment.getReply_username()).getId(),comment.getComment());
        return "ok";
    }
}
