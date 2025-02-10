package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysClienteleProjectinfoMapper;
import com.kcylog.system.domain.SysClienteleProjectinfo;
import com.kcylog.system.service.ISysClienteleProjectinfoService;

/**
 * 客户项目对接情况Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
@Service
public class SysClienteleProjectinfoServiceImpl implements ISysClienteleProjectinfoService 
{
    @Autowired
    private SysClienteleProjectinfoMapper sysClienteleProjectinfoMapper;

    /**
     * 查询客户项目对接情况
     * 
     * @param id 客户项目对接情况主键
     * @return 客户项目对接情况
     */
    @Override
    public SysClienteleProjectinfo selectSysClienteleProjectinfoById(Long id)
    {
        return sysClienteleProjectinfoMapper.selectSysClienteleProjectinfoById(id);
    }

    /**
     * 查询客户项目对接情况列表
     * 
     * @param sysClienteleProjectinfo 客户项目对接情况
     * @return 客户项目对接情况
     */
    @Override
    public List<SysClienteleProjectinfo> selectSysClienteleProjectinfoList(SysClienteleProjectinfo sysClienteleProjectinfo)
    {
        return sysClienteleProjectinfoMapper.selectSysClienteleProjectinfoList(sysClienteleProjectinfo);
    }

    /**
     * 新增客户项目对接情况
     * 
     * @param sysClienteleProjectinfo 客户项目对接情况
     * @return 结果
     */
    @Override
    public int insertSysClienteleProjectinfo(SysClienteleProjectinfo sysClienteleProjectinfo)
    {
        return sysClienteleProjectinfoMapper.insertSysClienteleProjectinfo(sysClienteleProjectinfo);
    }

    /**
     * 修改客户项目对接情况
     * 
     * @param sysClienteleProjectinfo 客户项目对接情况
     * @return 结果
     */
    @Override
    public int updateSysClienteleProjectinfo(SysClienteleProjectinfo sysClienteleProjectinfo)
    {
        return sysClienteleProjectinfoMapper.updateSysClienteleProjectinfo(sysClienteleProjectinfo);
    }

    /**
     * 批量删除客户项目对接情况
     * 
     * @param ids 需要删除的客户项目对接情况主键
     * @return 结果
     */
    @Override
    public int deleteSysClienteleProjectinfoByIds(Long[] ids)
    {
        return sysClienteleProjectinfoMapper.deleteSysClienteleProjectinfoByIds(ids);
    }

    /**
     * 删除客户项目对接情况信息
     * 
     * @param id 客户项目对接情况主键
     * @return 结果
     */
    @Override
    public int deleteSysClienteleProjectinfoById(Long id)
    {
        return sysClienteleProjectinfoMapper.deleteSysClienteleProjectinfoById(id);
    }
}
