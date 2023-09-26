package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysProjectValue;
import com.kcylog.system.mapper.SysProjectValueMapper;
import com.kcylog.system.service.ISysProjectValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目产值Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-26
 */
@Service
public class SysProjectValueServiceImpl implements ISysProjectValueService 
{
    @Autowired
    private SysProjectValueMapper sysProjectValueMapper;

    /**
     * 查询项目产值
     * 
     * @param valueId 项目产值主键
     * @return 项目产值
     */
    @Override
    public SysProjectValue selectSysProjectValueByValueId(Long valueId)
    {
        return sysProjectValueMapper.selectSysProjectValueByValueId(valueId);
    }

    /**
     * 查询项目产值列表
     * 
     * @param sysProjectValue 项目产值
     * @return 项目产值
     */
    @Override
    public List<SysProjectValue> selectSysProjectValueList(SysProjectValue sysProjectValue)
    {
        return sysProjectValueMapper.selectSysProjectValueList(sysProjectValue);
    }

    /**
     * 新增项目产值
     * 
     * @param sysProjectValue 项目产值
     * @return 结果
     */
    @Override
    public int insertSysProjectValue(SysProjectValue sysProjectValue)
    {
        return sysProjectValueMapper.insertSysProjectValue(sysProjectValue);
    }

    /**
     * 修改项目产值
     * 
     * @param sysProjectValue 项目产值
     * @return 结果
     */
    @Override
    public int updateSysProjectValue(SysProjectValue sysProjectValue)
    {
        return sysProjectValueMapper.updateSysProjectValue(sysProjectValue);
    }

    /**
     * 批量删除项目产值
     * 
     * @param valueIds 需要删除的项目产值主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectValueByValueIds(Long[] valueIds)
    {
        return sysProjectValueMapper.deleteSysProjectValueByValueIds(valueIds);
    }

    /**
     * 删除项目产值信息
     * 
     * @param valueId 项目产值主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectValueByValueId(Long valueId)
    {
        return sysProjectValueMapper.deleteSysProjectValueByValueId(valueId);
    }

    @Override
    public int deleteSysProjectValueByProjectId(Long projectId)
    {
        return sysProjectValueMapper.deleteSysProjectValueByProjectId(projectId);
    }
}
