package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysReviewEmployee;
import com.kcylog.system.mapper.SysReviewEmployeeMapper;
import com.kcylog.system.service.ISysReviewEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 雇工实际工作内容记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@Service
public class SysReviewEmployeeServiceImpl implements ISysReviewEmployeeService 
{
    @Autowired
    private SysReviewEmployeeMapper sysReviewEmployeeMapper;

    /**
     * 查询雇工实际工作内容记录
     * 
     * @param reviewEmployeeId 雇工实际工作内容记录主键
     * @return 雇工实际工作内容记录
     */
    @Override
    public SysReviewEmployee selectSysReviewEmployeeByReviewEmployeeId(Long reviewEmployeeId)
    {
        return sysReviewEmployeeMapper.selectSysReviewEmployeeByReviewEmployeeId(reviewEmployeeId);
    }

    /**
     * 查询雇工实际工作内容记录列表
     * 
     * @param sysReviewEmployee 雇工实际工作内容记录
     * @return 雇工实际工作内容记录
     */
    @Override
    public List<SysReviewEmployee> selectSysReviewEmployeeList(SysReviewEmployee sysReviewEmployee)
    {
        return sysReviewEmployeeMapper.selectSysReviewEmployeeList(sysReviewEmployee);
    }

    /**
     * 新增雇工实际工作内容记录
     * 
     * @param sysReviewEmployee 雇工实际工作内容记录
     * @return 结果
     */
    @Override
    public int insertSysReviewEmployee(SysReviewEmployee sysReviewEmployee)
    {
        return sysReviewEmployeeMapper.insertSysReviewEmployee(sysReviewEmployee);
    }

    /**
     * 修改雇工实际工作内容记录
     * 
     * @param sysReviewEmployee 雇工实际工作内容记录
     * @return 结果
     */
    @Override
    public int updateSysReviewEmployee(SysReviewEmployee sysReviewEmployee)
    {
        return sysReviewEmployeeMapper.updateSysReviewEmployee(sysReviewEmployee);
    }

    /**
     * 批量删除雇工实际工作内容记录
     * 
     * @param reviewEmployeeIds 需要删除的雇工实际工作内容记录主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewEmployeeByReviewEmployeeIds(Long[] reviewEmployeeIds)
    {
        return sysReviewEmployeeMapper.deleteSysReviewEmployeeByReviewEmployeeIds(reviewEmployeeIds);
    }

    /**
     * 删除雇工实际工作内容记录信息
     * 
     * @param reviewEmployeeId 雇工实际工作内容记录主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewEmployeeByReviewEmployeeId(Long reviewEmployeeId)
    {
        return sysReviewEmployeeMapper.deleteSysReviewEmployeeByReviewEmployeeId(reviewEmployeeId);
    }
}
