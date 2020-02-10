package com.liaocc.test.dao;

import com.liaocc.test.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "select * from user where username=?1 and psw=?2",nativeQuery = true)
    User getbyUsernameAndPsw(String name,String psw);

    @Modifying
    @Transactional
    @Query(value = "insert into user(username,psw) values(?1,?2)",nativeQuery = true)
    int saveUser(String name,String psw);

    @Query(value = "select count(*) from user where username=?1",nativeQuery = true)
    Long isexist(String username);

    @Query(value = "select *from user where username=?1",nativeQuery = true)
    User getUserByname(String name);

    @Query(value = "select *from user where id=?1",nativeQuery = true)
    User getUserByid(Long id);

    @Query(value = "select *from user",nativeQuery = true)
    List<User> getAllUsers();
}
