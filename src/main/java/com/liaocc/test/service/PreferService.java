package com.liaocc.test.service;

import com.liaocc.test.po.Prefer;

import java.math.BigInteger;
import java.util.List;

public interface PreferService {
    public List<BigInteger> getPrefer(Long userid);
    public boolean unLike(Long blog_id,Long user_id);
    public boolean like(Long blog_id,Long user_id);
    public int countmygetprefer(Long userid);
}
