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
}
