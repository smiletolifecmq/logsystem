package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysCarReview;

import java.util.List;

/**
 * 车辆使用审核Mapper接口
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
public interface SysCarReviewMapper 
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
     * 删除车辆使用审核
     * 
     * @param carReviewId 车辆使用审核主键
     * @return 结果
     */
    public int deleteSysCarReviewByCarReviewId(Long carReviewId);

    /**
     * 批量删除车辆使用审核
     * 
     * @param carReviewIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCarReviewByCarReviewIds(Long[] carReviewIds);

    public SysCarReview checkProjectKeyUnique(SysCarReview sysCarReview);
}
