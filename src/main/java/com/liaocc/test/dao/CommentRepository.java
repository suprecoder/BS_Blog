package com.liaocc.test.dao;

import com.liaocc.test.po.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>{
    @Query(value = "select *from comment where blog_id=?1 order by id",nativeQuery = true)
    List<Comment> getByblogid(Long blogid);

    @Modifying
    @Transactional
    @Query(value = "insert into comment(blog_id,user_id,reply_user_id,comment) values (?1,?2,?3,?4)",nativeQuery = true)
    int insert(Long blogid,Long userid,Long reply_userid,String comment);

    @Modifying
    @Transactional
    @Query(value = "delete from comment where id=?1",nativeQuery = true)
    void delete(Long id);
}