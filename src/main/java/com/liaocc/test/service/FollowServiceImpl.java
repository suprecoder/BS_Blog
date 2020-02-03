package com.liaocc.test.service;

import com.liaocc.test.dao.BlogRepository;
import com.liaocc.test.dao.FollowRepository;
import com.liaocc.test.po.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    BlogRepository blogRepository;
    @Override
    public List<Blog> getfollow(Long userid,int val) {
        return blogRepository.getFollowBlog(userid,(val-1)*8,8);
    }

    @Override
    public boolean follow(Long blog_id, Long user_id) {
        return false;
    }

    @Override
    public boolean unfollow(Long blog_id, Long user_id) {
        return false;
    }

    @Override
    public int count(Long userid) {
        return followRepository.count(userid);
    }

}
