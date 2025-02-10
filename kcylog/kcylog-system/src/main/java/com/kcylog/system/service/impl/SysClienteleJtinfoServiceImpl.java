package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysClienteleJtinfoMapper;
import com.kcylog.system.domain.SysClienteleJtinfo;
import com.kcylog.system.service.ISysClienteleJtinfoService;

/**
 * 客户家庭信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
@Service
public class SysClienteleJtinfoServiceImpl implements ISysClienteleJtinfoService 
{
    @Autowired
    private SysClienteleJtinfoMapper sysClienteleJtinfoMapper;

    /**
     * 查询客户家庭信息
     * 
     * @param id 客户家庭信息主键
     * @return 客户家庭信息
     */
    @Override
    public SysClienteleJtinfo selectSysClienteleJtinfoById(Long id)
    {
        return sysClienteleJtinfoMapper.selectSysClienteleJtinfoById(id);
    }

    /**
     * 查询客户家庭信息列表
     * 
     * @param sysClienteleJtinfo 客户家庭信息
     * @return 客户家庭信息
     */
    @Override
    public List<SysClienteleJtinfo> selectSysClienteleJtinfoList(SysClienteleJtinfo sysClienteleJtinfo)
    {
        return sysClienteleJtinfoMapper.selectSysClienteleJtinfoList(sysClienteleJtinfo);
    }

    /**
     * 新增客户家庭信息
     * 
     * @param sysClienteleJtinfo 客户家庭信息
     * @return 结果
     */
    @Override
    public int insertSysClienteleJtinfo(SysClienteleJtinfo sysClienteleJtinfo)
    {
        return sysClienteleJtinfoMapper.insertSysClienteleJtinfo(sysClienteleJtinfo);
    }

    /**
     * 修改客户家庭信息
     * 
     * @param sysClienteleJtinfo 客户家庭信息
     * @return 结果
     */
    @Override
    public int updateSysClienteleJtinfo(SysClienteleJtinfo sysClienteleJtinfo)
    {
        return sysClienteleJtinfoMapper.updateSysClienteleJtinfo(sysClienteleJtinfo);
    }

    /**
     * 批量删除客户家庭信息
     * 
     * @param ids 需要删除的客户家庭信息主键
     * @return 结果
     */
    @Override
    public int deleteSysClienteleJtinfoByIds(Long[] ids)
    {
        return sysClienteleJtinfoMapper.deleteSysClienteleJtinfoByIds(ids);
    }

    /**
     * 删除客户家庭信息信息
     * 
     * @param id 客户家庭信息主键
     * @return 结果
     */
    @Override
    public int deleteSysClienteleJtinfoById(Long id)
    {
        return sysClienteleJtinfoMapper.deleteSysClienteleJtinfoById(id);
    }
}
