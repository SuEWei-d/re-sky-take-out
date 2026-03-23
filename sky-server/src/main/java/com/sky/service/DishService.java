package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {

    /**
     * 新建菜品
     * @param dishDTO
     */
    void insert(DishDTO dishDTO);
}
