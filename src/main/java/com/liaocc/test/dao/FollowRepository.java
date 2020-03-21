package com.liaocc.test.dao;

import com.liaocc.test.po.Follow;
import com.liaocc.test.po.key.UseridAndFollowid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, UseridAndFollowid> {
    @Query(value = "select follow_id from follow where user_id=?1",nativeQuery = true)
    List<BigInteger> getFollow(Long userid);

    //我关注的人的博客数量
    @Query(value = "select count(*) from blog where user_id in (SELECT follow_id from follow where user_id=?1) and publictype=1",nativeQuery = true)
    int count(Long userid);

    //我收获的关注
    @Query(value = "select count(*) from follow where follow_id =?1",nativeQuery = true)
    int countgetfollow(Long user_id);

    @Modifying
    @Transactional
    @Query(value = "delete from follow where user_id=?1 and follow_id=?2",nativeQuery = true)
    int delete(Long user_id,Long follow_id);

    @Modifying
    @Transactional
    @Query(value = "insert into follow(user_id,follow_id) values(?1,?2)",nativeQuery = true)
    int insert(Long user_id,Long follow_id);

    //我是否关注了某人
    @Query(value = "select count(*) from follow where user_id=?1 and follow_id=?2",nativeQuery = true)
    int isexist(Long user_id,Long follow_id);

    //我关注的人的的id
    @Query(value = "select follow_id from follow where user_id=?1",nativeQuery = true)
    List<BigInteger> getFollowid(Long user_id);

}
