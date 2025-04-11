package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysKhdcmyd;
import com.kcylog.system.mapper.SysKhdcmydMapper;
import com.kcylog.system.service.ISysKhdcmydService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户调查满意度Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-04-11
 */
@Service
public class SysKhdcmydServiceImpl implements ISysKhdcmydService 
{
    @Autowired
    private SysKhdcmydMapper sysKhdcmydMapper;

    /**
     * 查询客户调查满意度
     * 
     * @param id 客户调查满意度主键
     * @return 客户调查满意度
     */
    @Override
    public SysKhdcmyd selectSysKhdcmydById(Long id)
    {
        return sysKhdcmydMapper.selectSysKhdcmydById(id);
    }

    /**
     * 查询客户调查满意度列表
     * 
     * @param sysKhdcmyd 客户调查满意度
     * @return 客户调查满意度
     */
    @Override
    public List<SysKhdcmyd> selectSysKhdcmydList(SysKhdcmyd sysKhdcmyd)
    {
        return sysKhdcmydMapper.selectSysKhdcmydList(sysKhdcmyd);
    }

    /**
     * 新增客户调查满意度
     * 
     * @param sysKhdcmyd 客户调查满意度
     * @return 结果
     */
    @Override
    public int insertSysKhdcmyd(SysKhdcmyd sysKhdcmyd)
    {
        return sysKhdcmydMapper.insertSysKhdcmyd(sysKhdcmyd);
    }

    /**
     * 修改客户调查满意度
     * 
     * @param sysKhdcmyd 客户调查满意度
     * @return 结果
     */
    @Override
    public int updateSysKhdcmyd(SysKhdcmyd sysKhdcmyd)
    {
        return sysKhdcmydMapper.updateSysKhdcmyd(sysKhdcmyd);
    }

    /**
     * 批量删除客户调查满意度
     * 
     * @param ids 需要删除的客户调查满意度主键
     * @return 结果
     */
    @Override
    public int deleteSysKhdcmydByIds(Long[] ids)
    {
        return sysKhdcmydMapper.deleteSysKhdcmydByIds(ids);
    }

    /**
     * 删除客户调查满意度信息
     * 
     * @param id 客户调查满意度主键
     * @return 结果
     */
    @Override
    public int deleteSysKhdcmydById(Long id)
    {
        return sysKhdcmydMapper.deleteSysKhdcmydById(id);
    }
}
