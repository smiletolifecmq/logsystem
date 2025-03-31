package com.kcylog.system.service.impl;

import com.kcylog.system.domain.BcProject;
import com.kcylog.system.mapper.BcProjectMapper;
import com.kcylog.system.service.IBcProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 百川项目地理空间数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-31
 */
@Service
public class BcProjectServiceImpl implements IBcProjectService 
{
    @Autowired
    private BcProjectMapper bcProjectMapper;

    /**
     * 查询百川项目地理空间数据
     * 
     * @param objectid 百川项目地理空间数据主键
     * @return 百川项目地理空间数据
     */
    @Override
    public BcProject selectBcProjectByObjectid(Long objectid)
    {
        return bcProjectMapper.selectBcProjectByObjectid(objectid);
    }

    /**
     * 查询百川项目地理空间数据列表
     * 
     * @param bcProject 百川项目地理空间数据
     * @return 百川项目地理空间数据
     */
    @Override
    public List<BcProject> selectBcProjectList(BcProject bcProject)
    {
        return bcProjectMapper.selectBcProjectList(bcProject);
    }

    /**
     * 新增百川项目地理空间数据
     * 
     * @param bcProject 百川项目地理空间数据
     * @return 结果
     */
    @Override
    public int insertBcProject(BcProject bcProject)
    {
        return bcProjectMapper.insertBcProject(bcProject);
    }

    /**
     * 修改百川项目地理空间数据
     * 
     * @param bcProject 百川项目地理空间数据
     * @return 结果
     */
    @Override
    public int updateBcProject(BcProject bcProject)
    {
        return bcProjectMapper.updateBcProject(bcProject);
    }

    /**
     * 批量删除百川项目地理空间数据
     * 
     * @param objectids 需要删除的百川项目地理空间数据主键
     * @return 结果
     */
    @Override
    public int deleteBcProjectByObjectids(Long[] objectids)
    {
        return bcProjectMapper.deleteBcProjectByObjectids(objectids);
    }

    /**
     * 删除百川项目地理空间数据信息
     * 
     * @param objectid 百川项目地理空间数据主键
     * @return 结果
     */
    @Override
    public int deleteBcProjectByObjectid(Long objectid)
    {
        return bcProjectMapper.deleteBcProjectByObjectid(objectid);
    }

    @Override
    public int deleteBcProjectByXMBH(String xmbh)
    {
        return bcProjectMapper.deleteBcProjectByXMBH(xmbh);
    }

    @Override
    public List<BcProject> selectSysProjectGeoinfoByProjectIds(List<String> projectIds)
    {
        return bcProjectMapper.selectSysProjectGeoinfoByProjectIds(projectIds);
    }
}
