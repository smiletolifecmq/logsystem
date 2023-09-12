package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysProjectRelation;
import com.kcylog.system.mapper.SysProjectRelationMapper;
import com.kcylog.system.service.ISysProjectRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目关联审核Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-08
 */
@Service
public class SysProjectRelationServiceImpl implements ISysProjectRelationService 
{
    @Autowired
    private SysProjectRelationMapper sysProjectRelationMapper;

    /**
     * 查询项目关联审核
     * 
     * @param relationId 项目关联审核主键
     * @return 项目关联审核
     */
    @Override
    public SysProjectRelation selectSysProjectRelationByRelationId(String relationId)
    {
        return sysProjectRelationMapper.selectSysProjectRelationByRelationId(relationId);
    }

    /**
     * 查询项目关联审核列表
     * 
     * @param sysProjectRelation 项目关联审核
     * @return 项目关联审核
     */
    @Override
    public List<SysProjectRelation> selectSysProjectRelationList(SysProjectRelation sysProjectRelation)
    {
        return sysProjectRelationMapper.selectSysProjectRelationList(sysProjectRelation);
    }

    /**
     * 新增项目关联审核
     * 
     * @param sysProjectRelation 项目关联审核
     * @return 结果
     */
    @Override
    public int insertSysProjectRelation(SysProjectRelation sysProjectRelation)
    {
        return sysProjectRelationMapper.insertSysProjectRelation(sysProjectRelation);
    }

    /**
     * 修改项目关联审核
     * 
     * @param sysProjectRelation 项目关联审核
     * @return 结果
     */
    @Override
    public int updateSysProjectRelation(SysProjectRelation sysProjectRelation)
    {
        return sysProjectRelationMapper.updateSysProjectRelation(sysProjectRelation);
    }

    /**
     * 批量删除项目关联审核
     * 
     * @param relationIds 需要删除的项目关联审核主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectRelationByRelationIds(String[] relationIds)
    {
        return sysProjectRelationMapper.deleteSysProjectRelationByRelationIds(relationIds);
    }

    /**
     * 删除项目关联审核信息
     * 
     * @param relationId 项目关联审核主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectRelationByRelationId(String relationId)
    {
        return sysProjectRelationMapper.deleteSysProjectRelationByRelationId(relationId);
    }

    @Override
    public int deleteByReviewId(SysProjectRelation sysProjectRelation)
    {
        return sysProjectRelationMapper.deleteByReviewId(sysProjectRelation);
    }
}
