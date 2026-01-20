package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.ViewBcyj;
import com.kcylog.system.service.IViewBcyjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2026-01-19
 */
@RestController
@RequestMapping("/system/bcyj")
public class ViewBcyjController extends BaseController
{
    @Autowired
    private IViewBcyjService viewBcyjService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:bcyj:list')")
    @GetMapping("/list")
    public TableDataInfo list(ViewBcyj viewBcyj)
    {
        startPage();
        List<ViewBcyj> list = viewBcyjService.selectViewBcyjList(viewBcyj);
        for(ViewBcyj obj : list){
            obj.setIssjcq((long)0);
            obj.setIszgcq((long)0);
            obj.setSjmscq((long)0);
            obj.setZgmscq((long)0);
            // 收件时间大于收件截止时间
            if (obj.getReceiveCutoffTime() != null && obj.getSjsj() != null){
                if (obj.getSjsj().after(obj.getReceiveCutoffTime())) {
                    obj.setIssjcq((long)1);
                }
            }
            // 当前时间 > receiveCutoffTime（已超期）
            if (obj.getReceiveCutoffTime() != null && obj.getSjsj() == null){
                Date now = new Date();
                if (now.after(obj.getReceiveCutoffTime())) {
                    obj.setIssjcq((long)1);
                }

                long days = ChronoUnit.DAYS.between(
                        new Date().toInstant(),
                        obj.getReceiveCutoffTime().toInstant()
                );

                if (days >= 0 && days <= 2) {
                    obj.setSjmscq((long)1);
                }
            }
            // 退回整改日期大于整改截止时间
            if (obj.getYssj() != null && obj.getRectifyCutoffTime() != null){
                if (obj.getYssj().after(obj.getRectifyCutoffTime())) {
                    obj.setIszgcq((long)1);
                }
            }
            // 当前时间 > rectifyCutoffTime（已超期）
            if (obj.getRectifyCutoffTime() != null && obj.getYssj() == null){
                Date now = new Date();
                if (now.after(obj.getRectifyCutoffTime())) {
                    obj.setIszgcq((long)1);
                }

                long days = ChronoUnit.DAYS.between(
                        new Date().toInstant(),
                        obj.getRectifyCutoffTime().toInstant()
                );

                if (days >= 0 && days <= 2) {
                    obj.setZgmscq((long)1);
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:bcyj:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ViewBcyj viewBcyj)
    {
        List<ViewBcyj> list = viewBcyjService.selectViewBcyjList(viewBcyj);
        ExcelUtil<ViewBcyj> util = new ExcelUtil<ViewBcyj>(ViewBcyj.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:bcyj:query')")
    @GetMapping(value = "/{projectCode}")
    public AjaxResult getInfo(@PathVariable("projectCode") String projectCode)
    {
        return success(viewBcyjService.selectViewBcyjByProjectCode(projectCode));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:bcyj:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ViewBcyj viewBcyj)
    {
        return toAjax(viewBcyjService.insertViewBcyj(viewBcyj));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:bcyj:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ViewBcyj viewBcyj)
    {
        return toAjax(viewBcyjService.updateViewBcyj(viewBcyj));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:bcyj:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projectCodes}")
    public AjaxResult remove(@PathVariable String[] projectCodes)
    {
        return toAjax(viewBcyjService.deleteViewBcyjByProjectCodes(projectCodes));
    }
}
