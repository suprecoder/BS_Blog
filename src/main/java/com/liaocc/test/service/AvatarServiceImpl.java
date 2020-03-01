package com.liaocc.test.service;

import com.liaocc.test.dao.AvatarRepository;
import com.liaocc.test.dao.BlogRepository;
import com.liaocc.test.dao.UserRepository;
import com.liaocc.test.po.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class AvatarServiceImpl implements AvatarService {
    @Autowired
    AvatarRepository avatarRepository;
    @Autowired
    BlogRepository blogRepository;
    @Override
    public InputStream getAvatar(Long userid){
        int count=avatarRepository.count(userid);
        if(count==0)
            userid=4l;
        System.out.println("userid: "+userid);
        byte[] bytes=null;
        bytes=avatarRepository.getavatar(userid);
        MultipartFile file = new MockMultipartFile("avatar","avatar","image/jpeg",bytes);
        InputStream is=null;
        try{
            is=file.getInputStream();
        }catch (Exception e){e.printStackTrace();}
        return is;
    }

    @Override
    @Transactional
    public void saveAvatar(Long userid, MultipartFile file) {
        try{
            byte[] bytes=file.getBytes();
            int temp=avatarRepository.count(userid);
            if(temp==0)
                avatarRepository.insertavatar(userid,bytes);
            else avatarRepository.updateavatar(userid,bytes);
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public InputStream getAvatarbyUser(Long userid) {
        return getAvatar(userid);
    }

    @Override
    public InputStream getAvatarbyBlog(Long blogid){
        Blog blog=blogRepository.getblog(blogid);
        Long userid=blog.getUser().getId();
        return getAvatar(userid);
    }
}
