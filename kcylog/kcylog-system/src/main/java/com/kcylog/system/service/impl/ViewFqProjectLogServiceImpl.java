package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.ViewFqProjectLogMapper;
import com.kcylog.system.domain.ViewFqProjectLog;
import com.kcylog.system.service.IViewFqProjectLogService;

/**
 * 项目管理操作日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-02-19
 */
@Service
public class ViewFqProjectLogServiceImpl implements IViewFqProjectLogService 
{
    @Autowired
    private ViewFqProjectLogMapper viewFqProjectLogMapper;

    /**
     * 查询项目管理操作日志
     * 
     * @param id 项目管理操作日志主键
     * @return 项目管理操作日志
     */
    @Override
    public ViewFqProjectLog selectViewFqProjectLogById(Long id)
    {
        return viewFqProjectLogMapper.selectViewFqProjectLogById(id);
    }

    /**
     * 查询项目管理操作日志列表
     * 
     * @param viewFqProjectLog 项目管理操作日志
     * @return 项目管理操作日志
     */
    @Override
    public List<ViewFqProjectLog> selectViewFqProjectLogList(ViewFqProjectLog viewFqProjectLog)
    {
        return viewFqProjectLogMapper.selectViewFqProjectLogList(viewFqProjectLog);
    }

    /**
     * 新增项目管理操作日志
     * 
     * @param viewFqProjectLog 项目管理操作日志
     * @return 结果
     */
    @Override
    public int insertViewFqProjectLog(ViewFqProjectLog viewFqProjectLog)
    {
        return viewFqProjectLogMapper.insertViewFqProjectLog(viewFqProjectLog);
    }

    /**
     * 修改项目管理操作日志
     * 
     * @param viewFqProjectLog 项目管理操作日志
     * @return 结果
     */
    @Override
    public int updateViewFqProjectLog(ViewFqProjectLog viewFqProjectLog)
    {
        return viewFqProjectLogMapper.updateViewFqProjectLog(viewFqProjectLog);
    }

    /**
     * 批量删除项目管理操作日志
     * 
     * @param ids 需要删除的项目管理操作日志主键
     * @return 结果
     */
    @Override
    public int deleteViewFqProjectLogByIds(Long[] ids)
    {
        return viewFqProjectLogMapper.deleteViewFqProjectLogByIds(ids);
    }

    /**
     * 删除项目管理操作日志信息
     * 
     * @param id 项目管理操作日志主键
     * @return 结果
     */
    @Override
    public int deleteViewFqProjectLogById(Long id)
    {
        return viewFqProjectLogMapper.deleteViewFqProjectLogById(id);
    }
}
