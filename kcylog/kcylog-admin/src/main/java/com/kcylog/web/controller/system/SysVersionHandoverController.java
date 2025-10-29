package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysVersionHandover;
import com.kcylog.system.service.ISysVersionHandoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 多版本移交Controller
 * 
 * @author ruoyi
 * @date 2025-10-29
 */
@RestController
@RequestMapping("/system/handoverVersion")
public class SysVersionHandoverController extends BaseController
{
    @Autowired
    private ISysVersionHandoverService sysVersionHandoverService;

    /**
     * 查询多版本移交列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysVersionHandover sysVersionHandover)
    {
        List<SysVersionHandover> list = sysVersionHandoverService.selectSysVersionHandoverList(sysVersionHandover);
        return getDataTable(list);
    }

    /**
     * 导出多版本移交列表
     */
    @Log(title = "多版本移交", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysVersionHandover sysVersionHandover)
    {
        List<SysVersionHandover> list = sysVersionHandoverService.selectSysVersionHandoverList(sysVersionHandover);
        ExcelUtil<SysVersionHandover> util = new ExcelUtil<SysVersionHandover>(SysVersionHandover.class);
        util.exportExcel(response, list, "多版本移交数据");
    }

    /**
     * 获取多版本移交详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysVersionHandoverService.selectSysVersionHandoverById(id));
    }

    /**
     * 新增多版本移交
     */
    @Log(title = "多版本移交", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysVersionHandover sysVersionHandover)
    {
        List<SysVersionHandover> list = sysVersionHandoverService.selectSysVersionHandoverList(sysVersionHandover);
        if (list != null && list.size() > 0){
            return error("该版本移交已存在");
        }
        return toAjax(sysVersionHandoverService.insertSysVersionHandover(sysVersionHandover));
    }

    /**
     * 修改多版本移交
     */
    @Log(title = "多版本移交", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysVersionHandover sysVersionHandover)
    {
        return toAjax(sysVersionHandoverService.updateSysVersionHandover(sysVersionHandover));
    }

    /**
     * 删除多版本移交
     */
    @Log(title = "多版本移交", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysVersionHandoverService.deleteSysVersionHandoverByIds(ids));
    }
}
