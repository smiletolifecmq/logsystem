package com.kcylog.system.mapper;

import java.util.List;
import com.kcylog.system.domain.SysClienteleJtinfo;

/**
 * 客户家庭信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public interface SysClienteleJtinfoMapper 
{
    /**
     * 查询客户家庭信息
     * 
     * @param id 客户家庭信息主键
     * @return 客户家庭信息
     */
    public SysClienteleJtinfo selectSysClienteleJtinfoById(Long id);

    /**
     * 查询客户家庭信息列表
     * 
     * @param sysClienteleJtinfo 客户家庭信息
     * @return 客户家庭信息集合
     */
    public List<SysClienteleJtinfo> selectSysClienteleJtinfoList(SysClienteleJtinfo sysClienteleJtinfo);

    /**
     * 新增客户家庭信息
     * 
     * @param sysClienteleJtinfo 客户家庭信息
     * @return 结果
     */
    public int insertSysClienteleJtinfo(SysClienteleJtinfo sysClienteleJtinfo);

    /**
     * 修改客户家庭信息
     * 
     * @param sysClienteleJtinfo 客户家庭信息
     * @return 结果
     */
    public int updateSysClienteleJtinfo(SysClienteleJtinfo sysClienteleJtinfo);

    /**
     * 删除客户家庭信息
     * 
     * @param id 客户家庭信息主键
     * @return 结果
     */
    public int deleteSysClienteleJtinfoById(Long id);

    /**
     * 批量删除客户家庭信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysClienteleJtinfoByIds(Long[] ids);
}
