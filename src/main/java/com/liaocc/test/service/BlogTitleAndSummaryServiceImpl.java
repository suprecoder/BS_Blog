package com.liaocc.test.service;

import com.liaocc.test.dao.BlogTitleAndSummaryRepository;
import com.liaocc.test.table.BlogTitleAndSummary;
import org.springframework.beans.factory.annotation.Autowired;

public class BlogTitleAndSummaryServiceImpl implements BlogTitleAndSummaryService{
    @Autowired
    BlogTitleAndSummaryRepository blogTitleAndSummaryRepository;
    @Override
    public BlogTitleAndSummary getTitleAndSummary(int page) {
        return blogTitleAndSummaryRepository.getTitleAndSummary((page-1)*8,8);
    }
}
