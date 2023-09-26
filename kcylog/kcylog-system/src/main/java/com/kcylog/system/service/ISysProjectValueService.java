package com.kcylog.system.service;

import com.kcylog.system.domain.SysProjectValue;

import java.util.List;

/**
 * 项目产值Service接口
 * 
 * @author ruoyi
 * @date 2023-09-26
 */
public interface ISysProjectValueService 
{
    /**
     * 查询项目产值
     * 
     * @param valueId 项目产值主键
     * @return 项目产值
     */
    public SysProjectValue selectSysProjectValueByValueId(Long valueId);

    /**
     * 查询项目产值列表
     * 
     * @param sysProjectValue 项目产值
     * @return 项目产值集合
     */
    public List<SysProjectValue> selectSysProjectValueList(SysProjectValue sysProjectValue);

    /**
     * 新增项目产值
     * 
     * @param sysProjectValue 项目产值
     * @return 结果
     */
    public int insertSysProjectValue(SysProjectValue sysProjectValue);

    /**
     * 修改项目产值
     * 
     * @param sysProjectValue 项目产值
     * @return 结果
     */
    public int updateSysProjectValue(SysProjectValue sysProjectValue);

    /**
     * 批量删除项目产值
     * 
     * @param valueIds 需要删除的项目产值主键集合
     * @return 结果
     */
    public int deleteSysProjectValueByValueIds(Long[] valueIds);

    /**
     * 删除项目产值信息
     * 
     * @param valueId 项目产值主键
     * @return 结果
     */
    public int deleteSysProjectValueByValueId(Long valueId);

    public int deleteSysProjectValueByProjectId(Long projectId);

}
