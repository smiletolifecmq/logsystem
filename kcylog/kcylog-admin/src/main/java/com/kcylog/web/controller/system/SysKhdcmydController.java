package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysKhdcmyd;
import com.kcylog.system.service.ISysKhdcmydService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 客户调查满意度Controller
 * 
 * @author ruoyi
 * @date 2025-04-11
 */
@RestController
@RequestMapping("/system/khdcmyd")
public class SysKhdcmydController extends BaseController
{
    @Autowired
    private ISysKhdcmydService sysKhdcmydService;

    /**
     * 查询客户调查满意度列表
     */
    @PreAuthorize("@ss.hasPermi('system:khdcmyd:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysKhdcmyd sysKhdcmyd)
    {
        startPage();
        List<SysKhdcmyd> list = sysKhdcmydService.selectSysKhdcmydList(sysKhdcmyd);
        return getDataTable(list);
    }

    /**
     * 导出客户调查满意度列表
     */
    @PreAuthorize("@ss.hasPermi('system:khdcmyd:export')")
    @Log(title = "客户调查满意度", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysKhdcmyd sysKhdcmyd)
    {
        List<SysKhdcmyd> list = sysKhdcmydService.selectSysKhdcmydList(sysKhdcmyd);
        ExcelUtil<SysKhdcmyd> util = new ExcelUtil<SysKhdcmyd>(SysKhdcmyd.class);
        util.exportExcel(response, list, "客户调查满意度数据");
    }

    /**
     * 获取客户调查满意度详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:khdcmyd:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysKhdcmydService.selectSysKhdcmydById(id));
    }

    /**
     * 新增客户调查满意度
     */
    @PreAuthorize("@ss.hasPermi('system:khdcmyd:add')")
    @Log(title = "客户调查满意度", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysKhdcmyd sysKhdcmyd)
    {
        return toAjax(sysKhdcmydService.insertSysKhdcmyd(sysKhdcmyd));
    }

    /**
     * 修改客户调查满意度
     */
    @PreAuthorize("@ss.hasPermi('system:khdcmyd:edit')")
    @Log(title = "客户调查满意度", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysKhdcmyd sysKhdcmyd)
    {
        return toAjax(sysKhdcmydService.updateSysKhdcmyd(sysKhdcmyd));
    }

    /**
     * 删除客户调查满意度
     */
    @PreAuthorize("@ss.hasPermi('system:khdcmyd:remove')")
    @Log(title = "客户调查满意度", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysKhdcmydService.deleteSysKhdcmydByIds(ids));
    }
}
