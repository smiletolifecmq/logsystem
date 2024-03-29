package com.kcylog.system.mapper;

import com.kcylog.common.annotation.DataSource;
import com.kcylog.common.enums.DataSourceType;
import com.kcylog.system.domain.ViewFqProjectArchiveTransferTrack;

import java.util.List;

/**
 * 移交流程Mapper接口
 * 
 * @author ruoyi
 * @date 2024-03-29
 */
@DataSource(value = DataSourceType.SLAVE)
public interface ViewFqProjectArchiveTransferTrackMapper 
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
     * 删除移交流程
     * 
     * @param projectId 移交流程主键
     * @return 结果
     */
    public int deleteViewFqProjectArchiveTransferTrackByProjectId(Long projectId);

    /**
     * 批量删除移交流程
     * 
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViewFqProjectArchiveTransferTrackByProjectIds(Long[] projectIds);
}
