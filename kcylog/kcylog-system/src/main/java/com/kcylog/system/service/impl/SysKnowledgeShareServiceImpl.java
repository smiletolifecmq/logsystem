package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.SysKnowledgeShare;
import com.kcylog.system.mapper.SysKnowledgeShareMapper;
import com.kcylog.system.param.KnowledgeShareParams;
import com.kcylog.system.service.ISysKnowledgeShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 知识分享Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-21
 */
@Service
public class SysKnowledgeShareServiceImpl implements ISysKnowledgeShareService 
{
    @Autowired
    private SysKnowledgeShareMapper sysKnowledgeShareMapper;

    /**
     * 查询知识分享
     * 
     * @param knowledgeId 知识分享主键
     * @return 知识分享
     */
    @Override
    public SysKnowledgeShare selectSysKnowledgeShareByKnowledgeId(Long knowledgeId)
    {
        return sysKnowledgeShareMapper.selectSysKnowledgeShareByKnowledgeId(knowledgeId);
    }

    /**
     * 查询知识分享列表
     * 
     * @param sysKnowledgeShare 知识分享
     * @return 知识分享
     */
    @Override
    public List<SysKnowledgeShare> selectSysKnowledgeShareList(SysKnowledgeShare sysKnowledgeShare)
    {
        return sysKnowledgeShareMapper.selectSysKnowledgeShareList(sysKnowledgeShare);
    }

    /**
     * 新增知识分享
     * 
     * @param sysKnowledgeShare 知识分享
     * @return 结果
     */
    @Override
    public int insertSysKnowledgeShare(SysKnowledgeShare sysKnowledgeShare)
    {
        sysKnowledgeShare.setCreateTime(DateUtils.getNowDate());
        return sysKnowledgeShareMapper.insertSysKnowledgeShare(sysKnowledgeShare);
    }

    /**
     * 修改知识分享
     * 
     * @param sysKnowledgeShare 知识分享
     * @return 结果
     */
    @Override
    public int updateSysKnowledgeShare(SysKnowledgeShare sysKnowledgeShare)
    {
        sysKnowledgeShare.setUpdateTime(DateUtils.getNowDate());
        return sysKnowledgeShareMapper.updateSysKnowledgeShare(sysKnowledgeShare);
    }

    /**
     * 批量删除知识分享
     * 
     * @param knowledgeShare 需要删除的知识分享主键
     * @return 结果
     */
    @Override
    public int deleteSysKnowledgeShareByKnowledgeIds(KnowledgeShareParams knowledgeShare)
    {
        return sysKnowledgeShareMapper.deleteSysKnowledgeShareByKnowledgeIds(knowledgeShare);
    }

    /**
     * 删除知识分享信息
     * 
     * @param knowledgeId 知识分享主键
     * @return 结果
     */
    @Override
    public int deleteSysKnowledgeShareByKnowledgeId(Long knowledgeId)
    {
        return sysKnowledgeShareMapper.deleteSysKnowledgeShareByKnowledgeId(knowledgeId);
    }
}
