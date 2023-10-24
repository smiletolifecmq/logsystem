package com.kcylog.system.service;

import com.kcylog.system.domain.SysReviewSub;
import com.kcylog.system.domain.SysReviewSubEmployee;

import java.util.List;

/**
 * 雇工实际工作内容记录Service接口
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public interface ISysReviewSubEmployeeService
{
    /**
     * 查询雇工实际工作内容记录
     * 
     * @param reviewEmployeeId 雇工实际工作内容记录主键
     * @return 雇工实际工作内容记录
     */
    public SysReviewSubEmployee selectSysReviewSubEmployeeByReviewEmployeeId(Long reviewEmployeeId);

    /**
     * 查询雇工实际工作内容记录列表
     * 
     * @param sysReviewSubEmployee 雇工实际工作内容记录
     * @return 雇工实际工作内容记录集合
     */
    public List<SysReviewSubEmployee> selectSysReviewSubEmployeeList(SysReviewSubEmployee sysReviewSubEmployee);

    /**
     * 新增雇工实际工作内容记录
     * 
     * @param sysReviewSubEmployee 雇工实际工作内容记录
     * @return 结果
     */
    public int insertSysReviewSubEmployee(SysReviewSubEmployee sysReviewSubEmployee);

    /**
     * 修改雇工实际工作内容记录
     * 
     * @param sysReviewSubEmployee 雇工实际工作内容记录
     * @return 结果
     */
    public int updateSysReviewSubEmployee(SysReviewSubEmployee sysReviewSubEmployee);

    /**
     * 批量删除雇工实际工作内容记录
     * 
     * @param reviewEmployeeIds 需要删除的雇工实际工作内容记录主键集合
     * @return 结果
     */
    public int deleteSysReviewSubEmployeeByReviewEmployeeIds(Long[] reviewEmployeeIds);

    /**
     * 删除雇工实际工作内容记录信息
     * 
     * @param reviewEmployeeId 雇工实际工作内容记录主键
     * @return 结果
     */
    public int deleteSysReviewSubEmployeeByReviewEmployeeId(Long reviewEmployeeId);

    public List<SysReviewSubEmployee> selectSysReviewSubEmployeeListNotItself(SysReviewSubEmployee sysReviewSubEmployee);

    public List<SysReviewSubEmployee> selectSysReviewSubEmployeeByReviewId(Long reviewId);

    public List<SysReviewSubEmployee> selectSysReviewSubEmployeeListJoinReview(SysReviewSub sysReviewSub);

    public int updateIsJsByReviewEmployeeIds(Long[] reviewExportEmployeeIds);

    public List<SysReviewSubEmployee> selectSysReviewSubEmployeeListJoinReviewMonth(SysReviewSub sysReviewSub);

    public List<SysReviewSubEmployee> selectSysReviewEmployeeByReviewIdMonth(SysReviewSub sysReviewSub);
}
