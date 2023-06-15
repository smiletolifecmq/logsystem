package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysReviewEmployee;
import com.kcylog.system.service.ISysReviewEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 雇工实际工作内容记录Controller
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/system/reviewEmployee")
public class SysReviewEmployeeController extends BaseController
{
    @Autowired
    private ISysReviewEmployeeService sysReviewEmployeeService;

    /**
     * 查询雇工实际工作内容记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysReviewEmployee sysReviewEmployee)
    {
        startPage();
        List<SysReviewEmployee> list = sysReviewEmployeeService.selectSysReviewEmployeeList(sysReviewEmployee);
        return getDataTable(list);
    }

    /**
     * 导出雇工实际工作内容记录列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysReviewEmployee sysReviewEmployee)
    {
        List<SysReviewEmployee> list = sysReviewEmployeeService.selectSysReviewEmployeeList(sysReviewEmployee);
        ExcelUtil<SysReviewEmployee> util = new ExcelUtil<SysReviewEmployee>(SysReviewEmployee.class);
        util.exportExcel(response, list, "雇工实际工作内容记录数据");
    }

    /**
     * 获取雇工实际工作内容记录详细信息
     */
    @GetMapping(value = "/{reviewEmployeeId}")
    public AjaxResult getInfo(@PathVariable("reviewEmployeeId") Long reviewEmployeeId)
    {
        return success(sysReviewEmployeeService.selectSysReviewEmployeeByReviewEmployeeId(reviewEmployeeId));
    }

    /**
     * 新增雇工实际工作内容记录
     */
    @Log(title = "雇工实际工作内容记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysReviewEmployee sysReviewEmployee)
    {
        return toAjax(sysReviewEmployeeService.insertSysReviewEmployee(sysReviewEmployee));
    }

    /**
     * 修改雇工实际工作内容记录
     */
    @Log(title = "雇工实际工作内容记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysReviewEmployee sysReviewEmployee)
    {
        return toAjax(sysReviewEmployeeService.updateSysReviewEmployee(sysReviewEmployee));
    }

    /**
     * 删除雇工实际工作内容记录
     */
    @Log(title = "雇工实际工作内容记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reviewEmployeeIds}")
    public AjaxResult remove(@PathVariable Long[] reviewEmployeeIds)
    {
        return toAjax(sysReviewEmployeeService.deleteSysReviewEmployeeByReviewEmployeeIds(reviewEmployeeIds));
    }
}
