package com.liaocc.test.dao;

import com.liaocc.test.po.Tag;
import com.liaocc.test.table.BlogAndTag;
import com.liaocc.test.table.TagidAndNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface TagRepository extends JpaRepository<Tag,Long> {
    @Query(value = "select name from tag where id in (select tag_id from cnt_blog_tag where blog_id=?1)",nativeQuery = true)
    List<String> getTags(Long blogid);

    @Query(value = "select * from tag where id in (select tag_id from cnt_blog_tag where blog_id=?1)",nativeQuery = true)
    List<Tag> getTagsIdByBlogid(Long blogid);

    @Query(value = "select id from tag where name=?1",nativeQuery = true)
    Long getTagId(String name);

    @Modifying
    @Transactional
    @Query(value = "insert into tag(name) values (?1)",nativeQuery = true)
    void insertTag(String name);

    @Modifying
    @Transactional
    @Query(value = "insert into cnt_blog_tag(blog_id,tag_id) values (?1,?2) on DUPLICATE key update tag_id=VALUES(tag_id)",nativeQuery = true)
    void insertTagToBlog(Long blogid,Long tagid);

    @Query(value = "select name from tag",nativeQuery = true)
    List<String> getAllTagsName();

    @Query(value = "select id from tag",nativeQuery = true)
    List<BigInteger> getAllTagsId();

    @Query(value = "select * from cnt_blog_tag",nativeQuery = true)
    List<BlogAndTag> getAllTagsAndBlog();

    @Query(value = "select id from tag where id in (select tag_id from cnt_blog_tag WHERE blog_id in (SELECT id from blog where user_id=?1))",nativeQuery = true)
    List<Long> getUser_Tag(Long userid);



    @Modifying
    @Transactional
    @Query(value = "insert into user_tagnum(user_id,tag_id,num) VALUES(?1,?2,?3) on DUPLICATE key update num=num+VALUES(num)",nativeQuery = true)
    void update_user_tagnum(Long userid,Long tagid,int num);
}
