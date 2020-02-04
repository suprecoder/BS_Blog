package com.liaocc.test.dao;

import com.liaocc.test.table.PersonalMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PresonMsgRepository extends JpaRepository<PersonalMsg,Long> {
    @Query(value = "select *from user where id=?1",nativeQuery = true)
    PersonalMsg get(Long userid);

    @Modifying
    @Transactional
    @Query(value = "update user set sex=?1,mail=?2,phonenum=?3,address=?4,job=?5,mydescribe=?6 where id=?7",nativeQuery = true)
    Integer save(String sex,String mail,String phonenum,String address,String job,String describe,Long id);
}
