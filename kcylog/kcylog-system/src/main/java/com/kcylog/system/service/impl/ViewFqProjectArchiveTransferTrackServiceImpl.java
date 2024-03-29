package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.ViewFqProjectArchiveTransferTrackMapper;
import com.kcylog.system.domain.ViewFqProjectArchiveTransferTrack;
import com.kcylog.system.service.IViewFqProjectArchiveTransferTrackService;

/**
 * 移交流程Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-03-29
 */
@Service
public class ViewFqProjectArchiveTransferTrackServiceImpl implements IViewFqProjectArchiveTransferTrackService 
{
    @Autowired
    private ViewFqProjectArchiveTransferTrackMapper viewFqProjectArchiveTransferTrackMapper;

    /**
     * 查询移交流程
     * 
     * @param projectId 移交流程主键
     * @return 移交流程
     */
    @Override
    public ViewFqProjectArchiveTransferTrack selectViewFqProjectArchiveTransferTrackByProjectId(Long projectId)
    {
        return viewFqProjectArchiveTransferTrackMapper.selectViewFqProjectArchiveTransferTrackByProjectId(projectId);
    }

    /**
     * 查询移交流程列表
     * 
     * @param viewFqProjectArchiveTransferTrack 移交流程
     * @return 移交流程
     */
    @Override
    public List<ViewFqProjectArchiveTransferTrack> selectViewFqProjectArchiveTransferTrackList(ViewFqProjectArchiveTransferTrack viewFqProjectArchiveTransferTrack)
    {
        return viewFqProjectArchiveTransferTrackMapper.selectViewFqProjectArchiveTransferTrackList(viewFqProjectArchiveTransferTrack);
    }

    /**
     * 新增移交流程
     * 
     * @param viewFqProjectArchiveTransferTrack 移交流程
     * @return 结果
     */
    @Override
    public int insertViewFqProjectArchiveTransferTrack(ViewFqProjectArchiveTransferTrack viewFqProjectArchiveTransferTrack)
    {
        return viewFqProjectArchiveTransferTrackMapper.insertViewFqProjectArchiveTransferTrack(viewFqProjectArchiveTransferTrack);
    }

    /**
     * 修改移交流程
     * 
     * @param viewFqProjectArchiveTransferTrack 移交流程
     * @return 结果
     */
    @Override
    public int updateViewFqProjectArchiveTransferTrack(ViewFqProjectArchiveTransferTrack viewFqProjectArchiveTransferTrack)
    {
        return viewFqProjectArchiveTransferTrackMapper.updateViewFqProjectArchiveTransferTrack(viewFqProjectArchiveTransferTrack);
    }

    /**
     * 批量删除移交流程
     * 
     * @param projectIds 需要删除的移交流程主键
     * @return 结果
     */
    @Override
    public int deleteViewFqProjectArchiveTransferTrackByProjectIds(Long[] projectIds)
    {
        return viewFqProjectArchiveTransferTrackMapper.deleteViewFqProjectArchiveTransferTrackByProjectIds(projectIds);
    }

    /**
     * 删除移交流程信息
     * 
     * @param projectId 移交流程主键
     * @return 结果
     */
    @Override
    public int deleteViewFqProjectArchiveTransferTrackByProjectId(Long projectId)
    {
        return viewFqProjectArchiveTransferTrackMapper.deleteViewFqProjectArchiveTransferTrackByProjectId(projectId);
    }
}
