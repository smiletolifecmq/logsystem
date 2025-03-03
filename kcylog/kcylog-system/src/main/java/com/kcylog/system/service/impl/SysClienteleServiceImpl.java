package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysClienteleMapper;
import com.kcylog.system.domain.SysClientele;
import com.kcylog.system.service.ISysClienteleService;

/**
 * 客户信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
@Service
public class SysClienteleServiceImpl implements ISysClienteleService 
{
    @Autowired
    private SysClienteleMapper sysClienteleMapper;

    /**
     * 查询客户信息
     * 
     * @param id 客户信息主键
     * @return 客户信息
     */
    @Override
    public SysClientele selectSysClienteleById(Long id)
    {
        return sysClienteleMapper.selectSysClienteleById(id);
    }

    /**
     * 查询客户信息列表
     * 
     * @param sysClientele 客户信息
     * @return 客户信息
     */
    @Override
    public List<SysClientele> selectSysClienteleList(SysClientele sysClientele)
    {
        return sysClienteleMapper.selectSysClienteleList(sysClientele);
    }

    /**
     * 新增客户信息
     * 
     * @param sysClientele 客户信息
     * @return 结果
     */
    @Override
    public int insertSysClientele(SysClientele sysClientele)
    {
        return sysClienteleMapper.insertSysClientele(sysClientele);
    }

    /**
     * 修改客户信息
     * 
     * @param sysClientele 客户信息
     * @return 结果
     */
    @Override
    public int updateSysClientele(SysClientele sysClientele)
    {
        return sysClienteleMapper.updateSysClientele(sysClientele);
    }

    /**
     * 批量删除客户信息
     * 
     * @param ids 需要删除的客户信息主键
     * @return 结果
     */
    @Override
    public int deleteSysClienteleByIds(Long[] ids)
    {
        return sysClienteleMapper.deleteSysClienteleByIds(ids);
    }

    /**
     * 删除客户信息信息
     * 
     * @param id 客户信息主键
     * @return 结果
     */
    @Override
    public int deleteSysClienteleById(Long id)
    {
        return sysClienteleMapper.deleteSysClienteleById(id);
    }

    @Override
    public List<SysClientele> selectSysClienteleListForUnit()
    {
        return sysClienteleMapper.selectSysClienteleListForUnit();
    }
}
