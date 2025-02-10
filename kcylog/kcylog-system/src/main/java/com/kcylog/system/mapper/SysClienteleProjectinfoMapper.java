package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysClienteleProjectinfo;

import java.util.List;

/**
 * 客户项目对接情况Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public interface SysClienteleProjectinfoMapper 
{
    /**
     * 查询客户项目对接情况
     * 
     * @param id 客户项目对接情况主键
     * @return 客户项目对接情况
     */
    public SysClienteleProjectinfo selectSysClienteleProjectinfoById(Long id);

    /**
     * 查询客户项目对接情况列表
     * 
     * @param sysClienteleProjectinfo 客户项目对接情况
     * @return 客户项目对接情况集合
     */
    public List<SysClienteleProjectinfo> selectSysClienteleProjectinfoList(SysClienteleProjectinfo sysClienteleProjectinfo);

    /**
     * 新增客户项目对接情况
     * 
     * @param sysClienteleProjectinfo 客户项目对接情况
     * @return 结果
     */
    public int insertSysClienteleProjectinfo(SysClienteleProjectinfo sysClienteleProjectinfo);

    /**
     * 修改客户项目对接情况
     * 
     * @param sysClienteleProjectinfo 客户项目对接情况
     * @return 结果
     */
    public int updateSysClienteleProjectinfo(SysClienteleProjectinfo sysClienteleProjectinfo);

    /**
     * 删除客户项目对接情况
     * 
     * @param id 客户项目对接情况主键
     * @return 结果
     */
    public int deleteSysClienteleProjectinfoById(Long id);

    /**
     * 批量删除客户项目对接情况
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysClienteleProjectinfoByIds(Long[] ids);
}
