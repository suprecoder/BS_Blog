package com.liaocc.test.service;

import com.liaocc.test.dao.FavouriteRepository;
import com.liaocc.test.po.Favourite;
import com.liaocc.test.po.Prefer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
@Service
public class FavouriteServiceImpl implements FavouriteService {
    @Autowired
    FavouriteRepository favouriteRepository;
    @Override
    public List<BigInteger> getFavourite(Long userid)  {
        return favouriteRepository.getFavourite(userid);
    }

    @Override
    public boolean unfavourite(Long blog_id, Long user_id) {
        int b=favouriteRepository.get(blog_id,user_id);
        if(b==0)
            return true;
        int a=favouriteRepository.delete(blog_id,user_id);
        if(a==0)return false;
        return true;
    }

    @Override
    public boolean favourite(Long blog_id, Long user_id) {
        int b=favouriteRepository.get(blog_id,user_id);
        if(b!=0)
            return true;
        int a=favouriteRepository.insert(blog_id,user_id);
        System.out.println("a:"+a);
        if(a==0)return false;
        return true;
    }
}
