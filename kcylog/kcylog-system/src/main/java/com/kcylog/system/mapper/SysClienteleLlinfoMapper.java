package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysClienteleLlinfo;

import java.util.List;

/**
 * 客户履历Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public interface SysClienteleLlinfoMapper 
{
    /**
     * 查询客户履历
     * 
     * @param id 客户履历主键
     * @return 客户履历
     */
    public SysClienteleLlinfo selectSysClienteleLlinfoById(Long id);

    /**
     * 查询客户履历列表
     * 
     * @param sysClienteleLlinfo 客户履历
     * @return 客户履历集合
     */
    public List<SysClienteleLlinfo> selectSysClienteleLlinfoList(SysClienteleLlinfo sysClienteleLlinfo);

    /**
     * 新增客户履历
     * 
     * @param sysClienteleLlinfo 客户履历
     * @return 结果
     */
    public int insertSysClienteleLlinfo(SysClienteleLlinfo sysClienteleLlinfo);

    /**
     * 修改客户履历
     * 
     * @param sysClienteleLlinfo 客户履历
     * @return 结果
     */
    public int updateSysClienteleLlinfo(SysClienteleLlinfo sysClienteleLlinfo);

    /**
     * 删除客户履历
     * 
     * @param id 客户履历主键
     * @return 结果
     */
    public int deleteSysClienteleLlinfoById(Long id);

    /**
     * 批量删除客户履历
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysClienteleLlinfoByIds(Long[] ids);
}
