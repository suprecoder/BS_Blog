package com.liaocc.test.dao;

import com.liaocc.test.table.BlogTitleAndHtml;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlogTitleAndHtmlRepository extends JpaRepository<BlogTitleAndHtml,String> {
    @Query(value = "select title,content_html from blog where id=?1",nativeQuery = true)
    BlogTitleAndHtml getHtmlContentAndTitle(Long id);
}
