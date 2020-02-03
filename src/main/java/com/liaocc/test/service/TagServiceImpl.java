package com.liaocc.test.service;

import com.liaocc.test.dao.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;
    @Override
    public List<String> gettags(Long blogid) {
        return tagRepository.getTags(blogid);
    }

    @Override
    @Transactional
    public boolean addtags(Long blogid,List<String> tags) {
        for(String tag:tags){
            Long tagid=tagRepository.getTagId(tag);
            if(tagid==null) {
                tagRepository.insertTag(tag);
                tagid=tagRepository.getTagId(tag);
            }

            tagRepository.insertTagToBlog(blogid,tagid);
        }
        return true;
    }
}
