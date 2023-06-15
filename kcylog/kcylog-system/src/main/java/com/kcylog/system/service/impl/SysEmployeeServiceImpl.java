package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysEmployee;
import com.kcylog.system.mapper.SysEmployeeMapper;
import com.kcylog.system.service.ISysEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 雇工Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@Service
public class SysEmployeeServiceImpl implements ISysEmployeeService 
{
    @Autowired
    private SysEmployeeMapper sysEmployeeMapper;

    /**
     * 查询雇工
     * 
     * @param employeeId 雇工主键
     * @return 雇工
     */
    @Override
    public SysEmployee selectSysEmployeeByEmployeeId(Long employeeId)
    {
        return sysEmployeeMapper.selectSysEmployeeByEmployeeId(employeeId);
    }

    /**
     * 查询雇工列表
     * 
     * @param sysEmployee 雇工
     * @return 雇工
     */
    @Override
    public List<SysEmployee> selectSysEmployeeList(SysEmployee sysEmployee)
    {
        return sysEmployeeMapper.selectSysEmployeeList(sysEmployee);
    }

    /**
     * 新增雇工
     * 
     * @param sysEmployee 雇工
     * @return 结果
     */
    @Override
    public int insertSysEmployee(SysEmployee sysEmployee)
    {
        return sysEmployeeMapper.insertSysEmployee(sysEmployee);
    }

    /**
     * 修改雇工
     * 
     * @param sysEmployee 雇工
     * @return 结果
     */
    @Override
    public int updateSysEmployee(SysEmployee sysEmployee)
    {
        return sysEmployeeMapper.updateSysEmployee(sysEmployee);
    }

    /**
     * 批量删除雇工
     * 
     * @param employeeIds 需要删除的雇工主键
     * @return 结果
     */
    @Override
    public int deleteSysEmployeeByEmployeeIds(Long[] employeeIds)
    {
        return sysEmployeeMapper.deleteSysEmployeeByEmployeeIds(employeeIds);
    }

    /**
     * 删除雇工信息
     * 
     * @param employeeId 雇工主键
     * @return 结果
     */
    @Override
    public int deleteSysEmployeeByEmployeeId(Long employeeId)
    {
        return sysEmployeeMapper.deleteSysEmployeeByEmployeeId(employeeId);
    }

    @Override
    public SysEmployee selectSysEmployeeByIdCard(String idCard)
    {
        return sysEmployeeMapper.selectSysEmployeeByIdCard(idCard);
    }
}
