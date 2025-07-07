package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysBcJybb;

import java.util.List;

/**
 * 百川分院经营金额Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-07
 */
public interface SysBcJybbMapper 
{
    /**
     * 查询百川分院经营金额
     * 
     * @param id 百川分院经营金额主键
     * @return 百川分院经营金额
     */
    public SysBcJybb selectSysBcJybbById(Long id);

    /**
     * 查询百川分院经营金额列表
     * 
     * @param sysBcJybb 百川分院经营金额
     * @return 百川分院经营金额集合
     */
    public List<SysBcJybb> selectSysBcJybbList(SysBcJybb sysBcJybb);

    /**
     * 新增百川分院经营金额
     * 
     * @param sysBcJybb 百川分院经营金额
     * @return 结果
     */
    public int insertSysBcJybb(SysBcJybb sysBcJybb);

    /**
     * 修改百川分院经营金额
     * 
     * @param sysBcJybb 百川分院经营金额
     * @return 结果
     */
    public int updateSysBcJybb(SysBcJybb sysBcJybb);

    /**
     * 删除百川分院经营金额
     * 
     * @param id 百川分院经营金额主键
     * @return 结果
     */
    public int deleteSysBcJybbById(Long id);

    /**
     * 批量删除百川分院经营金额
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysBcJybbByIds(Long[] ids);
}
