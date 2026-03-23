package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 分页查询分类列表
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 修改分类信息
     * @param category
     */
    void update(Category category);

    /**
     * 新增分类信息
     * @param category
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(Long id);

    /**
     * 根据类型查询分类信息
     * @param type
     * @return
     */
    List<Category> getByType(Integer type);
}
