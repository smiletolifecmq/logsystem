package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysProjectProgress;
import com.kcylog.system.service.ISysProjectProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 项目进度管控Controller
 * 
 * @author ruoyi
 * @date 2023-07-11
 */
@RestController
@RequestMapping("/system/projectProgress")
public class SysProjectProgressController extends BaseController
{
    @Autowired
    private ISysProjectProgressService sysProjectProgressService;

    /**
     * 查询项目进度管控列表
     */
    @PreAuthorize("@ss.hasPermi('system:projectProgress:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProjectProgress sysProjectProgress)
    {
        startPage();
        List<SysProjectProgress> list = sysProjectProgressService.selectSysProjectProgressList(sysProjectProgress);
        return getDataTable(list);
    }

    /**
     * 导出项目进度管控列表
     */
    @PreAuthorize("@ss.hasPermi('system:projectProgress:export')")
    @Log(title = "项目进度管控", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProjectProgress sysProjectProgress)
    {
        List<SysProjectProgress> list = sysProjectProgressService.selectSysProjectProgressList(sysProjectProgress);
        ExcelUtil<SysProjectProgress> util = new ExcelUtil<SysProjectProgress>(SysProjectProgress.class);
        util.exportExcel(response, list, "项目进度管控数据");
    }

    /**
     * 获取项目进度管控详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:projectProgress:query')")
    @GetMapping(value = "/{progressId}")
    public AjaxResult getInfo(@PathVariable("progressId") Long progressId)
    {
        return success(sysProjectProgressService.selectSysProjectProgressByProgressId(progressId));
    }

    /**
     * 新增项目进度管控
     */
    @PreAuthorize("@ss.hasPermi('system:projectProgress:add')")
    @Log(title = "项目进度管控", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProjectProgress sysProjectProgress)
    {
        return toAjax(sysProjectProgressService.insertSysProjectProgress(sysProjectProgress));
    }

    /**
     * 修改项目进度管控
     */
    @PreAuthorize("@ss.hasPermi('system:projectProgress:edit')")
    @Log(title = "项目进度管控", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProjectProgress sysProjectProgress)
    {
        return toAjax(sysProjectProgressService.updateSysProjectProgress(sysProjectProgress));
    }

    /**
     * 删除项目进度管控
     */
    @PreAuthorize("@ss.hasPermi('system:projectProgress:remove')")
    @Log(title = "项目进度管控", businessType = BusinessType.DELETE)
	@DeleteMapping("/{progressIds}")
    public AjaxResult remove(@PathVariable Long[] progressIds)
    {
        return toAjax(sysProjectProgressService.deleteSysProjectProgressByProgressIds(progressIds));
    }
}
