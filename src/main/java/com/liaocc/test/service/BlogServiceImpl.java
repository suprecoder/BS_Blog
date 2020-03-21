package com.liaocc.test.service;

import com.liaocc.test.dao.*;
import com.liaocc.test.po.Blog;
import com.liaocc.test.po.Tag;
import com.liaocc.test.po.User;
import com.liaocc.test.redis.RedisUtils;
import com.liaocc.test.table.BlogAndTag;
import com.liaocc.test.table.BlogTitleAndHtml;
import com.liaocc.test.table.BlogTitleAndSummary;
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
        List<Long> blogs= (List<Long>) redisUtils.get("recommandblog"+userid.toString());
        if(blogs==null) {
            blogs = getRecommandBlog(userid);
            redisUtils.set("recommandblog" + userid.toString(), blogs, 20l, TimeUnit.MINUTES);
        }
        List<Blog> ans=new ArrayList<>();
        for(int i=(page-1)*8;i<Math.min(((page-1)*8+8),blogs.size());i++){
            ans.add(getblog(blogs.get(i)));
        }

        return ans;

    }

    private Map<Long,Integer> RecommandBlogSize=new HashMap<>();

    @Autowired
    User_TagNumRepository user_tagNumRepository;
    @Override
    public List<Long> getRecommandBlog(Long userid) {
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
        System.out.println(CMP.length);
        Arrays.sort(CMP,0,CMP.length-1);

        Long count=blogRepository.countAll();
        Set<Long> hashset=new HashSet<>();
        List<Long> ans=new ArrayList<>();
        for(int i=0;i<CMP.length-1;i++){
            List<Blog> blogs=blogRepository.getFavouriteBlog(CMP[i].user.getId(),0,count.intValue());
            List<Blog> finalBlogs1 = blogs;
            for(Blog blog:blogs){
                //redisUtils.set("blog"+blog.getId().toString(),blog,2l,TimeUnit.HOURS);
                if(!hashset.contains(blog.getId()))
                    ans.add(blog.getId());
                hashset.add(blog.getId());
            }
            blogs=blogRepository.getPreferBlog(CMP[i].user.getId(),0,count.intValue());
            List<Blog> finalBlogs = blogs;
            for(Blog blog:blogs){
                //redisUtils.set("blog"+blog.getId().toString(),blog,2l,TimeUnit.HOURS);
                if(!hashset.contains(blog.getId()))
                    ans.add(blog.getId());
                hashset.add(blog.getId());
            }
            if(ans.size()>=100){
                break;
            }
        }
        for(int i=0;i<CMP.length-1;i++) {
            if(ans.size()>=100)break;
            List<Blog> blogs = blogRepository.listblogbyusername(CMP[i].user.getUsername());
            for (Blog blog : blogs) {
		if(blog.getPublictype().equals(0))continue;
                //redisUtils.set("blog" + blog.getId().toString(), blog, 2l, TimeUnit.HOURS);
                if (!hashset.contains(blog.getId()))
                    ans.add(blog.getId());
                hashset.add(blog.getId());
            }
            if(ans.size()>=100){
                break;
            }
        }
        RecommandBlogSize.put(userid,ans.size());
        return ans;
    }

    @Autowired
    PreferService preferService;
    @Autowired
    FavouriteService favouriteService;
    @Autowired
    UserService userService;

    @Override
    public List<BlogTitleAndSummary> toTitleAndSummary(List<Blog> blogs,Long userid) {
        List<BlogTitleAndSummary> ans=new ArrayList<>();
        List<BigInteger> prefer=preferService.getPrefer(userid);
        List<BigInteger> favourite=favouriteService.getFavourite(userid);
        int ii=0;
        for(Blog b:blogs) {
            b.setSummary(getSummary(b.getId()));
            BlogTitleAndSummary temp = new BlogTitleAndSummary();
            temp.setId(b.getId());
            temp.setTitle(b.getTitle());
            temp.setSummary(b.getSummary());
            temp.setLike(false);
            temp.setFavourite(false);
            temp.setTags(tagService.gettags(b.getId()));
            for(int i=0;i<prefer.size();i++){
                if(prefer.get(i).longValue()==b.getId().longValue()){
                    temp.setLike(true);
                }
            }
            for(int i=0;i<favourite.size();i++){
                if(favourite.get(i).longValue()==b.getId().longValue()){
                    temp.setFavourite(true);
                }
            }
            temp.setWriter(getblog(b.getId()).getUser().getUsername());
            ans.add(temp);
        }
        return ans;
    }

    @Override
    public List<Blog> search(String queryString) {
        return blogRepository.search(queryString);
    }

}
