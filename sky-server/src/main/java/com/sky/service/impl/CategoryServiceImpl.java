package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        int pageNum = categoryPageQueryDTO.getPage();
        int pageSize = categoryPageQueryDTO.getPageSize();

        PageHelper.startPage(pageNum, pageSize);
        Page<Category> pageResult = categoryMapper.page(categoryPageQueryDTO);

        long total = pageResult.getTotal();
        List<Category> records = pageResult.getResult();

        return new PageResult(total, records);
    }
}
