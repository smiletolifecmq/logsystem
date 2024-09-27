package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysManagementWkparrival;
import com.kcylog.system.service.ISysManagementWkparrivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 到账未开票统计Controller
 * 
 * @author ruoyi
 * @date 2024-09-27
 */
@RestController
@RequestMapping("/system/wkparrival")
public class SysManagementWkparrivalController extends BaseController
{
    @Autowired
    private ISysManagementWkparrivalService sysManagementWkparrivalService;

    /**
     * 查询到账未开票统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:wkparrival:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysManagementWkparrival sysManagementWkparrival)
    {
        startPage();
        List<SysManagementWkparrival> list = sysManagementWkparrivalService.selectSysManagementWkparrivalList(sysManagementWkparrival);
        return getDataTable(list);
    }

    /**
     * 导出到账未开票统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:wkparrival:export')")
    @Log(title = "到账未开票统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysManagementWkparrival sysManagementWkparrival)
    {
        List<SysManagementWkparrival> list = sysManagementWkparrivalService.selectSysManagementWkparrivalList(sysManagementWkparrival);
        ExcelUtil<SysManagementWkparrival> util = new ExcelUtil<SysManagementWkparrival>(SysManagementWkparrival.class);
        util.exportExcel(response, list, "到账未开票统计数据");
    }

    /**
     * 获取到账未开票统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:wkparrival:query')")
    @GetMapping(value = "/{dzId}")
    public AjaxResult getInfo(@PathVariable("dzId") Long dzId)
    {
        return success(sysManagementWkparrivalService.selectSysManagementWkparrivalByDzId(dzId));
    }

    /**
     * 新增到账未开票统计
     */
    @PreAuthorize("@ss.hasPermi('system:wkparrival:add')")
    @Log(title = "到账未开票统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysManagementWkparrival sysManagementWkparrival)
    {
        return toAjax(sysManagementWkparrivalService.insertSysManagementWkparrival(sysManagementWkparrival));
    }

    /**
     * 修改到账未开票统计
     */
    @PreAuthorize("@ss.hasPermi('system:wkparrival:edit')")
    @Log(title = "到账未开票统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysManagementWkparrival sysManagementWkparrival)
    {
        return toAjax(sysManagementWkparrivalService.updateSysManagementWkparrival(sysManagementWkparrival));
    }

    /**
     * 删除到账未开票统计
     */
    @PreAuthorize("@ss.hasPermi('system:wkparrival:remove')")
    @Log(title = "到账未开票统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dzIds}")
    public AjaxResult remove(@PathVariable Long[] dzIds)
    {
        return toAjax(sysManagementWkparrivalService.deleteSysManagementWkparrivalByDzIds(dzIds));
    }
}
