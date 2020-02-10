package com.liaocc.test.dao;

import com.liaocc.test.table.TagidAndNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface User_TagNumRepository extends JpaRepository<TagidAndNum,Long> {
    //获取某个用户使用的标签和其数量
    @Query(value = "SELECT * FROM user_tagnum WHERE user_id=?1",nativeQuery = true)
    List<TagidAndNum> getTagidAndNum(Long userid);
}
