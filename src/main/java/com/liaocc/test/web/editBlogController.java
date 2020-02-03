package com.liaocc.test.web;

import com.liaocc.test.dao.TagRepository;
import com.liaocc.test.po.Blog;
import com.liaocc.test.po.User;
import com.liaocc.test.service.BlogService;
import com.liaocc.test.service.TagService;
import com.liaocc.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/edit")
public class editBlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @GetMapping("getblog")
    Blog getblog(@RequestParam(value = "blogid",required = true) Long blogid, HttpSession session){
        User user=userService.getUserbyname((String)session.getAttribute("username"));
        Long userid=user.getId();
        Blog b=blogService.getblog(blogid);
        b.setTags(tagService.gettags(blogid));
        if(b.getUser().getId()==userid)
            return b;
        return new Blog();
    }

}