package com.kcylog.system.mapper;

import com.kcylog.common.annotation.DataSource;
import com.kcylog.common.enums.DataSourceType;
import com.kcylog.system.domain.ViewBcyj;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2026-01-19
 */
@DataSource(value = DataSourceType.SLAVEBC)
public interface ViewBcyjMapper 
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
     * 删除【请填写功能名称】
     * 
     * @param projectCode 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteViewBcyjByProjectCode(String projectCode);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param projectCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViewBcyjByProjectCodes(String[] projectCodes);
}
