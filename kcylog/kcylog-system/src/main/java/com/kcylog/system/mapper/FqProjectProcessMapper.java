package com.kcylog.system.mapper;

import com.kcylog.system.domain.FqProjectProcess;
import com.kcylog.system.domain.SysProject;

import java.util.List;

/**
 * 新系统项目流程同步Mapper接口
 * 
 * @author ruoyi
 * @date 2024-04-11
 */
public interface FqProjectProcessMapper 
{
    /**
     * 查询新系统项目流程同步
     * 
     * @param id 新系统项目流程同步主键
     * @return 新系统项目流程同步
     */
    public FqProjectProcess selectFqProjectProcessById(Long id);

    /**
     * 查询新系统项目流程同步列表
     * 
     * @return 新系统项目流程同步集合
     */
    public List<FqProjectProcess> selectFqProjectProcessList(SysProject sysProject);

    /**
     * 新增新系统项目流程同步
     * 
     * @param fqProjectProcess 新系统项目流程同步
     * @return 结果
     */
    public int insertFqProjectProcess(FqProjectProcess fqProjectProcess);

    /**
     * 修改新系统项目流程同步
     * 
     * @param fqProjectProcess 新系统项目流程同步
     * @return 结果
     */
    public int updateFqProjectProcess(FqProjectProcess fqProjectProcess);

    /**
     * 删除新系统项目流程同步
     * 
     * @param id 新系统项目流程同步主键
     * @return 结果
     */
    public int deleteFqProjectProcessById(Long id);

    /**
     * 批量删除新系统项目流程同步
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFqProjectProcessByIds(Long[] ids);
}
