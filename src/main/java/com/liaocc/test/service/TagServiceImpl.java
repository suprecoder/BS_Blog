package com.liaocc.test.service;

import com.liaocc.test.dao.TagRepository;
import com.liaocc.test.redis.RedisUtils;
import com.liaocc.test.table.BlogAndTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service

public class TagServiceImpl implements TagService {
    @Autowired
    RedisUtils redisUtils;
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

    @Override
    public List<String> getAllTagsName() {
        return tagRepository.getAllTagsName();
    }

    class Sort implements Comparable{
        String name;
        int length;

        public Sort(String name, int length) {
            this.name = name;
            this.length = length;
        }

        @Override
        public int compareTo(Object o) {
            Sort temp=(Sort)o;
            if(this.length>temp.length)return -1;
            else if(this.length<temp.length) return 1;
            return 0;
        }
    }

    @Override
    public List<String> getRecommandTagsName(String name) {
        name=name.toLowerCase();
        List<String> names=getAllTagsName();
        System.out.println("recommand"+name);
        System.out.println("recommand"+names);
        List<String> ans=new ArrayList<>();
        Sort[] sort=new Sort[names.size()];
        for(int j=0;j<names.size();j++){
            String a=names.get(j).toLowerCase();
            int[] b=new int[a.length()];
            int index=0;
            b[0]=0;
            if(a.charAt(0)==name.charAt(0)){
                b[0]=1;
                index=1;
            }
            for(int i=1;i<a.length();i++){
                b[i]=b[i-1];
                if(index>=name.length())
                    continue;
                if(a.charAt(i)==name.charAt(index)){
                    index++;
                    b[i]++;
                }
            }
            sort[j]=new Sort(a,b[a.length()-1]);
        }
        Arrays.sort(sort);
        for(int i=0;i<Math.min(sort.length,3);i++){
            if(sort[i].length>0)
                ans.add(sort[i].name);
        }
        return ans;
    }

    @Override
    public List<BlogAndTag> getAllTagsAndBlog() {
        return tagRepository.getAllTagsAndBlog();
    }

    //这个user有哪些tag
    @Override
    public List<Long> User_Tag(Long userid){
        return tagRepository.getUser_Tag(userid);
    }
}
