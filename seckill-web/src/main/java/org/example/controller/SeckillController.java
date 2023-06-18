package org.example.controller;


import org.example.base.BaseResponse;
import org.example.model.SeckillUser;
import org.example.model.req.SeckillReq;
import org.example.security.WebUserUtil;
import org.example.service.Impl.SeckillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private SeckillServiceImpl seckillService;

    @PostMapping("/simple")
    public BaseResponse<String> simple(@RequestBody @Valid SeckillReq.SimpleSeckillReq simpleSeckill) {
        SeckillUser loginUser = WebUserUtil.getLoginUser();
        if(loginUser == null) {
            return BaseResponse.error("登录失败", "秒杀失败");
        }
        return seckillService.simpleSeckill(simpleSeckill, loginUser);
    }

    @PostMapping("/pessimistic")
    public BaseResponse<String> pessimistic(@RequestBody @Valid SeckillReq.SimpleSeckillReq simpleSeckill) {
        SeckillUser loginUser = WebUserUtil.getLoginUser();
        if(loginUser == null) {
            return BaseResponse.error("登录失败", "秒杀失败");
        }
        BaseResponse<String> stringBaseResponse = null;
        try {
            stringBaseResponse = seckillService.pessimisticSeckill(simpleSeckill, loginUser);
        } catch (Exception e) {
            return BaseResponse.error("售出过量", "售出过量");
        }
        return stringBaseResponse;
    }
    @PostMapping("/optimistic")
    public BaseResponse<String> optimistic(@RequestBody @Valid SeckillReq.SimpleSeckillReq simpleSeckill) {
        SeckillUser loginUser = WebUserUtil.getLoginUser();
        if(loginUser == null) {
            return BaseResponse.error("登录失败", "秒杀失败");
        }
        BaseResponse<String> stringBaseResponse = null;
        try {
            stringBaseResponse = seckillService.pessimisticSeckill(simpleSeckill, loginUser);
        } catch (Exception e) {
            return BaseResponse.error("售出过量", "售出过量");
        }
        return stringBaseResponse;
    }
    @PostMapping("/redis")
    public BaseResponse<String> redisSeckill(@RequestBody @Valid SeckillReq.SimpleSeckillReq simpleSeckill) {
        SeckillUser loginUser = WebUserUtil.getLoginUser();
        if(loginUser == null) {
            return BaseResponse.error("登录失败", "秒杀失败");
        }
        return seckillService.redisSeckill(simpleSeckill, loginUser);

    }

}
