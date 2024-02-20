package com.kcylog.system.mapper;

import com.kcylog.system.domain.ViewFqProjectLog;

import java.util.List;

/**
 * 项目管理操作日志Mapper接口
 * 
 * @author ruoyi
 * @date 2024-02-19
 */
public interface ViewFqProjectLogMapper 
{
    /**
     * 查询项目管理操作日志
     * 
     * @param id 项目管理操作日志主键
     * @return 项目管理操作日志
     */
    public ViewFqProjectLog selectViewFqProjectLogById(Long id);

    /**
     * 查询项目管理操作日志列表
     * 
     * @param viewFqProjectLog 项目管理操作日志
     * @return 项目管理操作日志集合
     */
    public List<ViewFqProjectLog> selectViewFqProjectLogList(ViewFqProjectLog viewFqProjectLog);

    /**
     * 新增项目管理操作日志
     * 
     * @param viewFqProjectLog 项目管理操作日志
     * @return 结果
     */
    public int insertViewFqProjectLog(ViewFqProjectLog viewFqProjectLog);

    /**
     * 修改项目管理操作日志
     * 
     * @param viewFqProjectLog 项目管理操作日志
     * @return 结果
     */
    public int updateViewFqProjectLog(ViewFqProjectLog viewFqProjectLog);

    /**
     * 删除项目管理操作日志
     * 
     * @param id 项目管理操作日志主键
     * @return 结果
     */
    public int deleteViewFqProjectLogById(Long id);

    /**
     * 批量删除项目管理操作日志
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViewFqProjectLogByIds(Long[] ids);
}
