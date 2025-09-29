package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysServiceWorkTypeMapper;
import com.kcylog.system.domain.SysServiceWorkType;
import com.kcylog.system.service.ISysServiceWorkTypeService;

/**
 * 内部服务类型表Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-09-28
 */
@Service
public class SysServiceWorkTypeServiceImpl implements ISysServiceWorkTypeService 
{
    @Autowired
    private SysServiceWorkTypeMapper sysServiceWorkTypeMapper;

    /**
     * 查询内部服务类型表
     * 
     * @param id 内部服务类型表主键
     * @return 内部服务类型表
     */
    @Override
    public SysServiceWorkType selectSysServiceWorkTypeById(Long id)
    {
        return sysServiceWorkTypeMapper.selectSysServiceWorkTypeById(id);
    }

    /**
     * 查询内部服务类型表列表
     * 
     * @param sysServiceWorkType 内部服务类型表
     * @return 内部服务类型表
     */
    @Override
    public List<SysServiceWorkType> selectSysServiceWorkTypeList(SysServiceWorkType sysServiceWorkType)
    {
        return sysServiceWorkTypeMapper.selectSysServiceWorkTypeList(sysServiceWorkType);
    }

    /**
     * 新增内部服务类型表
     * 
     * @param sysServiceWorkType 内部服务类型表
     * @return 结果
     */
    @Override
    public int insertSysServiceWorkType(SysServiceWorkType sysServiceWorkType)
    {
        return sysServiceWorkTypeMapper.insertSysServiceWorkType(sysServiceWorkType);
    }

    /**
     * 修改内部服务类型表
     * 
     * @param sysServiceWorkType 内部服务类型表
     * @return 结果
     */
    @Override
    public int updateSysServiceWorkType(SysServiceWorkType sysServiceWorkType)
    {
        return sysServiceWorkTypeMapper.updateSysServiceWorkType(sysServiceWorkType);
    }

    /**
     * 批量删除内部服务类型表
     * 
     * @param ids 需要删除的内部服务类型表主键
     * @return 结果
     */
    @Override
    public int deleteSysServiceWorkTypeByIds(Long[] ids)
    {
        return sysServiceWorkTypeMapper.deleteSysServiceWorkTypeByIds(ids);
    }

    /**
     * 删除内部服务类型表信息
     * 
     * @param id 内部服务类型表主键
     * @return 结果
     */
    @Override
    public int deleteSysServiceWorkTypeById(Long id)
    {
        return sysServiceWorkTypeMapper.deleteSysServiceWorkTypeById(id);
    }
}
