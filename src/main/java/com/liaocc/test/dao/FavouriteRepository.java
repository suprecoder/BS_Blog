package com.liaocc.test.dao;

import com.liaocc.test.po.Favourite;
import com.liaocc.test.po.key.UseridAndBlogidKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, UseridAndBlogidKey> {
    @Query(value = "SELECT blog_id from favourite where user_id=?1",nativeQuery = true)
    List<BigInteger> getFavourite(Long userid);

    @Modifying
    @Transactional
    @Query(value = "delete from favourite where blog_id=?1 and user_id=?2",nativeQuery = true)
    int delete(Long blog_id,Long user_id);

    @Modifying
    @Transactional
    @Query(value = "insert into favourite(blog_id,user_id) values(?1,?2)",nativeQuery = true)
    int insert(Long blog_id,Long user_id);

    @Query(value = "select count(*) from favourite where blog_id=?1 and user_id=?2",nativeQuery = true)
    int get(Long blog_id,Long user_id);

    //我收藏的博客数量
    @Query(value = "select count(*) from favourite where user_id=?1",nativeQuery = true)
    int count(Long user_id);

    //我收获的收藏
    @Query(value = "select count(*) from favourite where blog_id in (select id from blog where user_id=?1)",nativeQuery = true)
    int countgetfavourite(Long user_id);
}
