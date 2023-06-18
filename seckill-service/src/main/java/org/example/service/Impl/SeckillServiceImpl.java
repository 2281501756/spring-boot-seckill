package org.example.service.Impl;

import org.apache.commons.lang3.BooleanUtils;
import org.example.base.BaseResponse;
import org.example.mapper.OrderMapper;
import org.example.mapper.ProductMapper;
import org.example.mapper.UserMapper;
import org.example.model.SeckillOrder;
import org.example.model.SeckillProducts;
import org.example.model.SeckillUser;
import org.example.model.req.SeckillReq;
import org.example.service.SeckillService;
import org.example.util.DecrCacheStock;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private Redisson redisson;

    @Autowired
    private DecrCacheStock decrCacheStock;

    private String PRODUCT_CACHE_PREFIX = "s:p ";

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> pessimisticSeckill(SeckillReq.SimpleSeckillReq simpleSeckill, SeckillUser user) {
        Long productID = simpleSeckill.getProductID();
        SeckillProducts seckillProducts = productMapper.selectByIdForUpdate(productID);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> optimisticSeckill(SeckillReq.SimpleSeckillReq simpleSeckill, SeckillUser user) throws Exception {
        Long productID = simpleSeckill.getProductID();
        SeckillProducts seckillProducts = productMapper.selectByPrimaryKey(productID);
        if(seckillProducts == null) {
            return BaseResponse.error("商品不存在", "商品不存在");
        } else if(seckillProducts.getStartBuyTime().getTime() > System.currentTimeMillis()) {
            return BaseResponse.error("秒杀还未开始", "秒杀还未开始");
        } else if (seckillProducts.getCount() <= seckillProducts.getSaled()) {
            return BaseResponse.error("商品库存不足", "商品库存不足");
        }

        SeckillOrder order = new SeckillOrder();
        order.setUserId(user.getId());
        order.setProductId(seckillProducts.getId());
        order.setProductName(seckillProducts.getName());
        order.setCreateTime(new Date());
        orderMapper.insert(order);
        seckillProducts.setSaled(seckillProducts.getSaled() + 1);
        Example example = new Example(SeckillProducts.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", seckillProducts.getId());
        int i = productMapper.updateByExample(seckillProducts, example);
        if(i == 0) {
            throw new Exception("库存不足");
        }
        return BaseResponse.ok("抢购成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<String> redisSeckill(SeckillReq.SimpleSeckillReq simpleSeckill, SeckillUser user)   {
        Long productID = simpleSeckill.getProductID();
        SeckillProducts seckillProducts = productMapper.selectByPrimaryKey(productID);
        if(seckillProducts == null) {
            return BaseResponse.error("商品不存在", "商品不存在");
        } else if(seckillProducts.getStartBuyTime().getTime() > System.currentTimeMillis()) {
            return BaseResponse.error("秒杀还未开始", "秒杀还未开始");
        } else if (seckillProducts.getCount() <= seckillProducts.getSaled()) {
            return BaseResponse.error("商品库存不足", "商品库存不足");
        }
        Long res = decrCacheStock.decr(productID);
        if(res <= 0) {
            return BaseResponse.error("库存不足", "库存不足");
        }
        try {
            SeckillOrder order = new SeckillOrder();
            order.setUserId(user.getId());
            order.setProductId(seckillProducts.getId());
            order.setProductName(seckillProducts.getName());
            order.setCreateTime(new Date());
            orderMapper.insert(order);
            productMapper.reduceSaledById(seckillProducts.getId());
        } catch (Exception e){
            decrCacheStock.incr(seckillProducts.getId());
        }
        return BaseResponse.error("秒杀成功","秒杀成功");
    }
    private SeckillProducts setProductCache(Long productID) {
        SeckillProducts products = (SeckillProducts) redisTemplate.opsForValue().get(PRODUCT_CACHE_PREFIX + productID);
        // 得到缓存
        if(products != null) {
            return products;
        }
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(PRODUCT_CACHE_PREFIX + productID + "lock", "1", 10, TimeUnit.SECONDS);
        SeckillProducts seckillProducts = null;
        try {
            if(!BooleanUtils.isTrue(flag)) {
                 Thread.sleep(50);
                 return setProductCache(productID);
            }
            seckillProducts = productMapper.selectByPrimaryKey(productID);
            redisTemplate.opsForValue().set(PRODUCT_CACHE_PREFIX + productID, seckillProducts);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            redisTemplate.delete(PRODUCT_CACHE_PREFIX + productID + "lock");
        }
        return seckillProducts;
    }
}
