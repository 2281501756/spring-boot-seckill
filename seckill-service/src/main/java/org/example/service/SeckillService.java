package org.example.service;

import org.example.base.BaseResponse;
import org.example.model.SeckillUser;
import org.example.model.req.SeckillReq;

public interface SeckillService {
    BaseResponse<String> simpleSeckill(SeckillReq.SimpleSeckillReq simpleSeckill, SeckillUser user);
}
