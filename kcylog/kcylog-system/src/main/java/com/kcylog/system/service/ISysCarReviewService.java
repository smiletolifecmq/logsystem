package com.kcylog.system.service;

import com.kcylog.system.domain.SysCarReview;

import java.util.List;

/**
 * 车辆使用审核Service接口
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
public interface ISysCarReviewService 
{
    /**
     * 查询车辆使用审核
     * 
     * @param carReviewId 车辆使用审核主键
     * @return 车辆使用审核
     */
    public SysCarReview selectSysCarReviewByCarReviewId(Long carReviewId);

    /**
     * 查询车辆使用审核列表
     * 
     * @param sysCarReview 车辆使用审核
     * @return 车辆使用审核集合
     */
    public List<SysCarReview> selectSysCarReviewList(SysCarReview sysCarReview);

    /**
     * 新增车辆使用审核
     * 
     * @param sysCarReview 车辆使用审核
     * @return 结果
     */
    public int insertSysCarReview(SysCarReview sysCarReview);

    /**
     * 修改车辆使用审核
     * 
     * @param sysCarReview 车辆使用审核
     * @return 结果
     */
    public int updateSysCarReview(SysCarReview sysCarReview);

    /**
     * 批量删除车辆使用审核
     * 
     * @param carReviewIds 需要删除的车辆使用审核主键集合
     * @return 结果
     */
    public int deleteSysCarReviewByCarReviewIds(Long[] carReviewIds);

    /**
     * 删除车辆使用审核信息
     * 
     * @param carReviewId 车辆使用审核主键
     * @return 结果
     */
    public int deleteSysCarReviewByCarReviewId(Long carReviewId);

    public SysCarReview checkProjectKeyUnique(SysCarReview sysCarReview);

    public int setSysReviewStatusByCarReviewId(SysCarReview sysCarReview);

    public List<SysCarReview> selectSysUpcomingReviewList(SysCarReview sysCarReview);

    public List<SysCarReview> selectSysDoneReviewList(SysCarReview sysCarReview);
}
