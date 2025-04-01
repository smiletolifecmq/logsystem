package com.kcylog.system.mapper;

import com.kcylog.common.annotation.DataSource;
import com.kcylog.common.enums.DataSourceType;
import com.kcylog.system.domain.BcProject;

import java.util.List;

/**
 * 百川项目地理空间数据Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-31
 */
@DataSource(value = DataSourceType.SLAVEORACLE)
public interface BcProjectMapper 
{
    /**
     * 查询百川项目地理空间数据
     * 
     * @param objectid 百川项目地理空间数据主键
     * @return 百川项目地理空间数据
     */
    public BcProject selectBcProjectByObjectid(Long objectid);

    /**
     * 查询百川项目地理空间数据列表
     * 
     * @param bcProject 百川项目地理空间数据
     * @return 百川项目地理空间数据集合
     */
    public List<BcProject> selectBcProjectList(BcProject bcProject);

    /**
     * 新增百川项目地理空间数据
     * 
     * @param bcProject 百川项目地理空间数据
     * @return 结果
     */
    public int insertBcProject(BcProject bcProject);

    /**
     * 修改百川项目地理空间数据
     * 
     * @param bcProject 百川项目地理空间数据
     * @return 结果
     */
    public int updateBcProject(BcProject bcProject);

    /**
     * 删除百川项目地理空间数据
     * 
     * @param objectid 百川项目地理空间数据主键
     * @return 结果
     */
    public int deleteBcProjectByObjectid(Long objectid);

    /**
     * 批量删除百川项目地理空间数据
     * 
     * @param objectids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBcProjectByObjectids(Long[] objectids);

    public int deleteBcProjectByXMBH(String xmbh);

    public List<BcProject> selectSysProjectGeoinfoByProjectIds(List<String> projectIds);

    public BcProject selectBcProjectByXmbh(String xmbh);
}
