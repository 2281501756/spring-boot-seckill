package org.example.service.Impl;

import org.example.base.BaseResponse;
import org.example.mapper.UserMapper;
import org.example.model.SeckillUser;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        redisTemplate.opsForValue().set(UserPhoneCodePrefix + phone, code, 60 * 30, TimeUnit.SECONDS);
    return BaseResponse.OK("发送成功", true);
    }
}
