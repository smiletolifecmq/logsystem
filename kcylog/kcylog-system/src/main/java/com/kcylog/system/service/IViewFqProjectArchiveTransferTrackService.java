package com.kcylog.system.service;

import com.kcylog.system.domain.ViewFqProjectArchiveTransferTrack;

import java.util.List;

/**
 * 移交流程Service接口
 * 
 * @author ruoyi
 * @date 2024-03-29
 */
public interface IViewFqProjectArchiveTransferTrackService 
{
    /**
     * 查询移交流程
     * 
     * @param projectId 移交流程主键
     * @return 移交流程
     */
    public ViewFqProjectArchiveTransferTrack selectViewFqProjectArchiveTransferTrackByProjectId(Long projectId);

    /**
     * 查询移交流程列表
     * 
     * @param viewFqProjectArchiveTransferTrack 移交流程
     * @return 移交流程集合
     */
    public List<ViewFqProjectArchiveTransferTrack> selectViewFqProjectArchiveTransferTrackList(ViewFqProjectArchiveTransferTrack viewFqProjectArchiveTransferTrack);

    /**
     * 新增移交流程
     * 
     * @param viewFqProjectArchiveTransferTrack 移交流程
     * @return 结果
     */
    public int insertViewFqProjectArchiveTransferTrack(ViewFqProjectArchiveTransferTrack viewFqProjectArchiveTransferTrack);

    /**
     * 修改移交流程
     * 
     * @param viewFqProjectArchiveTransferTrack 移交流程
     * @return 结果
     */
    public int updateViewFqProjectArchiveTransferTrack(ViewFqProjectArchiveTransferTrack viewFqProjectArchiveTransferTrack);

    /**
     * 批量删除移交流程
     * 
     * @param projectIds 需要删除的移交流程主键集合
     * @return 结果
     */
    public int deleteViewFqProjectArchiveTransferTrackByProjectIds(Long[] projectIds);

    /**
     * 删除移交流程信息
     * 
     * @param projectId 移交流程主键
     * @return 结果
     */
    public int deleteViewFqProjectArchiveTransferTrackByProjectId(Long projectId);

    public List<ViewFqProjectArchiveTransferTrack> selectViewFqProjectArchiveTransferTrackByProjectCode(List<String> projectCodes);

    public List<ViewFqProjectArchiveTransferTrack> selectViewFqProjectArchiveTransferTrackListByProjectId(Long projectId);
}
