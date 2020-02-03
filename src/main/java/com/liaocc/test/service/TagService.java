package com.liaocc.test.service;

import java.util.List;

public interface TagService {
    List<String> gettags(Long blogid);
    boolean addtags(Long blogid,List<String> tags);
}
