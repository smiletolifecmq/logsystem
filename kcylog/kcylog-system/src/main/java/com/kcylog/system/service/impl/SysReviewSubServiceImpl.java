package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.SysReviewSub;
import com.kcylog.system.mapper.SysReviewSubMapper;
import com.kcylog.system.service.ISysReviewSubService;
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
public class SysReviewSubServiceImpl implements ISysReviewSubService
{
    @Autowired
    private SysReviewSubMapper sysReviewSubMapper;

    /**
     * 查询审核单
     *
     * @param reviewId 审核单主键
     * @return 审核单
     */
    @Override
    public SysReviewSub selectSysReviewSubByReviewId(String reviewId)
    {
        return sysReviewSubMapper.selectSysReviewSubByReviewId(reviewId);
    }

    /**
     * 查询审核单列表
     *
     * @param sysReviewSub 审核单
     * @return 审核单
     */
    @Override
    public List<SysReviewSub> selectSysReviewSubList(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.selectSysReviewSubList(sysReviewSub);
    }

    /**
     * 新增审核单
     *
     * @param sysReviewSub 审核单
     * @return 结果
     */
    @Override
    public int insertSysReviewSub(SysReviewSub sysReviewSub)
    {
        sysReviewSub.setCreateTime(DateUtils.getNowDate());
        return sysReviewSubMapper.insertSysReviewSub(sysReviewSub);
    }

    /**
     * 修改审核单
     *
     * @param sysReviewSub 审核单
     * @return 结果
     */
    @Override
    public int updateSysReviewSub(SysReviewSub sysReviewSub)
    {
        sysReviewSub.setUpdateTime(DateUtils.getNowDate());
        return sysReviewSubMapper.updateSysReviewSub(sysReviewSub);
    }

    /**
     * 批量删除审核单
     *
     * @param reviewIds 需要删除的审核单主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSubByReviewIds(String[] reviewIds)
    {
        return sysReviewSubMapper.deleteSysReviewSubByReviewIds(reviewIds);
    }

    /**
     * 删除审核单信息
     *
     * @param reviewId 审核单主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSubByReviewId(String reviewId)
    {
        return sysReviewSubMapper.deleteSysReviewSubByReviewId(reviewId);
    }

    /**
     * 发起审核单申请
     */
    @Override
    public int setSysReviewSubStatusByReviewId(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.setSysReviewSubStatusByReviewId(sysReviewSub);
    }

    // 获取当前用户需要审核的待审核单
    @Override
    public List<SysReviewSub> selectSysUpcomingReviewList(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.selectSysUpcomingReviewList(sysReviewSub);
    }

    @Override
    public List<SysReviewSub> selectCompletedReviewList(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.selectCompletedReviewList(sysReviewSub);
    }

    @Override
    public List<SysReviewSub> selectDoneReviewList(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.selectDoneReviewList(sysReviewSub);
    }

    @Override
    public int updateSysReviewSubFinalTime(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.updateSysReviewSubFinalTime(sysReviewSub);
    }

    @Override
    public int setSysReviewSubFinalSecondStatusByReviewId(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.setSysReviewSubFinalSecondStatusByReviewId(sysReviewSub);
    }

    @Override
    public int updateStartEditByReviewId(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.updateStartEditByReviewId(sysReviewSub);
    }


    @Override
    public int generateStatement(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.generateStatement(sysReviewSub);
    }


    @Override
    public List<SysReviewSub> settlementListReview(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.settlementListReview(sysReviewSub);
    }

    @Override
    public List<SysReviewSub> selectSysReviewSubListBySettlementId(SysReviewSub sysReviewSub)
    {
        return sysReviewSubMapper.selectSysReviewSubListBySettlementId(sysReviewSub);
    }

    @Override
    public int updateFinalHireByReviewId(Long reviewId)
    {
        return sysReviewSubMapper.updateFinalHireByReviewId(reviewId);
    }

    @Override
    public SysReviewSub getReviewBySerialNum(String serialNum)
    {
        return sysReviewSubMapper.getReviewBySerialNum(serialNum);
    }

}
