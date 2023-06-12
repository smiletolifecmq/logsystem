package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysReview;

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
}
