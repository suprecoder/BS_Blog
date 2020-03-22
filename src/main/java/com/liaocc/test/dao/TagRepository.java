package com.liaocc.test.dao;

import com.liaocc.test.po.Tag;
import com.liaocc.test.table.BlogAndTag;
import com.liaocc.test.table.TagidAndNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface TagRepository extends JpaRepository<Tag,Long> {
    @Query(value = "select name from tag where id in (select tag_id from cnt_blog_tag where blog_id=?1)",nativeQuery = true)
    List<String> getTags(Long blogid);

    @Query(value = "select * from tag where id in (select tag_id from cnt_blog_tag where blog_id=?1)",nativeQuery = true)
    List<Tag> getTagsIdByBlogid(Long blogid);

    @Query(value = "select id from tag where name=?1",nativeQuery = true)
    Long getTagId(String name);

    @Modifying
    @Transactional
    @Query(value = "insert into tag(name) values (?1)",nativeQuery = true)
    void insertTag(String name);

    @Modifying
    @Transactional
    @Query(value = "insert into cnt_blog_tag(blog_id,tag_id) values (?1,?2) on DUPLICATE key update tag_id=VALUES(tag_id)",nativeQuery = true)
    void insertTagToBlog(Long blogid,Long tagid);

    @Query(value = "select name from tag",nativeQuery = true)
    List<String> getAllTagsName();

    @Query(value = "select id from tag",nativeQuery = true)
    List<BigInteger> getAllTagsId();

    @Query(value = "select * from cnt_blog_tag",nativeQuery = true)
    List<BlogAndTag> getAllTagsAndBlog();

    @Query(value = "select id from tag where id in (select tag_id from cnt_blog_tag WHERE blog_id in (SELECT id from blog where user_id=?1))",nativeQuery = true)
    List<Long> getUser_Tag(Long userid);

    @Query(value = "select name from cnt_blog_tag,tag where tag_id=id GROUP BY tag_id order by count(*) desc limit 1,10",nativeQuery = true)
    List<String> getHotTags();

    @Modifying
    @Transactional
    @Query(value = "insert into user_tagnum(user_id,tag_id,num) VALUES(?1,?2,?3) on DUPLICATE key update num=num+VALUES(num)",nativeQuery = true)
    void update_user_tagnum(Long userid,Long tagid,int num);

    //根据首字母查询标签名
    /*
    DROP FUNCTION IF EXISTS `GET_FIRST_PINYIN_CHAR`;
CREATE FUNCTION `GET_FIRST_PINYIN_CHAR`(PARAM VARCHAR(255)) RETURNS VARCHAR(2) CHARSET utf8
BEGIN
	DECLARE V_RETURN VARCHAR(255);
	DECLARE V_FIRST_CHAR VARCHAR(2);
	SET V_FIRST_CHAR = UPPER(LEFT(PARAM,1));
	SET V_RETURN = V_FIRST_CHAR;
	IF LENGTH( V_FIRST_CHAR) <> CHARACTER_LENGTH( V_FIRST_CHAR ) THEN
	SET V_RETURN = ELT(INTERVAL(CONV(HEX(LEFT(CONVERT(PARAM USING gbk),1)),16,10),
		0xB0A1,0xB0C5,0xB2C1,0xB4EE,0xB6EA,0xB7A2,0xB8C1,0xB9FE,0xBBF7,
		0xBFA6,0xC0AC,0xC2E8,0xC4C3,0xC5B6,0xC5BE,0xC6DA,0xC8BB,
		0xC8F6,0xCBFA,0xCDDA,0xCEF4,0xD1B9,0xD4D1),
	'A','B','C','D','E','F','G','H','J','K','L','M','N','O','P','Q','R','S','T','W','X','Y','Z');
	END IF;
	RETURN V_RETURN;
END
     */
    @Query(nativeQuery = true,value = "select name from tag where get_first_pinyin_char(name) = ?1")
    List<String> getTagByFirstLitter(char c);
}
