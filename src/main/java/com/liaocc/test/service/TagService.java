package com.liaocc.test.service;

import com.liaocc.test.table.BlogAndTag;
import org.json.JSONObject;

import java.util.List;

public interface TagService {
    List<String> gettags(Long blogid);
    boolean addtags(Long blogid,List<String> tags);
    List<String> getAllTagsName();
    //获取推荐输入的标签名
    List<String> getRecommandTagsName(String name);
    List<BlogAndTag> getAllTagsAndBlog();
    public List<Long> User_Tag(Long userid);
    public List<String> getHotTags();
    public JSONObject getTagsByFirstLitter();
}
