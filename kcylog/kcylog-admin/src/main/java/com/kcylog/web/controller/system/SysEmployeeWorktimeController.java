package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysEmployeeWorktime;
import com.kcylog.system.service.ISysEmployeeWorktimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 雇工工作时间记录Controller
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/system/worktime")
public class SysEmployeeWorktimeController extends BaseController
{
    @Autowired
    private ISysEmployeeWorktimeService sysEmployeeWorktimeService;

    /**
     * 查询雇工工作时间记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysEmployeeWorktime sysEmployeeWorktime)
    {
        startPage();
        List<SysEmployeeWorktime> list = sysEmployeeWorktimeService.selectSysEmployeeWorktimeList(sysEmployeeWorktime);
        return getDataTable(list);
    }

    /**
     * 导出雇工工作时间记录列表
     */
    @Log(title = "雇工工作时间记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysEmployeeWorktime sysEmployeeWorktime)
    {
        List<SysEmployeeWorktime> list = sysEmployeeWorktimeService.selectSysEmployeeWorktimeList(sysEmployeeWorktime);
        ExcelUtil<SysEmployeeWorktime> util = new ExcelUtil<SysEmployeeWorktime>(SysEmployeeWorktime.class);
        util.exportExcel(response, list, "雇工工作时间记录数据");
    }

    /**
     * 获取雇工工作时间记录详细信息
     */
    @GetMapping(value = "/{worktimeId}")
    public AjaxResult getInfo(@PathVariable("worktimeId") Long worktimeId)
    {
        return success(sysEmployeeWorktimeService.selectSysEmployeeWorktimeByWorktimeId(worktimeId));
    }

    /**
     * 新增雇工工作时间记录
     */
    @Log(title = "雇工工作时间记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysEmployeeWorktime sysEmployeeWorktime)
    {
        return toAjax(sysEmployeeWorktimeService.insertSysEmployeeWorktime(sysEmployeeWorktime));
    }

    /**
     * 修改雇工工作时间记录
     */
    @Log(title = "雇工工作时间记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysEmployeeWorktime sysEmployeeWorktime)
    {
        return toAjax(sysEmployeeWorktimeService.updateSysEmployeeWorktime(sysEmployeeWorktime));
    }

    /**
     * 删除雇工工作时间记录
     */
    @Log(title = "雇工工作时间记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{worktimeIds}")
    public AjaxResult remove(@PathVariable Long[] worktimeIds)
    {
        return toAjax(sysEmployeeWorktimeService.deleteSysEmployeeWorktimeByWorktimeIds(worktimeIds));
    }
}
