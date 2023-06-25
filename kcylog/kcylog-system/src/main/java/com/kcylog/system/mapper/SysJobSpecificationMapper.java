package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysJobSpecification;

import java.util.List;

/**
 * 作业规范Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-25
 */
public interface SysJobSpecificationMapper 
{
    /**
     * 查询作业规范
     * 
     * @param jobId 作业规范主键
     * @return 作业规范
     */
    public SysJobSpecification selectSysJobSpecificationByJobId(Long jobId);

    /**
     * 查询作业规范列表
     * 
     * @param sysJobSpecification 作业规范
     * @return 作业规范集合
     */
    public List<SysJobSpecification> selectSysJobSpecificationList(SysJobSpecification sysJobSpecification);

    /**
     * 新增作业规范
     * 
     * @param sysJobSpecification 作业规范
     * @return 结果
     */
    public int insertSysJobSpecification(SysJobSpecification sysJobSpecification);

    /**
     * 修改作业规范
     * 
     * @param sysJobSpecification 作业规范
     * @return 结果
     */
    public int updateSysJobSpecification(SysJobSpecification sysJobSpecification);

    /**
     * 删除作业规范
     * 
     * @param jobId 作业规范主键
     * @return 结果
     */
    public int deleteSysJobSpecificationByJobId(Long jobId);

    /**
     * 批量删除作业规范
     * 
     * @param jobIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysJobSpecificationByJobIds(Long[] jobIds);
}
