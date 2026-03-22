package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {

    /**
     * 分页查询分类列表
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);


    /**
     * 启用禁用分类
     * @param status
     * @param id
     */
    void stauts(int status, Long id);

    /**
     * 修改分类信息
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 新增分类信息
     * @param categoryDTO
     */
    void insert(CategoryDTO categoryDTO);

    /**
     * 根据分类Id删除分类信息
     * @param id
     */
    void deleteById(Long id);
}
