package com.liaocc.test.web;

import com.liaocc.test.service.BlogService;
import com.liaocc.test.service.TagService;
import com.liaocc.test.service.UserService;
import com.liaocc.test.table.BlogTitleAndSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/tag")
public class tagController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @GetMapping("getordertags")
    public String getOrderTags(){
        return tagService.getTagsByFirstLitter().toString();
    }
    @GetMapping("getblogbytag")
    public List<BlogTitleAndSummary>getBlogByTag(@RequestParam(required = true,value = "tagname")String tagname, HttpSession session){
        return blogService.getBlogByTag(tagname,userService.getUserbyname((String)session.getAttribute("username")).getId());
    }
}
