package com.kcylog.system.service;

import com.kcylog.system.domain.SysBcGcbb;

import java.util.List;

/**
 * 百川工程报表Service接口
 * 
 * @author ruoyi
 * @date 2025-07-08
 */
public interface ISysBcGcbbService 
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
     * 批量删除百川工程报表
     * 
     * @param ids 需要删除的百川工程报表主键集合
     * @return 结果
     */
    public int deleteSysBcGcbbByIds(Long[] ids);

    /**
     * 删除百川工程报表信息
     * 
     * @param id 百川工程报表主键
     * @return 结果
     */
    public int deleteSysBcGcbbById(Long id);

    public int deleteSysBcGcbbByYear(String year);
}
