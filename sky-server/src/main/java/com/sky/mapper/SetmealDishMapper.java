package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查找套餐
     * @param id
     * @return
     */
    SetmealDish getByDishId(@Param("dishId")Long id);

    /**
     * 根据菜品Ids批量查找套餐
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);


    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 批量删除套餐菜品
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 根据套餐id获取套餐绑定的菜品
     * @param id
     * @return
     */
    List<SetmealDish> getBySetmealId(@Param("setmealId") Long id);
}
