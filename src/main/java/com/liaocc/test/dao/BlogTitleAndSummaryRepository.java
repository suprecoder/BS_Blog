package com.liaocc.test.dao;

import com.liaocc.test.table.BlogTitleAndHtml;
import com.liaocc.test.table.BlogTitleAndSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlogTitleAndSummaryRepository extends JpaRepository<BlogTitleAndSummary,String> {
    @Query(value = "SELECT id,title,summary,prefer.user_id FROM blog LEFT JOIN prefer on blog.id=prefer.blog_id limit ?1,?2",nativeQuery = true)
    BlogTitleAndSummary getTitleAndSummary(int page,int perpage);
}
