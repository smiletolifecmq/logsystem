package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysReview;
import com.kcylog.system.domain.SysReviewProcess;

import java.util.List;

/**
 * 审核单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
public interface SysReviewMapper 
{
    /**
     * 查询审核单
     * 
     * @param reviewId 审核单主键
     * @return 审核单
     */
    public SysReview selectSysReviewByReviewId(String reviewId);

    /**
     * 查询审核单列表
     * 
     * @param sysReview 审核单
     * @return 审核单集合
     */
    public List<SysReview> selectSysReviewList(SysReview sysReview);

    /**
     * 新增审核单
     * 
     * @param sysReview 审核单
     * @return 结果
     */
    public int insertSysReview(SysReview sysReview);

    /**
     * 修改审核单
     * 
     * @param sysReview 审核单
     * @return 结果
     */
    public int updateSysReview(SysReview sysReview);

    /**
     * 删除审核单
     * 
     * @param reviewId 审核单主键
     * @return 结果
     */
    public int deleteSysReviewByReviewId(String reviewId);

    /**
     * 批量删除审核单
     * 
     * @param reviewIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysReviewByReviewIds(String[] reviewIds);

    /**
     * 发起审核单申请
     */
    public int setSysReviewStatusByReviewId(SysReview sysReview);

    // 获取当前用户需要审核的待审核单
    public List<SysReview> selectSysUpcomingReviewList(SysReview sysReview);

    public List<SysReview> selectCompletedReviewList(SysReview sysReview);

    public List<SysReview> selectDoneReviewList(SysReview sysReview);

    public int updateSysReviewFinalTime(SysReview sysReview);

    public int setSysReviewFinalSecondStatusByReviewId(SysReview sysReview);

    public int updateStartEditByReviewId(SysReview sysReview);

    public int generateStatement(SysReview sysReview);

    public List<SysReview> settlementListReview(SysReview sysReview);

    public List<SysReview> selectSysReviewListBySettlementId(SysReview sysReview);

    public int updateFinalHireByReviewId(Long reviewId);

    public SysReview getReviewBySerialNum(String serialNum);

    public SysReview getSubcontractReview(String serialNum);

    public List<SysReview> selectSysReviewListByReviewIds(Long[] reviewExportIds);

    public int updateReviewManType(SysReviewProcess sysReviewProcess);
}
