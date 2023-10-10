package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysCarReviewMapper;
import com.kcylog.system.domain.SysCarReview;
import com.kcylog.system.service.ISysCarReviewService;

/**
 * 车辆使用审核Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@Service
public class SysCarReviewServiceImpl implements ISysCarReviewService 
{
    @Autowired
    private SysCarReviewMapper sysCarReviewMapper;

    /**
     * 查询车辆使用审核
     * 
     * @param carReviewId 车辆使用审核主键
     * @return 车辆使用审核
     */
    @Override
    public SysCarReview selectSysCarReviewByCarReviewId(Long carReviewId)
    {
        return sysCarReviewMapper.selectSysCarReviewByCarReviewId(carReviewId);
    }

    /**
     * 查询车辆使用审核列表
     * 
     * @param sysCarReview 车辆使用审核
     * @return 车辆使用审核
     */
    @Override
    public List<SysCarReview> selectSysCarReviewList(SysCarReview sysCarReview)
    {
        return sysCarReviewMapper.selectSysCarReviewList(sysCarReview);
    }

    /**
     * 新增车辆使用审核
     * 
     * @param sysCarReview 车辆使用审核
     * @return 结果
     */
    @Override
    public int insertSysCarReview(SysCarReview sysCarReview)
    {
        return sysCarReviewMapper.insertSysCarReview(sysCarReview);
    }

    /**
     * 修改车辆使用审核
     * 
     * @param sysCarReview 车辆使用审核
     * @return 结果
     */
    @Override
    public int updateSysCarReview(SysCarReview sysCarReview)
    {
        return sysCarReviewMapper.updateSysCarReview(sysCarReview);
    }

    /**
     * 批量删除车辆使用审核
     * 
     * @param carReviewIds 需要删除的车辆使用审核主键
     * @return 结果
     */
    @Override
    public int deleteSysCarReviewByCarReviewIds(Long[] carReviewIds)
    {
        return sysCarReviewMapper.deleteSysCarReviewByCarReviewIds(carReviewIds);
    }

    /**
     * 删除车辆使用审核信息
     * 
     * @param carReviewId 车辆使用审核主键
     * @return 结果
     */
    @Override
    public int deleteSysCarReviewByCarReviewId(Long carReviewId)
    {
        return sysCarReviewMapper.deleteSysCarReviewByCarReviewId(carReviewId);
    }

    @Override
    public SysCarReview checkProjectKeyUnique(SysCarReview sysCarReview)
    {
        return sysCarReviewMapper.checkProjectKeyUnique(sysCarReview);
    }
}
