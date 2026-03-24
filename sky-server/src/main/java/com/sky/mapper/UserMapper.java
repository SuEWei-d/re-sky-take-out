package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 根据Openid获取用户信息
     * @param openid
     * @return
     */
    User getByOpenid(String openid);

    /**
     * 新建用户
     * @param user
     */
    void insert(User user);
}
