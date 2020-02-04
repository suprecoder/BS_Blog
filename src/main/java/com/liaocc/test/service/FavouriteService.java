package com.liaocc.test.service;

import com.liaocc.test.po.Favourite;
import com.liaocc.test.po.Prefer;

import java.math.BigInteger;
import java.util.List;

public interface FavouriteService {
    public List<BigInteger> getFavourite(Long userid);
    public boolean unfavourite(Long blog_id,Long user_id);
    public boolean favourite(Long blog_id,Long user_id);
    public int countmygetfavourite(Long userid);

}
