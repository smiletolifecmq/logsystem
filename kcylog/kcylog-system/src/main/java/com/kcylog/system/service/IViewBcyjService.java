package com.kcylog.system.service;

import java.util.List;
import com.kcylog.system.domain.ViewBcyj;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2026-01-19
 */
public interface IViewBcyjService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param projectCode 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ViewBcyj selectViewBcyjByProjectCode(String projectCode);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param viewBcyj 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ViewBcyj> selectViewBcyjList(ViewBcyj viewBcyj);

    /**
     * 新增【请填写功能名称】
     * 
     * @param viewBcyj 【请填写功能名称】
     * @return 结果
     */
    public int insertViewBcyj(ViewBcyj viewBcyj);

    /**
     * 修改【请填写功能名称】
     * 
     * @param viewBcyj 【请填写功能名称】
     * @return 结果
     */
    public int updateViewBcyj(ViewBcyj viewBcyj);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param projectCodes 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteViewBcyjByProjectCodes(String[] projectCodes);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param projectCode 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteViewBcyjByProjectCode(String projectCode);
}
