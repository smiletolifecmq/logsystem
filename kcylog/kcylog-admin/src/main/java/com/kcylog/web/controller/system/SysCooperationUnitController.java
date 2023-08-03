package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysCooperationUnit;
import com.kcylog.system.service.ISysCooperationUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 协作单位Controller
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@RestController
@RequestMapping("/system/unit")
public class SysCooperationUnitController extends BaseController
{
    @Autowired
    private ISysCooperationUnitService sysCooperationUnitService;

    /**
     * 查询协作单位列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysCooperationUnit sysCooperationUnit)
    {
        startPage();
        List<SysCooperationUnit> list = sysCooperationUnitService.selectSysCooperationUnitList(sysCooperationUnit);
        return getDataTable(list);
    }

    /**
     * 导出协作单位列表
     */
    @Log(title = "协作单位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCooperationUnit sysCooperationUnit)
    {
        List<SysCooperationUnit> list = sysCooperationUnitService.selectSysCooperationUnitList(sysCooperationUnit);
        ExcelUtil<SysCooperationUnit> util = new ExcelUtil<SysCooperationUnit>(SysCooperationUnit.class);
        util.exportExcel(response, list, "协作单位数据");
    }

    /**
     * 获取协作单位详细信息
     */
    @GetMapping(value = "/{unitId}")
    public AjaxResult getInfo(@PathVariable("unitId") Long unitId)
    {
        return success(sysCooperationUnitService.selectSysCooperationUnitByUnitId(unitId));
    }

    /**
     * 新增协作单位
     */
    @Log(title = "协作单位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCooperationUnit sysCooperationUnit)
    {
        return toAjax(sysCooperationUnitService.insertSysCooperationUnit(sysCooperationUnit));
    }

    /**
     * 修改协作单位
     */
    @Log(title = "协作单位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCooperationUnit sysCooperationUnit)
    {
        return toAjax(sysCooperationUnitService.updateSysCooperationUnit(sysCooperationUnit));
    }

    /**
     * 删除协作单位
     */
    @Log(title = "协作单位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{unitIds}")
    public AjaxResult remove(@PathVariable Long[] unitIds)
    {
        return toAjax(sysCooperationUnitService.deleteSysCooperationUnitByUnitIds(unitIds));
    }
}
