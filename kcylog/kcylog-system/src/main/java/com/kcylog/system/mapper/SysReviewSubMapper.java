package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysReviewSub;

import java.util.List;

/**
 * 审核单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
public interface SysReviewSubMapper
{
    /**
     * 查询审核单
     * 
     * @param reviewId 审核单主键
     * @return 审核单
     */
    public SysReviewSub selectSysReviewSubByReviewId(String reviewId);

    /**
     * 查询审核单列表
     * 
     * @param sysReviewSub 审核单
     * @return 审核单集合
     */
    public List<SysReviewSub> selectSysReviewSubList(SysReviewSub sysReviewSub);

    /**
     * 新增审核单
     * 
     * @param sysReviewSub 审核单
     * @return 结果
     */
    public int insertSysReviewSub(SysReviewSub sysReviewSub);

    /**
     * 修改审核单
     * 
     * @param sysReviewSub 审核单
     * @return 结果
     */
    public int updateSysReviewSub(SysReviewSub sysReviewSub);

    /**
     * 删除审核单
     * 
     * @param reviewId 审核单主键
     * @return 结果
     */
    public int deleteSysReviewSubByReviewId(String reviewId);

    /**
     * 批量删除审核单
     * 
     * @param reviewIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysReviewSubByReviewIds(String[] reviewIds);

    /**
     * 发起审核单申请
     */
    public int setSysReviewSubStatusByReviewId(SysReviewSub sysReviewSub);

    // 获取当前用户需要审核的待审核单
    public List<SysReviewSub> selectSysUpcomingReviewList(SysReviewSub sysReviewSub);

    public List<SysReviewSub> selectCompletedReviewList(SysReviewSub sysReviewSub);

    public List<SysReviewSub> selectDoneReviewList(SysReviewSub sysReviewSub);

    public int updateSysReviewSubFinalTime(SysReviewSub sysReviewSub);

    public int setSysReviewSubFinalSecondStatusByReviewId(SysReviewSub sysReviewSub);

    public int updateStartEditByReviewId(SysReviewSub sysReviewSub);

    public int generateStatement(SysReviewSub sysReviewSub);

    public List<SysReviewSub> settlementListReview(SysReviewSub sysReviewSub);

    public List<SysReviewSub> selectSysReviewSubListBySettlementId(SysReviewSub sysReviewSub);

    public int updateFinalHireByReviewId(Long reviewId);

    public SysReviewSub getReviewBySerialNum(String serialNum);

    public SysReviewSub getSubcontractReview(Long subcontractId);
}
