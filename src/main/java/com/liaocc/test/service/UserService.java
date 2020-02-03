package com.liaocc.test.service;

import com.liaocc.test.po.User;

public interface UserService {
    User getUserbyname(String name);
    boolean login(String name,String psw);
    public boolean register(String username,String psw);
    public boolean verify(String username);
}
