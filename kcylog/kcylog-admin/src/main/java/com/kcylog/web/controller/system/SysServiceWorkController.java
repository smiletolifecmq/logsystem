package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysServiceWork;
import com.kcylog.system.service.ISysServiceWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2025-09-01
 */
@RestController
@RequestMapping("/system/serviceWork")
public class SysServiceWorkController extends BaseController
{
    @Autowired
    private ISysServiceWorkService sysServiceWorkService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:serviceWork:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysServiceWork sysServiceWork)
    {
        startPage();
        List<SysServiceWork> list = sysServiceWorkService.selectSysServiceWorkList(sysServiceWork);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:serviceWork:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysServiceWork sysServiceWork)
    {
        List<SysServiceWork> list = sysServiceWorkService.selectSysServiceWorkList(sysServiceWork);
        int num = 1;
        for (SysServiceWork obj : list){
            obj.setNum(num);
            num ++;
        }
        ExcelUtil<SysServiceWork> util = new ExcelUtil<SysServiceWork>(SysServiceWork.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:serviceWork:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysServiceWorkService.selectSysServiceWorkById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:serviceWork:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysServiceWork sysServiceWork)
    {
        return toAjax(sysServiceWorkService.insertSysServiceWork(sysServiceWork));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:serviceWork:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysServiceWork sysServiceWork)
    {
        return toAjax(sysServiceWorkService.updateSysServiceWork(sysServiceWork));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:serviceWork:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysServiceWorkService.deleteSysServiceWorkByIds(ids));
    }
}
