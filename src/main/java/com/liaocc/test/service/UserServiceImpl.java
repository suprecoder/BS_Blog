package com.liaocc.test.service;

import com.liaocc.test.dao.UserRepository;
import com.liaocc.test.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserbyname(String name) {
        return userRepository.getUserByname(name);
    }

    @Override
    public boolean login(String username, String psw) {
        User user=userRepository.getbyUsernameAndPsw(username,psw);
        if(user!=null){
            return true;
        }
        return false;
    }
    @Override
    @Transactional
    public boolean register(String name,String psw){
        boolean flag=verify(name);
        if(flag) {
            int a = userRepository.saveUser(name, psw);
            if (a == 1)
                return true;
        }
        return false;
    }

    @Override
    public boolean verify(String username) {
        Long a=userRepository.isexist(username);
        System.out.println(a);
        if(a.equals(0L))
            return true;
        return false;
    }

    @Override
    public User getUserByid(Long id) {
        return userRepository.getUserByid(id);
    }
}
