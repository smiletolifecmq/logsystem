package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysClientele;

import java.util.List;

/**
 * 客户信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public interface SysClienteleMapper 
{
    /**
     * 查询客户信息
     * 
     * @param id 客户信息主键
     * @return 客户信息
     */
    public SysClientele selectSysClienteleById(Long id);

    /**
     * 查询客户信息列表
     * 
     * @param sysClientele 客户信息
     * @return 客户信息集合
     */
    public List<SysClientele> selectSysClienteleList(SysClientele sysClientele);

    /**
     * 新增客户信息
     * 
     * @param sysClientele 客户信息
     * @return 结果
     */
    public int insertSysClientele(SysClientele sysClientele);

    /**
     * 修改客户信息
     * 
     * @param sysClientele 客户信息
     * @return 结果
     */
    public int updateSysClientele(SysClientele sysClientele);

    /**
     * 删除客户信息
     * 
     * @param id 客户信息主键
     * @return 结果
     */
    public int deleteSysClienteleById(Long id);

    /**
     * 批量删除客户信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysClienteleByIds(Long[] ids);
}
