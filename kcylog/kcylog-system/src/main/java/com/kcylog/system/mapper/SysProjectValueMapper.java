package com.kcylog.system.mapper;

import java.util.List;
import com.kcylog.system.domain.SysProjectValue;

/**
 * 项目产值Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-26
 */
public interface SysProjectValueMapper 
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
     * 删除项目产值
     * 
     * @param valueId 项目产值主键
     * @return 结果
     */
    public int deleteSysProjectValueByValueId(Long valueId);

    /**
     * 批量删除项目产值
     * 
     * @param valueIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysProjectValueByValueIds(Long[] valueIds);
}
