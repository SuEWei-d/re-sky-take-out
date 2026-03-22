package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 分类相关接口
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询分类
     *
     * @param categoryPageQueryDTO
     * @return
     */
    @ApiOperation("分页查询分类列表")
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult pageResult = categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用分类
     * @param status
     * @param id
     * @return
     */
    @ApiOperation("启用禁用分类状态")
    @PostMapping("/status/{status}")
    public Result status(@PathVariable int status, Long id){
        categoryService.stauts(status, id);
        return Result.success();
    }

    /**
     * 修改分类信息
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改分类信息")
    public Result update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 新增分类信息
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增分类信息")
    public Result insert(@RequestBody CategoryDTO categoryDTO){
        categoryService.insert(categoryDTO);
        return Result.success();
    }

    /**
     * 根据分类Id删除分类信息
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据ID删除分类信息")
    public Result deleteById(Long id){
        categoryService.deleteById(id);
        return Result.success();
    }
}
