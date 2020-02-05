package com.liaocc.test.service;

import com.liaocc.test.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getcomment(Long blogid);
    boolean publishcomment(Long blogid,Long userid,Long reply_userid,String comment);
    boolean delete(Long id);

}
