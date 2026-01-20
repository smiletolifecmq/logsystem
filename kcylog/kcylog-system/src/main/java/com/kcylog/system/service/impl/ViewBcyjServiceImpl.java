package com.kcylog.system.service.impl;

import com.kcylog.system.domain.ViewBcyj;
import com.kcylog.system.mapper.ViewBcyjMapper;
import com.kcylog.system.service.IViewBcyjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-19
 */
@Service
public class ViewBcyjServiceImpl implements IViewBcyjService 
{
    @Autowired
    private ViewBcyjMapper viewBcyjMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param projectCode 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ViewBcyj selectViewBcyjByProjectCode(String projectCode)
    {
        return viewBcyjMapper.selectViewBcyjByProjectCode(projectCode);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param viewBcyj 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ViewBcyj> selectViewBcyjList(ViewBcyj viewBcyj)
    {
        return viewBcyjMapper.selectViewBcyjList(viewBcyj);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param viewBcyj 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertViewBcyj(ViewBcyj viewBcyj)
    {
        return viewBcyjMapper.insertViewBcyj(viewBcyj);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param viewBcyj 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateViewBcyj(ViewBcyj viewBcyj)
    {
        return viewBcyjMapper.updateViewBcyj(viewBcyj);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param projectCodes 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteViewBcyjByProjectCodes(String[] projectCodes)
    {
        return viewBcyjMapper.deleteViewBcyjByProjectCodes(projectCodes);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param projectCode 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteViewBcyjByProjectCode(String projectCode)
    {
        return viewBcyjMapper.deleteViewBcyjByProjectCode(projectCode);
    }
}
