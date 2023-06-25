package com.kcylog.system.service.impl;

import java.util.List;
import com.kcylog.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysJobSpecificationMapper;
import com.kcylog.system.domain.SysJobSpecification;
import com.kcylog.system.service.ISysJobSpecificationService;

/**
 * 作业规范Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-25
 */
@Service
public class SysJobSpecificationServiceImpl implements ISysJobSpecificationService 
{
    @Autowired
    private SysJobSpecificationMapper sysJobSpecificationMapper;

    /**
     * 查询作业规范
     * 
     * @param jobId 作业规范主键
     * @return 作业规范
     */
    @Override
    public SysJobSpecification selectSysJobSpecificationByJobId(Long jobId)
    {
        return sysJobSpecificationMapper.selectSysJobSpecificationByJobId(jobId);
    }

    /**
     * 查询作业规范列表
     * 
     * @param sysJobSpecification 作业规范
     * @return 作业规范
     */
    @Override
    public List<SysJobSpecification> selectSysJobSpecificationList(SysJobSpecification sysJobSpecification)
    {
        return sysJobSpecificationMapper.selectSysJobSpecificationList(sysJobSpecification);
    }

    /**
     * 新增作业规范
     * 
     * @param sysJobSpecification 作业规范
     * @return 结果
     */
    @Override
    public int insertSysJobSpecification(SysJobSpecification sysJobSpecification)
    {
        sysJobSpecification.setCreateTime(DateUtils.getNowDate());
        return sysJobSpecificationMapper.insertSysJobSpecification(sysJobSpecification);
    }

    /**
     * 修改作业规范
     * 
     * @param sysJobSpecification 作业规范
     * @return 结果
     */
    @Override
    public int updateSysJobSpecification(SysJobSpecification sysJobSpecification)
    {
        sysJobSpecification.setUpdateTime(DateUtils.getNowDate());
        return sysJobSpecificationMapper.updateSysJobSpecification(sysJobSpecification);
    }

    /**
     * 批量删除作业规范
     * 
     * @param jobIds 需要删除的作业规范主键
     * @return 结果
     */
    @Override
    public int deleteSysJobSpecificationByJobIds(Long[] jobIds)
    {
        return sysJobSpecificationMapper.deleteSysJobSpecificationByJobIds(jobIds);
    }

    /**
     * 删除作业规范信息
     * 
     * @param jobId 作业规范主键
     * @return 结果
     */
    @Override
    public int deleteSysJobSpecificationByJobId(Long jobId)
    {
        return sysJobSpecificationMapper.deleteSysJobSpecificationByJobId(jobId);
    }
}
