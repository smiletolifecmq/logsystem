package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.SysBcGcbb;
import com.kcylog.system.mapper.SysBcGcbbMapper;
import com.kcylog.system.service.ISysBcGcbbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 百川工程报表Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-08
 */
@Service
public class SysBcGcbbServiceImpl implements ISysBcGcbbService 
{
    @Autowired
    private SysBcGcbbMapper sysBcGcbbMapper;

    /**
     * 查询百川工程报表
     * 
     * @param id 百川工程报表主键
     * @return 百川工程报表
     */
    @Override
    public SysBcGcbb selectSysBcGcbbById(Long id)
    {
        return sysBcGcbbMapper.selectSysBcGcbbById(id);
    }

    /**
     * 查询百川工程报表列表
     * 
     * @param sysBcGcbb 百川工程报表
     * @return 百川工程报表
     */
    @Override
    public List<SysBcGcbb> selectSysBcGcbbList(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbList(sysBcGcbb);
    }

    /**
     * 新增百川工程报表
     * 
     * @param sysBcGcbb 百川工程报表
     * @return 结果
     */
    @Override
    public int insertSysBcGcbb(SysBcGcbb sysBcGcbb)
    {
        sysBcGcbb.setCreateTime(DateUtils.getNowDate());
        return sysBcGcbbMapper.insertSysBcGcbb(sysBcGcbb);
    }

    /**
     * 修改百川工程报表
     * 
     * @param sysBcGcbb 百川工程报表
     * @return 结果
     */
    @Override
    public int updateSysBcGcbb(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.updateSysBcGcbb(sysBcGcbb);
    }

    /**
     * 批量删除百川工程报表
     * 
     * @param ids 需要删除的百川工程报表主键
     * @return 结果
     */
    @Override
    public int deleteSysBcGcbbByIds(Long[] ids)
    {
        return sysBcGcbbMapper.deleteSysBcGcbbByIds(ids);
    }

    /**
     * 删除百川工程报表信息
     * 
     * @param id 百川工程报表主键
     * @return 结果
     */
    @Override
    public int deleteSysBcGcbbById(Long id)
    {
        return sysBcGcbbMapper.deleteSysBcGcbbById(id);
    }

    @Override
    public int deleteSysBcGcbbByYear(String year)
    {
        return sysBcGcbbMapper.deleteSysBcGcbbByYear(year);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbTj(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbTj(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForTotalCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForTotalCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForWeekCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForWeekCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForSellYearCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForSellYearCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForSellWeekCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForSellWeekCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForPlanYearCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForPlanYearCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForPlanWeekCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForPlanWeekCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForJiCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForJiCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForPipeCctvCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForPipeCctvCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForPipeOtherCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForPipeOtherCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForGovYearEarthControlCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForGovYearEarthControlCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForGovYearRoadCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForGovYearRoadCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForGovWeekEarthControlCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForGovWeekEarthControlCount(sysBcGcbb);
    }

    @Override
    public List<SysBcGcbb> selectSysBcGcbbListForGovWeekRoadCount(SysBcGcbb sysBcGcbb)
    {
        return sysBcGcbbMapper.selectSysBcGcbbListForGovWeekRoadCount(sysBcGcbb);
    }
}
