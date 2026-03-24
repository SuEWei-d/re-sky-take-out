package com.sky.service.impl;

import com.sky.mapper.ShopMapper;
import com.sky.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String key = "SHOP_STATUS";

    @Override
    public void setStatus(Integer status) {
        //通过在Redis持久化保存设置店铺状态
        redisTemplate.opsForValue().set(key, status);
    }

    @Override
    public Integer getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(key);
        return status;
    }
}
