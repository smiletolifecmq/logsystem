package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysContract;
import com.kcylog.system.service.ISysContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 合同管理Controller
 * 
 * @author ruoyi
 * @date 2025-03-26
 */
@RestController
@RequestMapping("/system/contract")
public class SysContractController extends BaseController
{
    @Autowired
    private ISysContractService sysContractService;

    /**
     * 查询合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysContract sysContract)
    {
        startPage();
        List<SysContract> list = sysContractService.selectSysContractList(sysContract);
        Date today = new Date(); // 当前日期
        for (SysContract obj : list){
            if(obj.getSsdqsj() != null){
                long diffInMillies = Math.abs(obj.getSsdqsj().getTime() - today.getTime()); // 计算毫秒差
                long daysBetween = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                obj.setDay(daysBetween);
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:contract:export')")
    @Log(title = "合同管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysContract sysContract)
    {
        List<SysContract> list = sysContractService.selectSysContractList(sysContract);
        int num = 0;
        for (SysContract obj : list){
            num ++;
            obj.setNumber(num);
        }
        ExcelUtil<SysContract> util = new ExcelUtil<SysContract>(SysContract.class);
        util.exportExcel(response, list, "合同管理数据");
    }

    /**
     * 获取合同管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:contract:query')")
    @GetMapping(value = "/{conId}")
    public AjaxResult getInfo(@PathVariable("conId") Long conId)
    {
        return success(sysContractService.selectSysContractByConId(conId));
    }

    /**
     * 新增合同管理
     */
    @PreAuthorize("@ss.hasPermi('system:contract:add')")
    @Log(title = "合同管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysContract sysContract)
    {
        sysContract.setFzr(getUsername());
        return toAjax(sysContractService.insertSysContract(sysContract));
    }

    /**
     * 修改合同管理
     */
    @PreAuthorize("@ss.hasPermi('system:contract:edit')")
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysContract sysContract)
    {
        if (sysContract.getYdsj() != null && sysContract.getFhsj() == null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sysContract.getYdsj());

            // 加三年
            calendar.add(Calendar.YEAR, 3);
            // 减一天
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            sysContract.setSsdqsj(calendar.getTime());
        }else if (sysContract.getFhsj() != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sysContract.getFhsj());
            // 加三年
            calendar.add(Calendar.YEAR, 3);
            // 减一天
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            sysContract.setSsdqsj(calendar.getTime());
        }
        return toAjax(sysContractService.updateSysContract(sysContract));
    }

    /**
     * 删除合同管理
     */
    @PreAuthorize("@ss.hasPermi('system:contract:remove')")
    @Log(title = "合同管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{conIds}")
    public AjaxResult remove(@PathVariable Long[] conIds)
    {
        return toAjax(sysContractService.deleteSysContractByConIds(conIds));
    }
}
