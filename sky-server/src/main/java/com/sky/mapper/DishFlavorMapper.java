package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量保存菜品口味
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品Id批量删除口味列表
     * @param serializableList
     */
    void deleteBatch(List<Long> serializableList);

    /**
     * 根据菜品id删除菜品口味
     * @param id
     */
    void deleteByDishId(@Param("dishId")Long id);

    /**
     * 根据菜品id查找口味
     * @param id
     * @return
     */
    List<DishFlavor> getByDishId(@Param("dishId") Long id);
}
