package com.kcylog.system.service;

import com.kcylog.system.domain.SysEmployee;

import java.util.List;

/**
 * 雇工Service接口
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public interface ISysEmployeeService 
{
    /**
     * 查询雇工
     * 
     * @param employeeId 雇工主键
     * @return 雇工
     */
    public SysEmployee selectSysEmployeeByEmployeeId(Long employeeId);

    /**
     * 查询雇工列表
     * 
     * @param sysEmployee 雇工
     * @return 雇工集合
     */
    public List<SysEmployee> selectSysEmployeeList(SysEmployee sysEmployee);

    /**
     * 新增雇工
     * 
     * @param sysEmployee 雇工
     * @return 结果
     */
    public int insertSysEmployee(SysEmployee sysEmployee);

    /**
     * 修改雇工
     * 
     * @param sysEmployee 雇工
     * @return 结果
     */
    public int updateSysEmployee(SysEmployee sysEmployee);

    /**
     * 批量删除雇工
     * 
     * @param employeeIds 需要删除的雇工主键集合
     * @return 结果
     */
    public int deleteSysEmployeeByEmployeeIds(Long[] employeeIds);

    /**
     * 删除雇工信息
     * 
     * @param employeeId 雇工主键
     * @return 结果
     */
    public int deleteSysEmployeeByEmployeeId(Long employeeId);

    public SysEmployee selectSysEmployeeByIdCard(String idCard);

}
