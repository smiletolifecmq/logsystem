package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoProject;
import com.kcylog.system.service.ISysGeoProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 地理项目Controller
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
@RestController
@RequestMapping("/system/geoProject")
public class SysGeoProjectController extends BaseController
{
    @Autowired
    private ISysGeoProjectService sysGeoProjectService;

    /**
     * 查询地理项目列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoProject sysGeoProject)
    {
        startPage();
        List<SysGeoProject> list = sysGeoProjectService.selectSysGeoProjectList(sysGeoProject);
        return getDataTable(list);
    }

    /**
     * 导出地理项目列表
     */
    @Log(title = "地理项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoProject sysGeoProject)
    {
        List<SysGeoProject> list = sysGeoProjectService.selectSysGeoProjectList(sysGeoProject);
        ExcelUtil<SysGeoProject> util = new ExcelUtil<SysGeoProject>(SysGeoProject.class);
        util.exportExcel(response, list, "地理项目数据");
    }

    /**
     * 获取地理项目详细信息
     */
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") Long projectId)
    {
        return success(sysGeoProjectService.selectSysGeoProjectByProjectId(projectId));
    }

    /**
     * 新增地理项目
     */
    @Log(title = "地理项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoProject sysGeoProject)
    {
        sysGeoProject.setUserId(SecurityUtils.getUserId());
        sysGeoProject.setUserName(SecurityUtils.getUsername());
        return toAjax(sysGeoProjectService.insertSysGeoProject(sysGeoProject));
    }

    /**
     * 修改地理项目
     */
    @Log(title = "地理项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoProject sysGeoProject)
    {
        sysGeoProject.setUserId(SecurityUtils.getUserId());
        sysGeoProject.setUserName(SecurityUtils.getUsername());
        return toAjax(sysGeoProjectService.updateSysGeoProject(sysGeoProject));
    }

    /**
     * 删除地理项目
     */
    @Log(title = "地理项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        return toAjax(sysGeoProjectService.deleteSysGeoProjectByProjectIds(projectIds));
    }
}
