package org.example.security;

import com.alibaba.fastjson.JSONObject;
import org.example.model.SeckillUser;
import org.example.util.SpringbootContextHolder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebUserUtil {
    public static final String SESSION_KEY = "seckill_user_session_key";

    public static SeckillUser getLoginUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        HttpSession session = request.getSession();
        if(session.getAttribute(SESSION_KEY) != null) {
            return (SeckillUser) session.getAttribute(SESSION_KEY);
        } else {
            String token = request.getHeader("token");
            RedisTemplate redisTemplate =  SpringbootContextHolder.getBean("redisTemplate");
            Object obj =  redisTemplate.opsForValue().get(token);
            if(obj == null) {
                return null;
            }
            SeckillUser user = JSONObject.parseObject(obj.toString(), SeckillUser.class);
            session.setAttribute(SESSION_KEY, user);
            return user;
        }
    }
}
