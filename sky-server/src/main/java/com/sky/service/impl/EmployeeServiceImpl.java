package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @Override
    public Result insert(EmployeeDTO employeeDTO) {
        //1、获取前端传来的员工参数
        Employee isEmployee = employeeMapper.getByUsername(employeeDTO.getUsername());
        //2、判断账号是否唯一
        if (isEmployee != null) {
            return Result.error("当前用户已存在!");
        }
        //3、手机号为合法的11位手机号
        // 合法手机号正则表达式
        String phoneRegex = "^(13[0-9]|14[01456789]|15[0-35-9]|16[2567]|17[235678]|18[0-9]|19[0-35-9])\\d{8}$";
        // 注入正则表达式
        Pattern pattern = Pattern.compile(phoneRegex);
        // 与正则表达式匹配
        Matcher matcher = pattern.matcher(employeeDTO.getPhone());
        if (!matcher.matches()) {
            return Result.error("输入的手机号不合法!");
        }
        // 4、密码默认为123456加密后传入数据库
        Employee employee = new Employee();
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        BeanUtils.copyProperties(employeeDTO, employee); // 需要源对象在前，目标对象在后

        // 5、状态设置（默认状态为锁定0）
        employee.setStatus(StatusConstant.DISABLE); // 1为启用，0为锁定

        // 6、设置创建时间和更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        // 7、设置创建人Id 和 修改人Id
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        //存入数据库
        employeeMapper.insert(employee);

        return Result.success();

    }

}
