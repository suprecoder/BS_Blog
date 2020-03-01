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
    public boolean follow(Long user_id,Long follow_id) {
        return followRepository.insert(user_id,follow_id)>0?true:false;
    }

    @Override
    public boolean unfollow(Long user_id,Long follow_id) {
        return followRepository.delete(user_id,follow_id)>0?true:false;
    }

    @Override
    public int count(Long userid) {
        return followRepository.count(userid);
    }

    @Override
    public int countmygetfollow(Long userid) {
        return followRepository.countgetfollow(userid);
    }

    @Override
    public boolean isfollow(Long user_id, Long follow_id) {
        return followRepository.isexist(user_id,follow_id)>0;
    }

    @Override
    public List<BigInteger> getfollowid(Long user_id) {
            return followRepository.getFollowid(user_id);
    }


}
