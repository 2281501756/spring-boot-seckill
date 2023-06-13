package org.example.service.Impl;

import org.apache.catalina.User;
import org.example.base.BaseResponse;
import org.example.mapper.OrderMapper;
import org.example.mapper.ProductMapper;
import org.example.mapper.UserMapper;
import org.example.model.SeckillOrder;
import org.example.model.SeckillProducts;
import org.example.model.SeckillUser;
import org.example.model.req.SeckillReq;
import org.example.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;


@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public BaseResponse<String> simpleSeckill(SeckillReq.SimpleSeckillReq simpleSeckill, SeckillUser user) {
        Long productID = simpleSeckill.getProductID();
        SeckillProducts seckillProducts = productMapper.selectByPrimaryKey(productID);
        if(seckillProducts == null) {
            return BaseResponse.error("商品不存在", "商品不存在");
        } else if(seckillProducts.getStartBuyTime().getTime() > System.currentTimeMillis()) {
            return BaseResponse.error("秒杀还未开始", "秒杀还未开始");
        } else if (seckillProducts.getCount() <= seckillProducts.getSaled()) {
            return BaseResponse.error("商品库存不足", "商品库存不足");
        }
        seckillProducts.setSaled(seckillProducts.getSaled() + 1);
        Example example = new Example(SeckillProducts.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", seckillProducts.getId());
        productMapper.updateByExample(seckillProducts, example);
        SeckillOrder order = new SeckillOrder();
        order.setUserId(user.getId());
        order.setProductId(seckillProducts.getId());
        order.setProductName(seckillProducts.getName());
        order.setCreateTime(new Date());
        orderMapper.insert(order);
        return BaseResponse.ok("抢购成功");
    }
}
