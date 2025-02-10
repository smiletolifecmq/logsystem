package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysClienteleLlinfoMapper;
import com.kcylog.system.domain.SysClienteleLlinfo;
import com.kcylog.system.service.ISysClienteleLlinfoService;

/**
 * 客户履历Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
@Service
public class SysClienteleLlinfoServiceImpl implements ISysClienteleLlinfoService 
{
    @Autowired
    private SysClienteleLlinfoMapper sysClienteleLlinfoMapper;

    /**
     * 查询客户履历
     * 
     * @param id 客户履历主键
     * @return 客户履历
     */
    @Override
    public SysClienteleLlinfo selectSysClienteleLlinfoById(Long id)
    {
        return sysClienteleLlinfoMapper.selectSysClienteleLlinfoById(id);
    }

    /**
     * 查询客户履历列表
     * 
     * @param sysClienteleLlinfo 客户履历
     * @return 客户履历
     */
    @Override
    public List<SysClienteleLlinfo> selectSysClienteleLlinfoList(SysClienteleLlinfo sysClienteleLlinfo)
    {
        return sysClienteleLlinfoMapper.selectSysClienteleLlinfoList(sysClienteleLlinfo);
    }

    /**
     * 新增客户履历
     * 
     * @param sysClienteleLlinfo 客户履历
     * @return 结果
     */
    @Override
    public int insertSysClienteleLlinfo(SysClienteleLlinfo sysClienteleLlinfo)
    {
        return sysClienteleLlinfoMapper.insertSysClienteleLlinfo(sysClienteleLlinfo);
    }

    /**
     * 修改客户履历
     * 
     * @param sysClienteleLlinfo 客户履历
     * @return 结果
     */
    @Override
    public int updateSysClienteleLlinfo(SysClienteleLlinfo sysClienteleLlinfo)
    {
        return sysClienteleLlinfoMapper.updateSysClienteleLlinfo(sysClienteleLlinfo);
    }

    /**
     * 批量删除客户履历
     * 
     * @param ids 需要删除的客户履历主键
     * @return 结果
     */
    @Override
    public int deleteSysClienteleLlinfoByIds(Long[] ids)
    {
        return sysClienteleLlinfoMapper.deleteSysClienteleLlinfoByIds(ids);
    }

    /**
     * 删除客户履历信息
     * 
     * @param id 客户履历主键
     * @return 结果
     */
    @Override
    public int deleteSysClienteleLlinfoById(Long id)
    {
        return sysClienteleLlinfoMapper.deleteSysClienteleLlinfoById(id);
    }
}
