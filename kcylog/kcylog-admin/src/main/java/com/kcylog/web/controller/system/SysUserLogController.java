package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.domain.entity.SysDept;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysUserLog;
import com.kcylog.system.service.ISysDeptService;
import com.kcylog.system.service.ISysUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户日志Controller
 *
 * @author ruoyi
 * @date 2023-05-06
 */
@RestController
@RequestMapping("/system/log")
public class SysUserLogController extends BaseController {
    @Autowired
    private ISysUserLogService sysUserLogService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询用户日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUserLog sysUserLog) {
        startPage();
        List<SysUserLog> list = sysUserLogService.selectSysUserLogList(sysUserLog);
        return getDataTable(list);
    }

    /**
     * 导出用户日志列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:export')")
    @Log(title = "用户日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserLog sysUserLog) {
        List<SysUserLog> list = sysUserLogService.selectSysUserLogList(sysUserLog);
        ExcelUtil<SysUserLog> util = new ExcelUtil<SysUserLog>(SysUserLog.class);
        util.exportExcel(response, list, "用户日志数据");
    }

    /**
     * 获取用户日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:log:query')")
    @GetMapping(value = "/{userLogId}")
    public AjaxResult getInfo(@PathVariable("userLogId") Long userLogId) {
        return success(sysUserLogService.selectSysUserLogByUserLogId(userLogId));
    }

    /**
     * 新增用户日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:add')")
    @Log(title = "用户日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SysUserLog sysUserLog) {
        sysUserLog.setUserId(getUserId());
        return toAjax(sysUserLogService.insertSysUserLog(sysUserLog));
    }

    /**
     * 修改用户日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:edit')")
    @Log(title = "用户日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserLog sysUserLog) {
        return toAjax(sysUserLogService.updateSysUserLog(sysUserLog));
    }

    /**
     * 删除用户日志
     */
    @PreAuthorize("@ss.hasPermi('system:log:remove')")
    @Log(title = "用户日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userLogIds}")
    public AjaxResult remove(@PathVariable Long[] userLogIds) {
        return toAjax(sysUserLogService.deleteSysUserLogByUserLogIds(userLogIds));
    }

    /**
     * 获取部门树列表
     */
    @PreAuthorize("@ss.hasPermi('system:log:list')")
    @GetMapping("/deptTree")
    public AjaxResult deptTree(SysDept dept) {
        return success(deptService.selectDeptTreeList(dept));
    }

    /**
     * 获取部门树列表
     */
    @GetMapping("/deptTreeNotPer")
    public AjaxResult deptTreeNotPer(SysDept dept) {
        return success(deptService.selectDeptTreeList(dept));
    }
}
