package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.exception.review.EmployeeExistsException;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysEmployee;
import com.kcylog.system.service.ISysEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 雇工Controller
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/system/employee")
public class SysEmployeeController extends BaseController
{
    @Autowired
    private ISysEmployeeService sysEmployeeService;

    /**
     * 查询雇工列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysEmployee sysEmployee)
    {
        startPage();
        List<SysEmployee> list = sysEmployeeService.selectSysEmployeeList(sysEmployee);
        return getDataTable(list);
    }

    /**
     * 导出雇工列表
     */
    @Log(title = "雇工", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysEmployee sysEmployee)
    {
        List<SysEmployee> list = sysEmployeeService.selectSysEmployeeList(sysEmployee);
        ExcelUtil<SysEmployee> util = new ExcelUtil<SysEmployee>(SysEmployee.class);
        util.exportExcel(response, list, "雇工数据");
    }

    /**
     * 获取雇工详细信息
     */
    @GetMapping(value = "/{employeeId}")
    public AjaxResult getInfo(@PathVariable("employeeId") Long employeeId)
    {
        return success(sysEmployeeService.selectSysEmployeeByEmployeeId(employeeId));
    }

    /**
     * 新增雇工
     */
    @Log(title = "雇工", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysEmployee sysEmployee)
    {
        try
        {
            sysEmployeeService.insertSysEmployee(sysEmployee);
            return toAjax(1);
        }
        catch (Exception e)
        {
            throw new EmployeeExistsException();
        }
    }

    /**
     * 修改雇工
     */
    @Log(title = "雇工", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysEmployee sysEmployee)
    {
        return toAjax(sysEmployeeService.updateSysEmployee(sysEmployee));
    }

    /**
     * 删除雇工
     */
    @Log(title = "雇工", businessType = BusinessType.DELETE)
	@DeleteMapping("/{employeeIds}")
    public AjaxResult remove(@PathVariable Long[] employeeIds)
    {
        return toAjax(sysEmployeeService.deleteSysEmployeeByEmployeeIds(employeeIds));
    }
}
