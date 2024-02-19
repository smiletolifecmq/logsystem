package com.kcylog.system.mapper;

import com.kcylog.common.annotation.DataSource;
import com.kcylog.common.enums.DataSourceType;
import com.kcylog.system.domain.ViewFqProject;

import java.util.List;

/**
 * 项目Mapper接口
 * 
 * @author ruoyi
 * @date 2024-02-19
 */
@DataSource(value = DataSourceType.SLAVE)
public interface ViewFqProjectMapper 
{
    /**
     * 查询项目
     * 
     * @return 项目
     */
    public ViewFqProject selectViewFqProjectByProjectCode(String projectCode);

    /**
     * 查询项目列表
     * 
     * @param viewFqProject 项目
     * @return 项目集合
     */
    public List<ViewFqProject> selectViewFqProjectList(ViewFqProject viewFqProject);

    /**
     * 新增项目
     * 
     * @param viewFqProject 项目
     * @return 结果
     */
    public int insertViewFqProject(ViewFqProject viewFqProject);

    /**
     * 修改项目
     * 
     * @param viewFqProject 项目
     * @return 结果
     */
    public int updateViewFqProject(ViewFqProject viewFqProject);

    /**
     * 删除项目
     * 
     * @param projectId 项目主键
     * @return 结果
     */
    public int deleteViewFqProjectByProjectId(String projectId);

    /**
     * 批量删除项目
     * 
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViewFqProjectByProjectIds(String[] projectIds);
}
