package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SjInfo;
import com.kcylog.system.domain.SysSending;
import com.kcylog.system.service.ISysSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 送件Controller
 * 
 * @author ruoyi
 * @date 2025-10-29
 */
@RestController
@RequestMapping("/system/sending")
public class SysSendingController extends BaseController
{
    @Autowired
    private ISysSendingService sysSendingService;

    /**
     * 查询送件列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysSending sysSending)
    {
        startPage();
        List<SysSending> list = sysSendingService.selectSysSendingList(sysSending);
        return getDataTable(list);
    }

    /**
     * 导出送件列表
     */
    @Log(title = "送件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSending sysSending)
    {
        List<SysSending> list = sysSendingService.selectSysSendingList(sysSending);
        ExcelUtil<SysSending> util = new ExcelUtil<SysSending>(SysSending.class);
        util.exportExcel(response, list, "送件数据");
    }

    /**
     * 获取送件详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysSendingService.selectSysSendingById(id));
    }

    /**
     * 新增送件
     */
    @Log(title = "送件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSending sysSending)
    {
        if (sysSending.getSjInfo() == null || sysSending.getSjInfo().size() == 0){
            return error("请填写送件信息");
        }
        SysSending newObj = new SysSending();
        newObj.setProjectId(sysSending.getProjectId());
        newObj.setSjsj(sysSending.getSjsj());
        newObj.setBz(sysSending.getBz());
        newObj.setLxfs("");
        for (SjInfo obj : sysSending.getSjInfo()){
            newObj.setLxfs(newObj.getLxfs() + obj.getLx() + "：" + obj.getFs() + "；");
        }
        return toAjax(sysSendingService.insertSysSending(newObj));
    }

    /**
     * 修改送件
     */
    @Log(title = "送件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSending sysSending)
    {
        return toAjax(sysSendingService.updateSysSending(sysSending));
    }

    /**
     * 删除送件
     */
    @Log(title = "送件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysSendingService.deleteSysSendingByIds(ids));
    }
}
