package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.FqProjectProcessMapper;
import com.kcylog.system.domain.FqProjectProcess;
import com.kcylog.system.service.IFqProjectProcessService;

/**
 * 新系统项目流程同步Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-11
 */
@Service
public class FqProjectProcessServiceImpl implements IFqProjectProcessService 
{
    @Autowired
    private FqProjectProcessMapper fqProjectProcessMapper;

    /**
     * 查询新系统项目流程同步
     * 
     * @param id 新系统项目流程同步主键
     * @return 新系统项目流程同步
     */
    @Override
    public FqProjectProcess selectFqProjectProcessById(Long id)
    {
        return fqProjectProcessMapper.selectFqProjectProcessById(id);
    }

    /**
     * 查询新系统项目流程同步列表
     * 
     * @param fqProjectProcess 新系统项目流程同步
     * @return 新系统项目流程同步
     */
    @Override
    public List<FqProjectProcess> selectFqProjectProcessList(FqProjectProcess fqProjectProcess)
    {
        return fqProjectProcessMapper.selectFqProjectProcessList(fqProjectProcess);
    }

    /**
     * 新增新系统项目流程同步
     * 
     * @param fqProjectProcess 新系统项目流程同步
     * @return 结果
     */
    @Override
    public int insertFqProjectProcess(FqProjectProcess fqProjectProcess)
    {
        return fqProjectProcessMapper.insertFqProjectProcess(fqProjectProcess);
    }

    /**
     * 修改新系统项目流程同步
     * 
     * @param fqProjectProcess 新系统项目流程同步
     * @return 结果
     */
    @Override
    public int updateFqProjectProcess(FqProjectProcess fqProjectProcess)
    {
        return fqProjectProcessMapper.updateFqProjectProcess(fqProjectProcess);
    }

    /**
     * 批量删除新系统项目流程同步
     * 
     * @param ids 需要删除的新系统项目流程同步主键
     * @return 结果
     */
    @Override
    public int deleteFqProjectProcessByIds(Long[] ids)
    {
        return fqProjectProcessMapper.deleteFqProjectProcessByIds(ids);
    }

    /**
     * 删除新系统项目流程同步信息
     * 
     * @param id 新系统项目流程同步主键
     * @return 结果
     */
    @Override
    public int deleteFqProjectProcessById(Long id)
    {
        return fqProjectProcessMapper.deleteFqProjectProcessById(id);
    }
}
