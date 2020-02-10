package com.liaocc.test.service;

import com.liaocc.test.dao.PreferRepository;
import com.liaocc.test.dao.TagRepository;
import com.liaocc.test.po.Prefer;
import com.liaocc.test.po.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class PreferServiceImpl implements PreferService {
    @Autowired
    PreferRepository preferRepository;
    @Autowired
    TagRepository tagRepository;

    @Override
    public List<BigInteger> getPrefer(Long userid) {
        return preferRepository.getLike(userid);
    }

    @Override
    public boolean unLike(Long blog_id, Long user_id) {
        int b=preferRepository.get(blog_id,user_id);
        if(b==0)
            return true;
        List<Tag> tags=tagRepository.getTagsIdByBlogid(blog_id);
        for(Tag tag:tags){
            tagRepository.update_user_tagnum(user_id,tag.getId(),-2);
        }
        int a=preferRepository.delete(blog_id,user_id);
        if(a==0)return false;
        return true;
    }

    @Override
    public boolean like(Long blog_id, Long user_id) {
        int b=preferRepository.get(blog_id,user_id);
        List<Tag> tags=tagRepository.getTagsIdByBlogid(blog_id);
        for(Tag tag:tags){
            tagRepository.update_user_tagnum(user_id,tag.getId(),2);
        }
        if(b!=0)
            return true;
        int a=preferRepository.insert(blog_id,user_id);
        if(a==0)return false;
        return true;
    }

    @Override
    public int countmygetprefer(Long userid) {
        return preferRepository.countgetprefer(userid);
    }
}
