package com.liaocc.test.service;

import com.liaocc.test.dao.*;
import com.liaocc.test.po.Blog;
import com.liaocc.test.po.Tag;
import com.liaocc.test.po.User;
import com.liaocc.test.redis.RedisUtils;
import com.liaocc.test.table.BlogAndTag;
import com.liaocc.test.table.BlogTitleAndHtml;
import com.liaocc.test.table.TagidAndNum;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.sql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.OpenJpaDialect;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BlogServiceImpl implements BlogService {
    private static final int itemPerPage = 8;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    BlogTitleAndHtmlRepository blogTitleAndHtmlRepository;
    @Autowired
    TagService tagService;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Blog> listblog() {
        return blogRepository.listblog();
    }

    @Override
    public Blog saveblog(Blog blog) {
        Blog b= blogRepository.saveAndFlush(blog);
        redisUtils.set("blog"+b.getId().toString(),b,2l, TimeUnit.HOURS);
        return b;
    }

    @Override
    public Blog getblog(Long blogid) {
        // getRecommandBlog(1l);

        Blog b= (Blog) redisUtils.get("blog"+blogid.toString());
        if(b!=null){
            System.out.println("------from redis--------");
            redisUtils.setexpire("blog"+blogid.toString(),2l,TimeUnit.HOURS);
            return b;
        }
        else{
            System.out.println("------from mysql--------");
            b=blogRepository.getblog(blogid);

            redisUtils.set("blog"+blogid.toString(),b,2l, TimeUnit.HOURS);
        }
        return b;
    }

    @Override
    public List<Blog> listblogbyname(String username,int page) {
        return blogRepository.listblogbyusername(username,(page-1)*itemPerPage);
    }

    @Override
    public List<Blog> listbloginpage(int page) {
        return blogRepository.listinpage((page-1)*itemPerPage,itemPerPage);
    }

    //推荐博客
    @Override
    public Long countAllRecommand(Long userid) {
        Integer num=RecommandBlogSize.get(userid);
        if(num==null)  return (long)getRecommandBlog(userid).size();
        return RecommandBlogSize.get(userid).longValue();
    }

    //某个用户有多少博客
    @Override
    public Long countAll(Long userid) {
        return blogRepository.countAll(userid);
    }

    @Override
    public Blog getBlogByUserAndTitle(User user,String title) {
        return blogRepository.getBlogByUserAndTitle(user,title);
    }

    @Override
    public BlogTitleAndHtml getHtmlContent(Long id) {
        return blogTitleAndHtmlRepository.getHtmlContentAndTitle(id);
    }

    @Override
    public String getSummary(Long blogid) {
        StringBuilder summary=new StringBuilder();
        Blog b=getblog(blogid);
        return toSummary(b);
    }
    @Override
    public String toSummary(Blog b) {
        if(b.getSummary()==null || b.getSummary()==""){
            StringBuilder temp=new StringBuilder();
            for(int i=0;i<Math.min(90,b.getContent_md().length());i++)
                temp.append(b.getContent_md().charAt(i));
            if(b.getContent_md().length()>90)
                for(int i=0;i<3;i++)
                    temp.append(".");
            b.setSummary(temp.toString());
        }
        return b.getSummary();
    }

    @Override
    public boolean delete(Long blogid, Long userid) {
        int temp= blogRepository.delete(blogid,userid);
        if(temp==0)return false;
        return true;
    }
    @Autowired
    TagRepository tagRepository;

    class cmp implements Comparable{
        User user;
        double point;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public double getPoint() {
            return point;
        }

        @Override
        public String toString() {
            return "cmp{" +
                    "user=" + user +
                    ", point=" + point +
                    '}';
        }

        public void setPoint(double point) {
            this.point = point;
        }

        public cmp(User user, double point) {
            this.user = user;
            this.point = point;
        }

        @Override
        public int compareTo(Object o) {
            cmp temp=(cmp)o;
            if(this.point>temp.point)return -1;
            else if(this.point<temp.point)return 1;
            return 0;
        }
    }

    private Map<Long,Integer> ListToMap(List<TagidAndNum> list){
        HashMap<Long,Integer> hm=new HashMap<>();
        for(TagidAndNum t:list){
            hm.put(t.getUseridAndTagidKey().getTag_id(),t.getNum());
        }
        return hm;
    }

    @Override
    public List<Blog> getRecommandBlogInpage(Long userid,int page) {
        List<Blog> blogs= (List<Blog>) redisUtils.get("recommandblog"+userid.toString());
        if(blogs==null)
            blogs=getRecommandBlog(userid);
        redisUtils.setexpire("recommandblog"+userid.toString(),20l,TimeUnit.MINUTES);
        return blogs.subList((page-1)*8,Math.min(((page-1)*8+8),blogs.size()));

    }

    private Map<Long,Integer> RecommandBlogSize=new HashMap<>();

    @Autowired
    User_TagNumRepository user_tagNumRepository;
    @Override
    public List<Blog> getRecommandBlog(Long userid) {
        List<User> users=userRepository.getAllUsers();
        User me=userRepository.getUserByid(userid);
        Map<Long,Integer> mytaginfo=ListToMap(user_tagNumRepository.getTagidAndNum(me.getId()));
        List<BigInteger> tagsid=tagRepository.getAllTagsId();
        cmp[] CMP=new cmp[users.size()];
        int index=0;
        for(User user:users){
            if(!user.getId().equals(me.getId())){
                Long id=user.getId();
                List<TagidAndNum> tan=user_tagNumRepository.getTagidAndNum(id);
                System.out.println("tan:  "+tan);
                Map<Long,Integer> taginfo=ListToMap(tan);
                double sum1=0,sum2=0,sum3=0;
                for(int i=0;i<tagsid.size();i++){
                    Long temmp=tagsid.get(i).longValue();
                    Integer aa=mytaginfo.get(temmp);
                    Integer bb=taginfo.get(temmp);
                    double a,b;
                    if(aa==null)a=0;else a=aa;
                    if(bb==null)b=0;else b=bb;
                    sum1+=a*a;
                    sum2+=b*b;
                    sum3+=a*b;
                }
                if(sum1<1e-4 || sum2<1e-4)
                    CMP[index++]=new cmp(user,0);
                else
                    CMP[index++]=new cmp(user,sum3/(Math.sqrt(sum1)*Math.sqrt(sum2)));
            }
        }
        Arrays.sort(CMP,0,CMP.length-1);
        Long count=blogRepository.countAll();
        Set<Long> hashset=new HashSet<>();
        List<Blog> ans=new ArrayList<>();
        for(int i=0;i<CMP.length-1;i++){
            List<Blog> blogs=blogRepository.getFavouriteBlog(CMP[i].user.getId(),0,count.intValue());
            for(Blog blog:blogs){
                redisUtils.set("blog"+blog.getId().toString(),blog,2l,TimeUnit.HOURS);
                if(!hashset.contains(blog.getId()))
                    ans.add(blog);
                hashset.add(blog.getId());
            }
            blogs=blogRepository.getPreferBlog(CMP[i].user.getId(),0,count.intValue());
            for(Blog blog:blogs){
                redisUtils.set("blog"+blog.getId().toString(),blog,2l,TimeUnit.HOURS);
                if(!hashset.contains(blog.getId()))
                    ans.add(blog);
                hashset.add(blog.getId());
            }

        }
        for(int i=0;i<CMP.length-1;i++) {
            List<Blog> blogs = blogRepository.listblogbyusername(CMP[i].user.getUsername());
            for (Blog blog : blogs) {
                redisUtils.set("blog" + blog.getId().toString(), blog, 2l, TimeUnit.HOURS);
                if (!hashset.contains(blog.getId()))
                    ans.add(blog);
                hashset.add(blog.getId());
            }
        }
        Random rn=new Random();
        for(int i=0;i<ans.size()/8;i++){
            int pa=Math.abs(rn.nextInt())%ans.size();
            int pb=Math.abs(rn.nextInt())%ans.size();
            Blog a=ans.get(pa);
            ans.set(pa,ans.get(pb));
            ans.set(pb,a);
        }

        //Object[] hash=hashset.toArray();

//        for(int i=0;i<tags.size();i++){
//            Long blogid=tags.get(i).getBlogidAndTagidKey().getBlog_id();
//            List<String> temp=tagService.gettags(blogid);
//
//            if(!getblog(blogid).getUser().getId().equals(userid)){
//
//            }
//        }
//        for(int i=0;i<hash.length;i++){
//            ans.add(getblog((Long)hash[i]));
//        }
        RecommandBlogSize.put(userid,ans.size());
        return ans;
    }

}