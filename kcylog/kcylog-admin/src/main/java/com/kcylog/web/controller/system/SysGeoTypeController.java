package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoType;
import com.kcylog.system.service.ISysGeoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 类型配置表Controller
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
@RestController
@RequestMapping("/system/geoType")
public class SysGeoTypeController extends BaseController
{
    @Autowired
    private ISysGeoTypeService sysGeoTypeService;

    /**
     * 查询类型配置表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoType sysGeoType)
    {
        startPage();
        List<SysGeoType> list = sysGeoTypeService.selectSysGeoTypeList(sysGeoType);
        return getDataTable(list);
    }

    /**
     * 导出类型配置表列表
     */
    @Log(title = "类型配置表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoType sysGeoType)
    {
        List<SysGeoType> list = sysGeoTypeService.selectSysGeoTypeList(sysGeoType);
        ExcelUtil<SysGeoType> util = new ExcelUtil<SysGeoType>(SysGeoType.class);
        util.exportExcel(response, list, "类型配置表数据");
    }

    /**
     * 获取类型配置表详细信息
     */
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId)
    {
        return success(sysGeoTypeService.selectSysGeoTypeByTypeId(typeId));
    }

    /**
     * 新增类型配置表
     */
    @Log(title = "类型配置表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoType sysGeoType)
    {
        return toAjax(sysGeoTypeService.insertSysGeoType(sysGeoType));
    }

    /**
     * 修改类型配置表
     */
    @Log(title = "类型配置表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoType sysGeoType)
    {
        return toAjax(sysGeoTypeService.updateSysGeoType(sysGeoType));
    }

    /**
     * 删除类型配置表
     */
    @Log(title = "类型配置表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds)
    {
        return toAjax(sysGeoTypeService.deleteSysGeoTypeByTypeIds(typeIds));
    }
}
