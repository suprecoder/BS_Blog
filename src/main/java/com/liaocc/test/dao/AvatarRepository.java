package com.liaocc.test.dao;

import com.liaocc.test.po.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AvatarRepository extends JpaRepository<Avatar,Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into avatar(user_id,img) values (?1,?2)",nativeQuery = true)
    int insertavatar(Long userid,byte[] bytes);

    @Modifying
    @Transactional
    @Query(value = "update avatar set img=?2 where user_id=?1",nativeQuery = true)
    int updateavatar(Long userid,byte[] bytes);

    @Query(value = "select img from avatar where user_id=?1",nativeQuery = true)
    byte[] getavatar(Long userid);

    @Query(value = "select count(*) from avatar where user_id=?1",nativeQuery = true)
    int count(Long userid);
}
