package com.liaocc.test.service;

import com.liaocc.test.dao.CommentRepository;
import com.liaocc.test.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public List<Comment> getcomment(Long blogid) {
        return commentRepository.getByblogid(blogid);
    }

    @Override
    public boolean publishcomment(Long blogid, Long userid, Long reply_userid, String comment) {
        commentRepository.insert(blogid,userid,reply_userid,comment);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        commentRepository.delete(id);
        return true;
    }
}
