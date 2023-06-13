package org.example.service.Impl;

import com.alibaba.fastjson.JSON;
import org.example.base.BaseResponse;
import org.example.mapper.UserMapper;
import org.example.model.SeckillUser;
import org.example.model.req.UserReq;
import org.example.model.resp.UserResp;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl implements UserService {
    public static final String UserPhoneCodePrefix = "seckill-phone-code:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseResponse<Boolean> sendSmsCode(String phone) {
        SeckillUser user =  userMapper.SelectByPhone(phone);
        if(user == null) {
            return BaseResponse.error( "请输入正确的手机号", false);
        }
        String code = "123456";
        redisTemplate.opsForValue().set(UserPhoneCodePrefix + phone, code, 30, TimeUnit.MINUTES);
    return BaseResponse.OK("发送成功", true);
    }

    @Override
    public BaseResponse<UserResp.LoginResp> login(UserReq.LoginReq loginReq) {
        String key = UserPhoneCodePrefix + loginReq.getPhone();
        String CacheCodeValue = (String) redisTemplate.opsForValue().get(key);
        System.out.println("结果是:" + CacheCodeValue);
        if (!loginReq.getCode().equals(CacheCodeValue)) {
            return BaseResponse.error("请输入正确的手机号和验证码", new UserResp.LoginResp("", null));
        }
        SeckillUser user = userMapper.SelectByPhone(loginReq.getPhone());
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, JSON.toJSONString(user), 14, TimeUnit.DAYS);
        return BaseResponse.OK("登录成功", new UserResp.LoginResp(token, user));
    }

    @Override
    public BaseResponse<UserResp.LoginResp> checkToken(String token) {
        SeckillUser user =  (SeckillUser) redisTemplate.opsForValue().get(token);
        if(user == null) {
            return BaseResponse.error("登录失败", new UserResp.LoginResp("", null));
        }
        return BaseResponse.OK("登录成功", new UserResp.LoginResp(token, user));
    }
}
