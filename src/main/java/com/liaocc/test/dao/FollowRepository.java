package com.liaocc.test.dao;

import com.liaocc.test.po.Follow;
import com.liaocc.test.po.key.UseridAndFollowid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, UseridAndFollowid> {
    @Query(value = "select follow_id from follow where user_id=?1",nativeQuery = true)
    List<BigInteger> getFollow(Long userid);

    //我关注的人的博客数量
    @Query(value = "select count(*) from blog where user_id in (SELECT follow_id from follow where user_id=?1)",nativeQuery = true)
    int count(Long userid);

    //我收获的关注
    @Query(value = "select count(*) from follow where follow_id in (select id from blog where user_id=?1)",nativeQuery = true)
    int countgetfollow(Long user_id);
}
