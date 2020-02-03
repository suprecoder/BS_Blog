package com.liaocc.test.service;

import com.liaocc.test.po.Blog;

import java.math.BigInteger;
import java.util.List;

public interface FollowService {
    public List<Blog> getfollow(Long userid,int val);
    public boolean follow(Long blog_id,Long user_id);
    public boolean unfollow(Long blog_id,Long user_id);
    public int count(Long userid);
}
