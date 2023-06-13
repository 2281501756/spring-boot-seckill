package org.example.security;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.example.base.BaseResponse;
import org.example.exception.ErrorMessage;
import org.example.model.SeckillUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@WebFilter(filterName = "UserLoginFilter", urlPatterns = "/*")
public class UserLoginFilter implements Filter {
    private static Set<String> waitList = new HashSet<>();

    static  {
        waitList.add("/user/sms/code");
        waitList.add("/user/login");
    }
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String url = request.getRequestURI();
        System.out.println(url);
        if(waitList.contains(url)) {
            filterChain.doFilter(request, response);
            return;
        }
        if(session.getAttribute(WebUserUtil.SESSION_KEY) != null) {
            filterChain.doFilter(request, response);
        } else if(request.getHeader("token") != null) {
            Object obj = redisTemplate.opsForValue().get(request.getHeader("token"));
            if(obj == null) {
                returnJson(response);
                return;
            }
            SeckillUser user = JSONObject.parseObject(obj.toString(), SeckillUser.class);
            session.setAttribute(WebUserUtil.SESSION_KEY, user);
            filterChain.doFilter(request, response);
        } else {
            returnJson(response);
        }
    }

    private void returnJson(ServletResponse response) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            BaseResponse baseResponse = new BaseResponse(304,"用户登录过期", null);
            writer.print(JSON.toJSONString(baseResponse));
        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
