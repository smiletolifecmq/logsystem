package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysReviewSub;
import com.kcylog.system.domain.SysReviewSubEmployee;
import com.kcylog.system.mapper.SysReviewSubEmployeeMapper;
import com.kcylog.system.service.ISysReviewSubEmployeeService;
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
public class SysReviewSubEmployeeServiceImpl implements ISysReviewSubEmployeeService
{
    @Autowired
    private SysReviewSubEmployeeMapper sysReviewSubEmployeeMapper;

    /**
     * 查询雇工实际工作内容记录
     * 
     * @param reviewEmployeeId 雇工实际工作内容记录主键
     * @return 雇工实际工作内容记录
     */
    @Override
    public SysReviewSubEmployee selectSysReviewSubEmployeeByReviewEmployeeId(Long reviewEmployeeId)
    {
        return sysReviewSubEmployeeMapper.selectSysReviewSubEmployeeByReviewEmployeeId(reviewEmployeeId);
    }

    /**
     * 查询雇工实际工作内容记录列表
     * 
     * @param sysReviewSubEmployee 雇工实际工作内容记录
     * @return 雇工实际工作内容记录
     */
    @Override
    public List<SysReviewSubEmployee> selectSysReviewSubEmployeeList(SysReviewSubEmployee sysReviewSubEmployee)
    {
        return sysReviewSubEmployeeMapper.selectSysReviewSubEmployeeList(sysReviewSubEmployee);
    }

    /**
     * 新增雇工实际工作内容记录
     * 
     * @param sysReviewSubEmployee 雇工实际工作内容记录
     * @return 结果
     */
    @Override
    public int insertSysReviewSubEmployee(SysReviewSubEmployee sysReviewSubEmployee)
    {
        return sysReviewSubEmployeeMapper.insertSysReviewSubEmployee(sysReviewSubEmployee);
    }

    /**
     * 修改雇工实际工作内容记录
     * 
     * @param sysReviewSubEmployee 雇工实际工作内容记录
     * @return 结果
     */
    @Override
    public int updateSysReviewSubEmployee(SysReviewSubEmployee sysReviewSubEmployee)
    {
        return sysReviewSubEmployeeMapper.updateSysReviewSubEmployee(sysReviewSubEmployee);
    }

    /**
     * 批量删除雇工实际工作内容记录
     * 
     * @param reviewEmployeeIds 需要删除的雇工实际工作内容记录主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSubEmployeeByReviewEmployeeIds(Long[] reviewEmployeeIds)
    {
        return sysReviewSubEmployeeMapper.deleteSysReviewSubEmployeeByReviewEmployeeIds(reviewEmployeeIds);
    }

    /**
     * 删除雇工实际工作内容记录信息
     * 
     * @param reviewEmployeeId 雇工实际工作内容记录主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSubEmployeeByReviewEmployeeId(Long reviewEmployeeId)
    {
        return sysReviewSubEmployeeMapper.deleteSysReviewSubEmployeeByReviewEmployeeId(reviewEmployeeId);
    }

    @Override
    public List<SysReviewSubEmployee> selectSysReviewSubEmployeeListNotItself(SysReviewSubEmployee sysReviewSubEmployee)
    {
        return sysReviewSubEmployeeMapper.selectSysReviewSubEmployeeListNotItself(sysReviewSubEmployee);
    }

    @Override
    public  List<SysReviewSubEmployee> selectSysReviewSubEmployeeByReviewId(Long reviewId)
    {
        return sysReviewSubEmployeeMapper.selectSysReviewSubEmployeeByReviewId(reviewId);
    }

    @Override
    public List<SysReviewSubEmployee> selectSysReviewSubEmployeeListJoinReview(SysReviewSub sysReviewSub)
    {
        return sysReviewSubEmployeeMapper.selectSysReviewSubEmployeeListJoinReview(sysReviewSub);
    }

    @Override
    public int updateIsJsByReviewEmployeeIds(Long[] reviewExportEmployeeIds)
    {
        return sysReviewSubEmployeeMapper.updateIsJsByReviewEmployeeIds(reviewExportEmployeeIds);
    }

    @Override
    public List<SysReviewSubEmployee> selectSysReviewSubEmployeeListJoinReviewMonth(SysReviewSub sysReviewSub)
    {
        return sysReviewSubEmployeeMapper.selectSysReviewSubEmployeeListJoinReviewMonth(sysReviewSub);
    }

    @Override
    public List<SysReviewSubEmployee> selectSysReviewEmployeeByReviewIdMonth(SysReviewSub sysReviewSub)
    {
        return sysReviewSubEmployeeMapper.selectSysReviewEmployeeByReviewIdMonth(sysReviewSub);
    }

}
