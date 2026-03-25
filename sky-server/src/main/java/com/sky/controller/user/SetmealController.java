package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "套餐相关接口")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据分类Id查找套餐
     * @param categoryId
     * @return
     */
    @Cacheable(cacheNames = "setmealCache", key = "#categoryId") // key: setmealCache::100
    @GetMapping("/list")
    @ApiOperation("根据分类Id查询套餐")
    public Result<List<Setmeal>> list(Long categoryId){
        List<Setmeal> setmealList = setmealService.getByCategoryId(categoryId);
        return Result.success(setmealList);
    }

    /**
     * 根据套餐id查询包含的菜品
     * @param setmealId
     * @return
     */
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品")
    public Result<List<DishItemVO>> getBySetmealIdWithDish(Long setmealId){
        List<DishItemVO> dishItemVOList = setmealService.getBySetmealWithDish(setmealId);
        return Result.success(dishItemVOList);
    }
}
