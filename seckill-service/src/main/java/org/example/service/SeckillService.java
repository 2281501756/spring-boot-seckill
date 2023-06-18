package org.example.service;

import org.example.base.BaseResponse;
import org.example.model.SeckillUser;
import org.example.model.req.SeckillReq;

public interface SeckillService {
    BaseResponse<String> simpleSeckill(SeckillReq.SimpleSeckillReq simpleSeckill, SeckillUser user);
    BaseResponse<String> pessimisticSeckill(SeckillReq.SimpleSeckillReq simpleSeckill, SeckillUser user);
    BaseResponse<String> optimisticSeckill(SeckillReq.SimpleSeckillReq simpleSeckill, SeckillUser user) throws Exception;
    BaseResponse<String> redisSeckill(SeckillReq.SimpleSeckillReq simpleSeckill, SeckillUser user) throws Exception;


}
