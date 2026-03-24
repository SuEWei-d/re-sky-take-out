package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void insert(SetmealDTO setmealDTO) {
        //新增套餐同时将新增绑定的SetmealDish存入数据库
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setmealDTO.setStatus(StatusConstant.DISABLE);
        setmealMapper.insert(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (!setmealDishes.isEmpty()) {
            List<SetmealDish> setmealDishList = setmealDishes.stream().map(setmealDish -> {
                setmealDish.setSetmealId(setmeal.getId());
                return setmealDish;
            }).collect(Collectors.toList());
            //批量新增套餐菜品
            setmealDishMapper.insertBatch(setmealDishList);
        }

    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        int pageNum = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();

        PageHelper.startPage(pageNum, pageSize);

        Page<SetmealVO> setmealVOPage = setmealMapper.page(setmealPageQueryDTO);

        long total = setmealVOPage.getTotal();
        List<SetmealVO> result = setmealVOPage.getResult();

        return new PageResult(total, result);
    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Transactional
    @Override
    public void delete(List<Long> ids) {
        // 当前套餐是否为起售状态
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException("当前套餐还在起售中无法删除!");
            }
        }

        // 批量删除套餐同时批量删除绑定的套餐菜品信息
        setmealMapper.delete(ids);
        setmealDishMapper.delete(ids);
    }

    /**
     * 修改套餐及套餐菜品
     * @param setmealDTO
     */
    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        List<Long> setmealId = new ArrayList<>();
        setmealId.add(setmeal.getId());
        // 同时删除套餐菜品
        setmealDishMapper.delete(setmealId);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        List<SetmealDish> setmealDishList = setmealDishes.stream().map(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
            return setmealDish;
        }).collect(Collectors.toList());

        // 并重新添加回新的套餐菜品
        setmealDishMapper.insertBatch(setmealDishList);
    }

    /**
     * 根据id查找套餐
     * @param id
     * @return
     */
    @Override
    public SetmealVO getById(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);

        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);

        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    /**
     * 起售停售
     * @param status
     * @param id
     * @return
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();

        setmealMapper.update(setmeal);
    }
}
