package com.liaocc.test.dao;

import com.liaocc.test.po.Blog;
import com.liaocc.test.po.User;
import com.liaocc.test.table.BlogTitleAndHtml;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    @Query(value = "select * from blog",nativeQuery = true)
     List<Blog> listblog();

    @Override
    Blog saveAndFlush(Blog b);

    @Query(value = "select * from blog where user_id=(select id from user where username=?1) order by blog.id limit ?2,8",nativeQuery = true)
    List<Blog> listblogbyusername(String username,int item);

    @Query(value = "(select * from blog where user_id=(select id from user where username=?1) order by blog.id)",nativeQuery = true)
    List<Blog> listblogbyusername(String username);

    //?1请求第几页,?2每页有多少条目
    @Query(value = "select *from blog order by id limit ?1,?2 ",nativeQuery = true)
    List<Blog> listinpage(int a,int b);

    @Query(value = "select count(*) from blog",nativeQuery = true)
    Long countAll();

    @Query(value = "select count(*) from blog where user_id=?1",nativeQuery = true)
    Long countAll(Long user_id);

    Blog getBlogByUserAndTitle(User user,String title);

    @Query(value = "select * from blog where id=?1",nativeQuery = true)
    Blog getblog(Long blog_id);

    @Query(value = "select * from blog where id in (SELECT blog_id from favourite where user_id=?1) and publictype=1 order by id limit ?2,?3",nativeQuery = true)
    List<Blog> getFavouriteBlog(Long userid,int a,int b);

    @Query(value = "select * from blog where id in (SELECT blog_id from prefer where user_id=?1) and publictype=1 order by id limit ?2,?3",nativeQuery = true)
    List<Blog> getPreferBlog(Long userid,int a,int b);

    @Query(value = "select * from blog where user_id in (SELECT follow_id from follow where user_id=?1) and publictype=1 order by id limit ?2,?3",nativeQuery = true)
    List<Blog> getFollowBlog(Long userid,int a,int b);

    @Modifying
    @Transactional
    @Query(value = "delete from blog where id=?1 and user_id=?2",nativeQuery = true)
    int delete(Long blogid,Long userid);

    @Query(value = "select *from blog where content_md like %?1% OR title like %?1% OR summary like %?1% or id in (SELECT blog_id from cnt_blog_tag where tag_id in (SELECT id from tag where name like %?1%)) or user_id in (select id from user where username like %?1%)",nativeQuery = true)
    List<Blog> search(String queryString);

    @Query(value = "select *from blog where id in (select blog_id from cnt_blog_tag where tag_id in (SELECT id from tag where name=?1)) and publictype=1",nativeQuery = true)
    List<Blog> getBlogByTag(String tag);
}
