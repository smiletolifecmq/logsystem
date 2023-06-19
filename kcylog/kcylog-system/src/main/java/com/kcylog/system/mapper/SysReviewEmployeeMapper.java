package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysReview;
import com.kcylog.system.domain.SysReviewEmployee;

import java.util.List;

/**
 * 雇工实际工作内容记录Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public interface SysReviewEmployeeMapper 
{
    /**
     * 查询雇工实际工作内容记录
     * 
     * @param reviewEmployeeId 雇工实际工作内容记录主键
     * @return 雇工实际工作内容记录
     */
    public SysReviewEmployee selectSysReviewEmployeeByReviewEmployeeId(Long reviewEmployeeId);

    /**
     * 查询雇工实际工作内容记录列表
     * 
     * @param sysReviewEmployee 雇工实际工作内容记录
     * @return 雇工实际工作内容记录集合
     */
    public List<SysReviewEmployee> selectSysReviewEmployeeList(SysReviewEmployee sysReviewEmployee);

    /**
     * 新增雇工实际工作内容记录
     * 
     * @param sysReviewEmployee 雇工实际工作内容记录
     * @return 结果
     */
    public int insertSysReviewEmployee(SysReviewEmployee sysReviewEmployee);

    /**
     * 修改雇工实际工作内容记录
     * 
     * @param sysReviewEmployee 雇工实际工作内容记录
     * @return 结果
     */
    public int updateSysReviewEmployee(SysReviewEmployee sysReviewEmployee);

    /**
     * 删除雇工实际工作内容记录
     * 
     * @param reviewEmployeeId 雇工实际工作内容记录主键
     * @return 结果
     */
    public int deleteSysReviewEmployeeByReviewEmployeeId(Long reviewEmployeeId);

    /**
     * 批量删除雇工实际工作内容记录
     * 
     * @param reviewEmployeeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysReviewEmployeeByReviewEmployeeIds(Long[] reviewEmployeeIds);

    public List<SysReviewEmployee> selectSysReviewEmployeeListNotItself(SysReviewEmployee sysReviewEmployee);

    public List<SysReviewEmployee> selectSysReviewEmployeeByReviewId(Long reviewId);

    public List<SysReviewEmployee> selectSysReviewEmployeeListJoinReview(SysReview sysReview);
}
