package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.ProjectChargeInfo;
import com.kcylog.system.mapper.ProjectChargeInfoMapper;
import com.kcylog.system.service.IProjectChargeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分包明细表Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-12
 */
@Service
public class ProjectChargeInfoServiceImpl implements IProjectChargeInfoService 
{
    @Autowired
    private ProjectChargeInfoMapper projectChargeInfoMapper;

    /**
     * 查询分包明细表
     * 
     * @param id 分包明细表主键
     * @return 分包明细表
     */
    @Override
    public ProjectChargeInfo selectProjectChargeInfoById(Long id)
    {
        return projectChargeInfoMapper.selectProjectChargeInfoById(id);
    }

    /**
     * 查询分包明细表列表
     * 
     * @param projectChargeInfo 分包明细表
     * @return 分包明细表
     */
    @Override
    public List<ProjectChargeInfo> selectProjectChargeInfoList(ProjectChargeInfo projectChargeInfo)
    {
        return projectChargeInfoMapper.selectProjectChargeInfoList(projectChargeInfo);
    }

    /**
     * 新增分包明细表
     * 
     * @param projectChargeInfo 分包明细表
     * @return 结果
     */
    @Override
    public int insertProjectChargeInfo(ProjectChargeInfo projectChargeInfo)
    {
        projectChargeInfo.setCreateTime(DateUtils.getNowDate());
        return projectChargeInfoMapper.insertProjectChargeInfo(projectChargeInfo);
    }

    /**
     * 修改分包明细表
     * 
     * @param projectChargeInfo 分包明细表
     * @return 结果
     */
    @Override
    public int updateProjectChargeInfo(ProjectChargeInfo projectChargeInfo)
    {
        return projectChargeInfoMapper.updateProjectChargeInfo(projectChargeInfo);
    }

    /**
     * 批量删除分包明细表
     * 
     * @param ids 需要删除的分包明细表主键
     * @return 结果
     */
    @Override
    public int deleteProjectChargeInfoByIds(Long[] ids)
    {
        return projectChargeInfoMapper.deleteProjectChargeInfoByIds(ids);
    }

    /**
     * 删除分包明细表信息
     * 
     * @param id 分包明细表主键
     * @return 结果
     */
    @Override
    public int deleteProjectChargeInfoById(String id)
    {
        return projectChargeInfoMapper.deleteProjectChargeInfoById(id);
    }
}
