package com.liaocc.test.web;

import com.liaocc.test.po.Blog;
import com.liaocc.test.service.BlogService;
import com.liaocc.test.table.BlogTitleAndHtml;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/showblog")
public class showblogController {
    @Autowired
    BlogService blogService;

    @PostMapping("getHtmlContent")
    BlogTitleAndHtml getHtmlContent(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        Long id = jsonObject.getLong("blogid");
        BlogTitleAndHtml titleAndHtml=blogService.getHtmlContent(id);
        titleAndHtml.username=blogService.getblog(id).getUser().getUsername();
        return titleAndHtml;
    }
}
