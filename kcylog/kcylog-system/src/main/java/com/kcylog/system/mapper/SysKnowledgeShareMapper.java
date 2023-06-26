package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysKnowledgeShare;
import com.kcylog.system.param.KnowledgeShareParams;

import java.util.List;

/**
 * 知识分享Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-21
 */
public interface SysKnowledgeShareMapper 
{
    /**
     * 查询知识分享
     * 
     * @param knowledgeId 知识分享主键
     * @return 知识分享
     */
    public SysKnowledgeShare selectSysKnowledgeShareByKnowledgeId(Long knowledgeId);

    /**
     * 查询知识分享列表
     * 
     * @param sysKnowledgeShare 知识分享
     * @return 知识分享集合
     */
    public List<SysKnowledgeShare> selectSysKnowledgeShareList(SysKnowledgeShare sysKnowledgeShare);

    /**
     * 新增知识分享
     * 
     * @param sysKnowledgeShare 知识分享
     * @return 结果
     */
    public int insertSysKnowledgeShare(SysKnowledgeShare sysKnowledgeShare);

    /**
     * 修改知识分享
     * 
     * @param sysKnowledgeShare 知识分享
     * @return 结果
     */
    public int updateSysKnowledgeShare(SysKnowledgeShare sysKnowledgeShare);

    /**
     * 删除知识分享
     * 
     * @param knowledgeId 知识分享主键
     * @return 结果
     */
    public int deleteSysKnowledgeShareByKnowledgeId(Long knowledgeId);

    /**
     * 批量删除知识分享
     * 
     * @param knowledgeShare 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysKnowledgeShareByKnowledgeIds(KnowledgeShareParams knowledgeShare);
}
