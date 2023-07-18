package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysReviewProcess;

import java.util.List;

/**
 * 审核单流程Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-12
 */
public interface SysReviewProcessMapper 
{
    /**
     * 查询审核单流程
     * 
     * @param reviewProcessId 审核单流程主键
     * @return 审核单流程
     */
    public SysReviewProcess selectSysReviewProcessByReviewProcessId(Long reviewProcessId);

    /**
     * 查询审核单流程列表
     * 
     * @param sysReviewProcess 审核单流程
     * @return 审核单流程集合
     */
    public List<SysReviewProcess> selectSysReviewProcessList(SysReviewProcess sysReviewProcess);

    /**
     * 新增审核单流程
     * 
     * @param sysReviewProcess 审核单流程
     * @return 结果
     */
    public int insertSysReviewProcess(SysReviewProcess sysReviewProcess);

    /**
     * 修改审核单流程
     * 
     * @param sysReviewProcess 审核单流程
     * @return 结果
     */
    public int updateSysReviewProcess(SysReviewProcess sysReviewProcess);

    /**
     * 删除审核单流程
     * 
     * @param reviewProcessId 审核单流程主键
     * @return 结果
     */
    public int deleteSysReviewProcessByReviewProcessId(Long reviewProcessId);

    /**
     * 批量删除审核单流程
     * 
     * @param reviewProcessIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysReviewProcessByReviewProcessIds(Long[] reviewProcessIds);

    /**
     * 批量新增审核单流程
     *
     * @param sysReviewProcess 审核单流程
     * @return 结果
     */
    public int insertSysReviewProcessBatch(List<SysReviewProcess> sysReviewProcess);

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
    public int deleteSysReviewProcessByReviewIds(String[] reviewIds);

    /**
     * 修改审核单流程状态
     *
     * @param sysReviewProcess 审核单参数
     * @return 结果
     */
    public int setStatusByUserIdAndReviewId(SysReviewProcess sysReviewProcess);

    public int setNextStatusByReviewId(Long reviewId);

    public int setStatusNotStartByReviewId(Long reviewId);

    public int updateStatusByUserIdReviewId(Long reviewId);

    public int reSetStatusByReviewProcessId(Long reviewProcessId);
}
