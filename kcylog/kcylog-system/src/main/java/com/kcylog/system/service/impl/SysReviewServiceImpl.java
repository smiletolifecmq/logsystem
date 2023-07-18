package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.SysReview;
import com.kcylog.system.mapper.SysReviewMapper;
import com.kcylog.system.service.ISysReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 审核单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
@Service
public class SysReviewServiceImpl implements ISysReviewService 
{
    @Autowired
    private SysReviewMapper sysReviewMapper;

    /**
     * 查询审核单
     * 
     * @param reviewId 审核单主键
     * @return 审核单
     */
    @Override
    public SysReview selectSysReviewByReviewId(String reviewId)
    {
        return sysReviewMapper.selectSysReviewByReviewId(reviewId);
    }

    /**
     * 查询审核单列表
     * 
     * @param sysReview 审核单
     * @return 审核单
     */
    @Override
    public List<SysReview> selectSysReviewList(SysReview sysReview)
    {
        return sysReviewMapper.selectSysReviewList(sysReview);
    }

    /**
     * 新增审核单
     * 
     * @param sysReview 审核单
     * @return 结果
     */
    @Override
    public int insertSysReview(SysReview sysReview)
    {
        sysReview.setCreateTime(DateUtils.getNowDate());
        return sysReviewMapper.insertSysReview(sysReview);
    }

    /**
     * 修改审核单
     * 
     * @param sysReview 审核单
     * @return 结果
     */
    @Override
    public int updateSysReview(SysReview sysReview)
    {
        sysReview.setUpdateTime(DateUtils.getNowDate());
        return sysReviewMapper.updateSysReview(sysReview);
    }

    /**
     * 批量删除审核单
     * 
     * @param reviewIds 需要删除的审核单主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewByReviewIds(String[] reviewIds)
    {
        return sysReviewMapper.deleteSysReviewByReviewIds(reviewIds);
    }

    /**
     * 删除审核单信息
     * 
     * @param reviewId 审核单主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewByReviewId(String reviewId)
    {
        return sysReviewMapper.deleteSysReviewByReviewId(reviewId);
    }

    /**
     * 发起审核单申请
     */
    @Override
    public int setSysReviewStatusByReviewId(SysReview sysReview)
    {
        return sysReviewMapper.setSysReviewStatusByReviewId(sysReview);
    }

    // 获取当前用户需要审核的待审核单
    @Override
    public List<SysReview> selectSysUpcomingReviewList(SysReview sysReview)
    {
        return sysReviewMapper.selectSysUpcomingReviewList(sysReview);
    }

    @Override
    public List<SysReview> selectCompletedReviewList(SysReview sysReview)
    {
        return sysReviewMapper.selectCompletedReviewList(sysReview);
    }

    @Override
    public List<SysReview> selectDoneReviewList(SysReview sysReview)
    {
        return sysReviewMapper.selectDoneReviewList(sysReview);
    }

    @Override
    public int updateSysReviewFinalTime(SysReview sysReview)
    {
        return sysReviewMapper.updateSysReviewFinalTime(sysReview);
    }

    @Override
    public int setSysReviewFinalSecondStatusByReviewId(SysReview sysReview)
    {
        return sysReviewMapper.setSysReviewFinalSecondStatusByReviewId(sysReview);
    }

    @Override
    public int updateStartEditByReviewId(SysReview sysReview)
    {
        return sysReviewMapper.updateStartEditByReviewId(sysReview);
    }


    @Override
    public int generateStatement(SysReview sysReview)
    {
        return sysReviewMapper.generateStatement(sysReview);
    }


    @Override
    public List<SysReview> settlementListReview(SysReview sysReview)
    {
        return sysReviewMapper.settlementListReview(sysReview);
    }

    @Override
    public List<SysReview> selectSysReviewListBySettlementId(SysReview sysReview)
    {
        return sysReviewMapper.selectSysReviewListBySettlementId(sysReview);
    }

    @Override
    public int updateFinalHireByReviewId(Long reviewId)
    {
        return sysReviewMapper.updateFinalHireByReviewId(reviewId);
    }
}
