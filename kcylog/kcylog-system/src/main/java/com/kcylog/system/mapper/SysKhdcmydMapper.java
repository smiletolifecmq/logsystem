package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysKhdcmyd;

import java.util.List;

/**
 * 客户调查满意度Mapper接口
 * 
 * @author ruoyi
 * @date 2025-04-11
 */
public interface SysKhdcmydMapper 
{
    /**
     * 查询客户调查满意度
     * 
     * @param id 客户调查满意度主键
     * @return 客户调查满意度
     */
    public SysKhdcmyd selectSysKhdcmydById(Long id);

    /**
     * 查询客户调查满意度列表
     * 
     * @param sysKhdcmyd 客户调查满意度
     * @return 客户调查满意度集合
     */
    public List<SysKhdcmyd> selectSysKhdcmydList(SysKhdcmyd sysKhdcmyd);

    /**
     * 新增客户调查满意度
     * 
     * @param sysKhdcmyd 客户调查满意度
     * @return 结果
     */
    public int insertSysKhdcmyd(SysKhdcmyd sysKhdcmyd);

    /**
     * 修改客户调查满意度
     * 
     * @param sysKhdcmyd 客户调查满意度
     * @return 结果
     */
    public int updateSysKhdcmyd(SysKhdcmyd sysKhdcmyd);

    /**
     * 删除客户调查满意度
     * 
     * @param id 客户调查满意度主键
     * @return 结果
     */
    public int deleteSysKhdcmydById(Long id);

    /**
     * 批量删除客户调查满意度
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysKhdcmydByIds(Long[] ids);
}
