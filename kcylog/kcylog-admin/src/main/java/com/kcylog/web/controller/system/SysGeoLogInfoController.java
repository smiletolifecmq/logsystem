package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoLogInfo;
import com.kcylog.system.service.ISysGeoLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 地理部门日志详情Controller
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/system/info")
public class SysGeoLogInfoController extends BaseController
{
    @Autowired
    private ISysGeoLogInfoService sysGeoLogInfoService;

    /**
     * 查询地理部门日志详情列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysGeoLogInfo sysGeoLogInfo)
    {
        startPage();
        List<SysGeoLogInfo> list = sysGeoLogInfoService.selectSysGeoLogInfoList(sysGeoLogInfo);
        return getDataTable(list);
    }

    /**
     * 导出地理部门日志详情列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "地理部门日志详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoLogInfo sysGeoLogInfo)
    {
        List<SysGeoLogInfo> list = sysGeoLogInfoService.selectSysGeoLogInfoList(sysGeoLogInfo);
        ExcelUtil<SysGeoLogInfo> util = new ExcelUtil<SysGeoLogInfo>(SysGeoLogInfo.class);
        util.exportExcel(response, list, "地理部门日志详情数据");
    }

    /**
     * 获取地理部门日志详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{logInfoId}")
    public AjaxResult getInfo(@PathVariable("logInfoId") Long logInfoId)
    {
        return success(sysGeoLogInfoService.selectSysGeoLogInfoByLogInfoId(logInfoId));
    }

    /**
     * 新增地理部门日志详情
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "地理部门日志详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoLogInfo sysGeoLogInfo)
    {
        return toAjax(sysGeoLogInfoService.insertSysGeoLogInfo(sysGeoLogInfo));
    }

    /**
     * 修改地理部门日志详情
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "地理部门日志详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoLogInfo sysGeoLogInfo)
    {
        return toAjax(sysGeoLogInfoService.updateSysGeoLogInfo(sysGeoLogInfo));
    }

    /**
     * 删除地理部门日志详情
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "地理部门日志详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{logInfoIds}")
    public AjaxResult remove(@PathVariable Long[] logInfoIds)
    {
        return toAjax(sysGeoLogInfoService.deleteSysGeoLogInfoByLogInfoIds(logInfoIds));
    }
}
