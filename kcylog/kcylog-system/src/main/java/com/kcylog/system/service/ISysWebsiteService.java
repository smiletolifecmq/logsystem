package com.kcylog.system.service;

import com.kcylog.system.domain.SysWebsite;

import java.util.List;

/**
 * 网站整合Service接口
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
public interface ISysWebsiteService 
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
     * 批量删除网站整合
     * 
     * @param webIds 需要删除的网站整合主键集合
     * @return 结果
     */
    public int deleteSysWebsiteByWebIds(String[] webIds);

    /**
     * 删除网站整合信息
     * 
     * @param webId 网站整合主键
     * @return 结果
     */
    public int deleteSysWebsiteByWebId(String webId);

    /**
     * 查询网站整合列表（根据点击量返回）
     *
     * @param sysWebsite 网站整合
     * @return 网站整合集合
     */
    public List<SysWebsite> selectSysWebListByNum(SysWebsite sysWebsite);

    public int updateSysWebsiteNum(SysWebsite sysWebsite);
}
