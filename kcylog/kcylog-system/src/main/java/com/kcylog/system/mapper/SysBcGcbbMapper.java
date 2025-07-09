package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysBcGcbb;

import java.util.List;

/**
 * 百川工程报表Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-08
 */
public interface SysBcGcbbMapper 
{
    /**
     * 查询百川工程报表
     * 
     * @param id 百川工程报表主键
     * @return 百川工程报表
     */
    public SysBcGcbb selectSysBcGcbbById(Long id);

    /**
     * 查询百川工程报表列表
     * 
     * @param sysBcGcbb 百川工程报表
     * @return 百川工程报表集合
     */
    public List<SysBcGcbb> selectSysBcGcbbList(SysBcGcbb sysBcGcbb);

    /**
     * 新增百川工程报表
     * 
     * @param sysBcGcbb 百川工程报表
     * @return 结果
     */
    public int insertSysBcGcbb(SysBcGcbb sysBcGcbb);

    /**
     * 修改百川工程报表
     * 
     * @param sysBcGcbb 百川工程报表
     * @return 结果
     */
    public int updateSysBcGcbb(SysBcGcbb sysBcGcbb);

    /**
     * 删除百川工程报表
     * 
     * @param id 百川工程报表主键
     * @return 结果
     */
    public int deleteSysBcGcbbById(Long id);

    /**
     * 批量删除百川工程报表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysBcGcbbByIds(Long[] ids);

    public int deleteSysBcGcbbByYear(String year);

    public List<SysBcGcbb> selectSysBcGcbbTj(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForTotalCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForWeekCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForSellYearCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForSellWeekCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForPlanYearCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForPlanWeekCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForJiCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForPipeCctvCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForPipeOtherCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForGovYearEarthControlCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForGovYearRoadCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForGovWeekEarthControlCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForGovWeekRoadCount(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForJiCountMonth(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForPipeCctvCountMonth(SysBcGcbb sysBcGcbb);

    public List<SysBcGcbb> selectSysBcGcbbListForPipeOtherCountMonth(SysBcGcbb sysBcGcbb);

}
