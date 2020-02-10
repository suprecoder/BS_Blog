package com.liaocc.test.redis;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

@Configuration
@RefreshScope
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10800)
public class RedisSessionConfig{
    @Value("${spring.redis2.host}")
    private String host;
    @Value("${spring.redis2.port}")
    private int port;
    @Value("${spring.redis2.timeout}")
    private int timeout;
    @Value("${spring.redis2.password}")
    private String password;
    @Value("${spring.redis2.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis2.pool.max-wait}")
    private int maxWait;
    @Value("${spring.redis2.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis2.pool.min-idle}")
    private int minIdle;

    @RefreshScope
    @Bean("keyGenerator2")
    public KeyGenerator wiselyKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

//    @Bean
//    public RedisHttpSessionConfiguration sessionConfiguration(@Qualifier("factory2")JedisConnectionFactory factory){
//
//        return redisHttpSessionConfiguration;
//    }


}
