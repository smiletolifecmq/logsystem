package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoNofundingProject;
import com.kcylog.system.service.ISysGeoNofundingProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 无经费服务项目统计Controller
 * 
 * @author ruoyi
 * @date 2024-10-22
 */
@RestController
@RequestMapping("/system/geoNofundingProject")
public class SysGeoNofundingProjectController extends BaseController
{
    @Autowired
    private ISysGeoNofundingProjectService sysGeoNofundingProjectService;

    /**
     * 查询无经费服务项目统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:geoNofundingProject:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysGeoNofundingProject sysGeoNofundingProject)
    {
        startPage();
        List<SysGeoNofundingProject> list = sysGeoNofundingProjectService.selectSysGeoNofundingProjectList(sysGeoNofundingProject);
        return getDataTable(list);
    }

    /**
     * 导出无经费服务项目统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:geoNofundingProject:export')")
    @Log(title = "无经费服务项目统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoNofundingProject sysGeoNofundingProject)
    {
        List<SysGeoNofundingProject> list = sysGeoNofundingProjectService.selectSysGeoNofundingProjectList(sysGeoNofundingProject);
        int num = 0;
        for (SysGeoNofundingProject obj : list){
            num ++;
            obj.setNum(num);
        }
        ExcelUtil<SysGeoNofundingProject> util = new ExcelUtil<SysGeoNofundingProject>(SysGeoNofundingProject.class);
        util.exportExcel(response, list, "无经费服务项目统计数据");
    }

    /**
     * 获取无经费服务项目统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:geoNofundingProject:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysGeoNofundingProjectService.selectSysGeoNofundingProjectById(id));
    }

    /**
     * 新增无经费服务项目统计
     */
    @PreAuthorize("@ss.hasPermi('system:geoNofundingProject:add')")
    @Log(title = "无经费服务项目统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoNofundingProject sysGeoNofundingProject)
    {
        sysGeoNofundingProject.setFzr(SecurityUtils.getUsername());
        return toAjax(sysGeoNofundingProjectService.insertSysGeoNofundingProject(sysGeoNofundingProject));
    }

    /**
     * 修改无经费服务项目统计
     */
    @PreAuthorize("@ss.hasPermi('system:geoNofundingProject:edit')")
    @Log(title = "无经费服务项目统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoNofundingProject sysGeoNofundingProject)
    {
        return toAjax(sysGeoNofundingProjectService.updateSysGeoNofundingProject(sysGeoNofundingProject));
    }

    /**
     * 删除无经费服务项目统计
     */
    @PreAuthorize("@ss.hasPermi('system:geoNofundingProject:remove')")
    @Log(title = "无经费服务项目统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysGeoNofundingProjectService.deleteSysGeoNofundingProjectByIds(ids));
    }
}
