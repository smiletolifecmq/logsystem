package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysBcJybbMapper;
import com.kcylog.system.domain.SysBcJybb;
import com.kcylog.system.service.ISysBcJybbService;

/**
 * 百川分院经营金额Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-07
 */
@Service
public class SysBcJybbServiceImpl implements ISysBcJybbService 
{
    @Autowired
    private SysBcJybbMapper sysBcJybbMapper;

    /**
     * 查询百川分院经营金额
     * 
     * @param id 百川分院经营金额主键
     * @return 百川分院经营金额
     */
    @Override
    public SysBcJybb selectSysBcJybbById(Long id)
    {
        return sysBcJybbMapper.selectSysBcJybbById(id);
    }

    /**
     * 查询百川分院经营金额列表
     * 
     * @param sysBcJybb 百川分院经营金额
     * @return 百川分院经营金额
     */
    @Override
    public List<SysBcJybb> selectSysBcJybbList(SysBcJybb sysBcJybb)
    {
        return sysBcJybbMapper.selectSysBcJybbList(sysBcJybb);
    }

    /**
     * 新增百川分院经营金额
     * 
     * @param sysBcJybb 百川分院经营金额
     * @return 结果
     */
    @Override
    public int insertSysBcJybb(SysBcJybb sysBcJybb)
    {
        return sysBcJybbMapper.insertSysBcJybb(sysBcJybb);
    }

    /**
     * 修改百川分院经营金额
     * 
     * @param sysBcJybb 百川分院经营金额
     * @return 结果
     */
    @Override
    public int updateSysBcJybb(SysBcJybb sysBcJybb)
    {
        return sysBcJybbMapper.updateSysBcJybb(sysBcJybb);
    }

    /**
     * 批量删除百川分院经营金额
     * 
     * @param ids 需要删除的百川分院经营金额主键
     * @return 结果
     */
    @Override
    public int deleteSysBcJybbByIds(Long[] ids)
    {
        return sysBcJybbMapper.deleteSysBcJybbByIds(ids);
    }

    /**
     * 删除百川分院经营金额信息
     * 
     * @param id 百川分院经营金额主键
     * @return 结果
     */
    @Override
    public int deleteSysBcJybbById(Long id)
    {
        return sysBcJybbMapper.deleteSysBcJybbById(id);
    }
}
