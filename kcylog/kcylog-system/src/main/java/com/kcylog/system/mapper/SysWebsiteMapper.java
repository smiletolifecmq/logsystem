package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysWebsite;

import java.util.List;

/**
 * 网站整合Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
public interface SysWebsiteMapper 
{
    /**
     * 查询网站整合
     * 
     * @param webId 网站整合主键
     * @return 网站整合
     */
    public SysWebsite selectSysWebsiteByWebId(String webId);

    /**
     * 查询网站整合列表
     * 
     * @param sysWebsite 网站整合
     * @return 网站整合集合
     */
    public List<SysWebsite> selectSysWebsiteList(SysWebsite sysWebsite);

    /**
     * 新增网站整合
     * 
     * @param sysWebsite 网站整合
     * @return 结果
     */
    public int insertSysWebsite(SysWebsite sysWebsite);

    /**
     * 修改网站整合
     * 
     * @param sysWebsite 网站整合
     * @return 结果
     */
    public int updateSysWebsite(SysWebsite sysWebsite);

    /**
     * 删除网站整合
     * 
     * @param webId 网站整合主键
     * @return 结果
     */
    public int deleteSysWebsiteByWebId(String webId);

    /**
     * 批量删除网站整合
     * 
     * @param webIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysWebsiteByWebIds(String[] webIds);

    /**
     * 查询网站整合列表（根据点击量返回）
     *
     * @param sysWebsite 网站整合
     * @return 网站整合集合
     */
    public List<SysWebsite> selectSysWebListByNum(SysWebsite sysWebsite);

    public int updateSysWebsiteNum(SysWebsite sysWebsite);
}
