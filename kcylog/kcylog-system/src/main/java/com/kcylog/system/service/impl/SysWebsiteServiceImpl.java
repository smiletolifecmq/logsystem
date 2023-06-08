package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.SysWebsite;
import com.kcylog.system.mapper.SysWebsiteMapper;
import com.kcylog.system.service.ISysWebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网站整合Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
@Service
public class SysWebsiteServiceImpl implements ISysWebsiteService 
{
    @Autowired
    private SysWebsiteMapper sysWebsiteMapper;

    /**
     * 查询网站整合
     * 
     * @param webId 网站整合主键
     * @return 网站整合
     */
    @Override
    public SysWebsite selectSysWebsiteByWebId(String webId)
    {
        return sysWebsiteMapper.selectSysWebsiteByWebId(webId);
    }

    /**
     * 查询网站整合列表
     * 
     * @param sysWebsite 网站整合
     * @return 网站整合
     */
    @Override
    public List<SysWebsite> selectSysWebsiteList(SysWebsite sysWebsite)
    {
        return sysWebsiteMapper.selectSysWebsiteList(sysWebsite);
    }

    /**
     * 新增网站整合
     * 
     * @param sysWebsite 网站整合
     * @return 结果
     */
    @Override
    public int insertSysWebsite(SysWebsite sysWebsite)
    {
        sysWebsite.setCreateTime(DateUtils.getNowDate());
        return sysWebsiteMapper.insertSysWebsite(sysWebsite);
    }

    /**
     * 修改网站整合
     * 
     * @param sysWebsite 网站整合
     * @return 结果
     */
    @Override
    public int updateSysWebsite(SysWebsite sysWebsite)
    {
        sysWebsite.setUpdateTime(DateUtils.getNowDate());
        return sysWebsiteMapper.updateSysWebsite(sysWebsite);
    }

    /**
     * 批量删除网站整合
     * 
     * @param webIds 需要删除的网站整合主键
     * @return 结果
     */
    @Override
    public int deleteSysWebsiteByWebIds(String[] webIds)
    {
        return sysWebsiteMapper.deleteSysWebsiteByWebIds(webIds);
    }

    /**
     * 删除网站整合信息
     * 
     * @param webId 网站整合主键
     * @return 结果
     */
    @Override
    public int deleteSysWebsiteByWebId(String webId)
    {
        return sysWebsiteMapper.deleteSysWebsiteByWebId(webId);
    }


    /**
     * 查询网站整合列表（根据点击量返回）
     *
     * @param sysWebsite 网站整合
     * @return 网站整合
     */
    @Override
    public List<SysWebsite> selectSysWebListByNum(SysWebsite sysWebsite)
    {
        return sysWebsiteMapper.selectSysWebListByNum(sysWebsite);
    }

    @Override
    public int updateSysWebsiteNum(SysWebsite sysWebsite)
    {
        sysWebsite.setUpdateTime(DateUtils.getNowDate());
        return sysWebsiteMapper.updateSysWebsiteNum(sysWebsite);
    }
}
