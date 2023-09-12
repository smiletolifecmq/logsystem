package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysProjectRelation;

import java.util.List;

/**
 * 项目关联审核Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-08
 */
public interface SysProjectRelationMapper 
{
    /**
     * 查询项目关联审核
     * 
     * @param relationId 项目关联审核主键
     * @return 项目关联审核
     */
    public SysProjectRelation selectSysProjectRelationByRelationId(String relationId);

    /**
     * 查询项目关联审核列表
     * 
     * @param sysProjectRelation 项目关联审核
     * @return 项目关联审核集合
     */
    public List<SysProjectRelation> selectSysProjectRelationList(SysProjectRelation sysProjectRelation);

    /**
     * 新增项目关联审核
     * 
     * @param sysProjectRelation 项目关联审核
     * @return 结果
     */
    public int insertSysProjectRelation(SysProjectRelation sysProjectRelation);

    /**
     * 修改项目关联审核
     * 
     * @param sysProjectRelation 项目关联审核
     * @return 结果
     */
    public int updateSysProjectRelation(SysProjectRelation sysProjectRelation);

    /**
     * 删除项目关联审核
     * 
     * @param relationId 项目关联审核主键
     * @return 结果
     */
    public int deleteSysProjectRelationByRelationId(String relationId);

    /**
     * 批量删除项目关联审核
     * 
     * @param relationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysProjectRelationByRelationIds(String[] relationIds);

    public int deleteByReviewId(SysProjectRelation sysProjectRelation);

    public List<SysProjectRelation> selectProjectRelationByReviewType(Integer reviewType);
}
