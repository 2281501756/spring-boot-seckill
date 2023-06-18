package org.example.util;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
public class DecrCacheStock {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private DefaultRedisScript<Long> redisScript;

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setLocation(new ClassPathResource("lua/DecrCacheStock.lua"));
    }

    public  Long decr(Long id) {
        return redisTemplate.execute(redisScript, Lists.newArrayList("cache:p:"+ id ));
    }
    public void incr(Long id) {
        redisTemplate.opsForValue().increment("cache:p:"+ id );
    }
}
