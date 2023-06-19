package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.exception.ServiceException;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.*;
import com.kcylog.system.service.ISysEmployeeService;
import com.kcylog.system.service.ISysEmployeeWorktimeService;
import com.kcylog.system.service.ISysReviewEmployeeService;
import com.kcylog.system.service.ISysReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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
    private ISysEmployeeWorktimeService sysEmployeeWorktimeService;

    @Autowired
    private ISysReviewEmployeeService sysReviewEmployeeService;

    @Autowired
    private ISysReviewService sysReviewService;

    @Autowired
    private ISysEmployeeService sysEmployeeService;
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
        util.exportExcel(response, list, "雇工实际工作记录数据");
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
    @Transactional
    public AjaxResult add(@RequestBody SysReviewEmployee sysReviewEmployee)
    {
        //判断金额
        SysReview sysReview = sysReviewService.selectSysReviewByReviewId(sysReviewEmployee.getReviewId().toString());
        List<SysReviewEmployee> list = sysReviewEmployeeService.selectSysReviewEmployeeList(sysReviewEmployee);
        BigDecimal money = BigDecimal.valueOf(0.00);
        for (SysReviewEmployee employee : list) {
            money = money.add(employee.getCost());
        }
        money = money.add(sysReviewEmployee.getCost());
        int result = sysReview.getBudgetMoney().compareTo(money);
        if (result < 0) {
            throw new ServiceException("审核单雇工实际总费用大于预估费用～");
        }

        //判断雇工时间
        SysEmployee sysEmployee = sysEmployeeService.selectSysEmployeeByIdCard(sysReviewEmployee.getIdCard());

        sysReviewEmployeeService.insertSysReviewEmployee(sysReviewEmployee);
        for (WorkTimeStamp reviewEmployee :sysReviewEmployee.getWorkTimeStamp()){
            SysEmployeeWorktime employeeWorktime = new SysEmployeeWorktime();
            employeeWorktime.setEmployeeId(sysEmployee.getEmployeeId());
            employeeWorktime.setStartTime(reviewEmployee.getStartTime());
            employeeWorktime.setEndTime(reviewEmployee.getEndTime());
            employeeWorktime.setReviewId(sysReviewEmployee.getReviewId());
            employeeWorktime.setReviewEmployeeId(sysReviewEmployee.getReviewEmployeeId());
            List<SysEmployeeWorktime> listWorktime = sysEmployeeWorktimeService.selectSysEmployeeWorktimeExists(employeeWorktime);
            if (listWorktime.size() > 0){
                throw new ServiceException("该雇工在这时间段已经安排了工作～");
            }
            sysEmployeeWorktimeService.insertSysEmployeeWorktime(employeeWorktime);
        }

        return toAjax(1);
    }

    /**
     * 修改雇工实际工作内容记录
     */
    @Log(title = "雇工实际工作内容记录", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    public AjaxResult edit(@RequestBody SysReviewEmployee sysReviewEmployee)
    {
        SysReview sysReview = sysReviewService.selectSysReviewByReviewId(sysReviewEmployee.getReviewId().toString());
        List<SysReviewEmployee> list = sysReviewEmployeeService.selectSysReviewEmployeeListNotItself(sysReviewEmployee);
        BigDecimal money = BigDecimal.valueOf(0.00);
        for (SysReviewEmployee employee : list) {
            money = money.add(employee.getCost());
        }
        money = money.add(sysReviewEmployee.getCost());
        int result = sysReview.getBudgetMoney().compareTo(money);
        if (result < 0) {
            throw new ServiceException("审核单雇工实际总费用大于预估费用～");
        }


        SysEmployee sysEmployee = sysEmployeeService.selectSysEmployeeByIdCard(sysReviewEmployee.getIdCard());

        if (sysReviewEmployee.getWorkTimeStamp() != null){
            Long[] reviewRemployeeId = {sysReviewEmployee.getReviewEmployeeId()};
            sysEmployeeWorktimeService.deleteSysEmployeeWorktimeByReviewEmployeeIds(reviewRemployeeId);
            for (WorkTimeStamp reviewEmployee :sysReviewEmployee.getWorkTimeStamp()){
                SysEmployeeWorktime employeeWorktime = new SysEmployeeWorktime();
                employeeWorktime.setEmployeeId(sysEmployee.getEmployeeId());
                employeeWorktime.setStartTime(reviewEmployee.getStartTime());
                employeeWorktime.setEndTime(reviewEmployee.getEndTime());
                employeeWorktime.setReviewId(sysReviewEmployee.getReviewId());
                employeeWorktime.setReviewEmployeeId(sysReviewEmployee.getReviewEmployeeId());
                List<SysEmployeeWorktime> listWorktime = sysEmployeeWorktimeService.selectSysEmployeeWorktimeExists(employeeWorktime);
                if (listWorktime.size() > 0){
                    throw new ServiceException("该雇工在这时间段已经安排了工作～");
                }
                sysEmployeeWorktimeService.insertSysEmployeeWorktime(employeeWorktime);
            }
        }

        return toAjax(sysReviewEmployeeService.updateSysReviewEmployee(sysReviewEmployee));
    }

    /**
     * 删除雇工实际工作内容记录
     */
    @Log(title = "雇工实际工作内容记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reviewEmployeeIds}")
    @Transactional
    public AjaxResult remove(@PathVariable Long[] reviewEmployeeIds)
    {
        sysEmployeeWorktimeService.deleteSysEmployeeWorktimeByReviewEmployeeIds(reviewEmployeeIds);
        return toAjax(sysReviewEmployeeService.deleteSysReviewEmployeeByReviewEmployeeIds(reviewEmployeeIds));
    }
}
