package com.kcylog.system.service;

import com.kcylog.system.domain.SysServiceWorkType;

import java.util.List;

/**
 * 内部服务类型表Service接口
 * 
 * @author ruoyi
 * @date 2025-09-28
 */
public interface ISysServiceWorkTypeService 
{
    /**
     * 查询内部服务类型表
     * 
     * @param id 内部服务类型表主键
     * @return 内部服务类型表
     */
    public SysServiceWorkType selectSysServiceWorkTypeById(Long id);

    /**
     * 查询内部服务类型表列表
     * 
     * @param sysServiceWorkType 内部服务类型表
     * @return 内部服务类型表集合
     */
    public List<SysServiceWorkType> selectSysServiceWorkTypeList(SysServiceWorkType sysServiceWorkType);

    /**
     * 新增内部服务类型表
     * 
     * @param sysServiceWorkType 内部服务类型表
     * @return 结果
     */
    public int insertSysServiceWorkType(SysServiceWorkType sysServiceWorkType);

    /**
     * 修改内部服务类型表
     * 
     * @param sysServiceWorkType 内部服务类型表
     * @return 结果
     */
    public int updateSysServiceWorkType(SysServiceWorkType sysServiceWorkType);

    /**
     * 批量删除内部服务类型表
     * 
     * @param ids 需要删除的内部服务类型表主键集合
     * @return 结果
     */
    public int deleteSysServiceWorkTypeByIds(Long[] ids);

    /**
     * 删除内部服务类型表信息
     * 
     * @param id 内部服务类型表主键
     * @return 结果
     */
    public int deleteSysServiceWorkTypeById(Long id);
}
