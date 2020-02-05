package com.liaocc.test.service;

import com.liaocc.test.po.Blog;

import java.math.BigInteger;
import java.util.List;

public interface FollowService {
    public List<Blog> getfollow(Long userid,int val);
    public boolean follow(Long user_id,Long follow_id);
    public boolean unfollow(Long user_id,Long follow_id);
    public int count(Long userid);
    public int countmygetfollow(Long userid);
    public boolean isfollow(Long user_id,Long follow_id);
}
