package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysReviewSubProcess;

import java.util.List;

/**
 * 审核单流程Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-12
 */
public interface SysReviewSubProcessMapper
{
    /**
     * 查询审核单流程
     * 
     * @param reviewProcessId 审核单流程主键
     * @return 审核单流程
     */
    public SysReviewSubProcess selectSysReviewSubProcessByReviewProcessId(Long reviewProcessId);

    /**
     * 查询审核单流程列表
     * 
     * @param sysReviewSubProcess 审核单流程
     * @return 审核单流程集合
     */
    public List<SysReviewSubProcess> selectSysReviewSubProcessList(SysReviewSubProcess sysReviewSubProcess);

    /**
     * 新增审核单流程
     * 
     * @param sysReviewSubProcess 审核单流程
     * @return 结果
     */
    public int insertSysReviewSubProcess(SysReviewSubProcess sysReviewSubProcess);

    /**
     * 修改审核单流程
     * 
     * @param sysReviewSubProcess 审核单流程
     * @return 结果
     */
    public int updateSysReviewSubProcess(SysReviewSubProcess sysReviewSubProcess);

    /**
     * 删除审核单流程
     * 
     * @param reviewProcessId 审核单流程主键
     * @return 结果
     */
    public int deleteSysReviewSubProcessByReviewProcessId(Long reviewProcessId);

    /**
     * 批量删除审核单流程
     * 
     * @param reviewProcessIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysReviewSubProcessByReviewProcessIds(Long[] reviewProcessIds);

    /**
     * 批量新增审核单流程
     *
     * @param sysReviewSubProcess 审核单流程
     * @return 结果
     */
    public int insertSysReviewSubProcessBatch(List<SysReviewSubProcess> sysReviewSubProcess);

    /**
     * 设置审核单状态
     *
     * @param reviewId 审核单id
     * @return 结果
     */
    public int setStatusByReviewIdFirst(Long reviewId);

    /**
     * 删除审核单流程
     *
     * @param reviewIds 审核单主键
     * @return 结果
     */
    public int deleteSysReviewSubProcessByReviewIds(String[] reviewIds);

    /**
     * 修改审核单流程状态
     *
     * @param sysReviewSubProcess 审核单参数
     * @return 结果
     */
    public int setStatusByUserIdAndReviewId(SysReviewSubProcess sysReviewSubProcess);

    public int setNextStatusByReviewId(Long reviewId);

    public int setStatusNotStartByReviewId(Long reviewId);

    public int updateStatusByUserIdReviewId(Long reviewId);

    public int reSetStatusByReviewId(Long reviewId);
}
