package com.kcylog.system.service;

import com.kcylog.system.domain.ProjectChargeInfo;

import java.util.List;

/**
 * 分包明细表Service接口
 * 
 * @author ruoyi
 * @date 2024-05-12
 */
public interface IProjectChargeInfoService 
{
    /**
     * 查询分包明细表
     * 
     * @param id 分包明细表主键
     * @return 分包明细表
     */
    public ProjectChargeInfo selectProjectChargeInfoById(Long id);

    /**
     * 查询分包明细表列表
     * 
     * @param projectChargeInfo 分包明细表
     * @return 分包明细表集合
     */
    public List<ProjectChargeInfo> selectProjectChargeInfoList(ProjectChargeInfo projectChargeInfo);

    /**
     * 新增分包明细表
     * 
     * @param projectChargeInfo 分包明细表
     * @return 结果
     */
    public int insertProjectChargeInfo(ProjectChargeInfo projectChargeInfo);

    /**
     * 修改分包明细表
     * 
     * @param projectChargeInfo 分包明细表
     * @return 结果
     */
    public int updateProjectChargeInfo(ProjectChargeInfo projectChargeInfo);

    /**
     * 批量删除分包明细表
     * 
     * @param ids 需要删除的分包明细表主键集合
     * @return 结果
     */
    public int deleteProjectChargeInfoByIds(Long[] ids);

    /**
     * 删除分包明细表信息
     * 
     * @param id 分包明细表主键
     * @return 结果
     */
    public int deleteProjectChargeInfoById(String id);

    public ProjectChargeInfo selectProjectChargeInfoByCode(String projectCode);
}
