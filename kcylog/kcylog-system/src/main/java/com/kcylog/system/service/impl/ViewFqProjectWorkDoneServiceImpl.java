package com.kcylog.system.service.impl;

import com.kcylog.system.domain.ViewFqProjectWorkDone;
import com.kcylog.system.mapper.ViewFqProjectWorkDoneMapper;
import com.kcylog.system.service.IViewFqProjectWorkDoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作业办结时间Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-03-29
 */
@Service
public class ViewFqProjectWorkDoneServiceImpl implements IViewFqProjectWorkDoneService 
{
    @Autowired
    private ViewFqProjectWorkDoneMapper viewFqProjectWorkDoneMapper;

    /**
     * 查询作业办结时间
     * 
     * @param projectId 作业办结时间主键
     * @return 作业办结时间
     */
    @Override
    public ViewFqProjectWorkDone selectViewFqProjectWorkDoneByProjectId(Long projectId)
    {
        return viewFqProjectWorkDoneMapper.selectViewFqProjectWorkDoneByProjectId(projectId);
    }

    /**
     * 查询作业办结时间列表
     * 
     * @param viewFqProjectWorkDone 作业办结时间
     * @return 作业办结时间
     */
    @Override
    public List<ViewFqProjectWorkDone> selectViewFqProjectWorkDoneList(ViewFqProjectWorkDone viewFqProjectWorkDone)
    {
        return viewFqProjectWorkDoneMapper.selectViewFqProjectWorkDoneList(viewFqProjectWorkDone);
    }

    /**
     * 新增作业办结时间
     * 
     * @param viewFqProjectWorkDone 作业办结时间
     * @return 结果
     */
    @Override
    public int insertViewFqProjectWorkDone(ViewFqProjectWorkDone viewFqProjectWorkDone)
    {
        return viewFqProjectWorkDoneMapper.insertViewFqProjectWorkDone(viewFqProjectWorkDone);
    }

    /**
     * 修改作业办结时间
     * 
     * @param viewFqProjectWorkDone 作业办结时间
     * @return 结果
     */
    @Override
    public int updateViewFqProjectWorkDone(ViewFqProjectWorkDone viewFqProjectWorkDone)
    {
        return viewFqProjectWorkDoneMapper.updateViewFqProjectWorkDone(viewFqProjectWorkDone);
    }

    /**
     * 批量删除作业办结时间
     * 
     * @param projectIds 需要删除的作业办结时间主键
     * @return 结果
     */
    @Override
    public int deleteViewFqProjectWorkDoneByProjectIds(Long[] projectIds)
    {
        return viewFqProjectWorkDoneMapper.deleteViewFqProjectWorkDoneByProjectIds(projectIds);
    }

    /**
     * 删除作业办结时间信息
     * 
     * @param projectId 作业办结时间主键
     * @return 结果
     */
    @Override
    public int deleteViewFqProjectWorkDoneByProjectId(Long projectId)
    {
        return viewFqProjectWorkDoneMapper.deleteViewFqProjectWorkDoneByProjectId(projectId);
    }

    @Override
    public List<ViewFqProjectWorkDone> selectViewFqProjectWorkDoneByProjectCodes(List<String> projectCodes)
    {
        return viewFqProjectWorkDoneMapper.selectViewFqProjectWorkDoneByProjectCodes(projectCodes);
    }
}
