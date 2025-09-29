package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysServiceWorkType;
import com.kcylog.system.service.ISysServiceWorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 内部服务类型表Controller
 * 
 * @author ruoyi
 * @date 2025-09-28
 */
@RestController
@RequestMapping("/system/serverType")
public class SysServiceWorkTypeController extends BaseController
{
    @Autowired
    private ISysServiceWorkTypeService sysServiceWorkTypeService;

    /**
     * 查询内部服务类型表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysServiceWorkType sysServiceWorkType)
    {
        List<SysServiceWorkType> list = sysServiceWorkTypeService.selectSysServiceWorkTypeList(sysServiceWorkType);
        return getDataTable(list);
    }

    /**
     * 导出内部服务类型表列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:export')")
    @Log(title = "内部服务类型表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysServiceWorkType sysServiceWorkType)
    {
        List<SysServiceWorkType> list = sysServiceWorkTypeService.selectSysServiceWorkTypeList(sysServiceWorkType);
        ExcelUtil<SysServiceWorkType> util = new ExcelUtil<SysServiceWorkType>(SysServiceWorkType.class);
        util.exportExcel(response, list, "内部服务类型表数据");
    }

    /**
     * 获取内部服务类型表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysServiceWorkTypeService.selectSysServiceWorkTypeById(id));
    }

    /**
     * 新增内部服务类型表
     */
    @PreAuthorize("@ss.hasPermi('system:type:add')")
    @Log(title = "内部服务类型表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysServiceWorkType sysServiceWorkType)
    {
        return toAjax(sysServiceWorkTypeService.insertSysServiceWorkType(sysServiceWorkType));
    }

    /**
     * 修改内部服务类型表
     */
    @PreAuthorize("@ss.hasPermi('system:type:edit')")
    @Log(title = "内部服务类型表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysServiceWorkType sysServiceWorkType)
    {
        return toAjax(sysServiceWorkTypeService.updateSysServiceWorkType(sysServiceWorkType));
    }

    /**
     * 删除内部服务类型表
     */
    @PreAuthorize("@ss.hasPermi('system:type:remove')")
    @Log(title = "内部服务类型表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysServiceWorkTypeService.deleteSysServiceWorkTypeByIds(ids));
    }
}
