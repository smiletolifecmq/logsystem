package com.kcylog.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.system.domain.SysSubcontract;
import com.kcylog.system.service.ISysSubcontractService;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.common.core.page.TableDataInfo;

/**
 * 分包Controller
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@RestController
@RequestMapping("/system/subcontract")
public class SysSubcontractController extends BaseController
{
    @Autowired
    private ISysSubcontractService sysSubcontractService;

    /**
     * 查询分包列表
     */
    @PreAuthorize("@ss.hasPermi('system:subcontract:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSubcontract sysSubcontract)
    {
        startPage();
        List<SysSubcontract> list = sysSubcontractService.selectSysSubcontractList(sysSubcontract);
        return getDataTable(list);
    }

    /**
     * 导出分包列表
     */
    @PreAuthorize("@ss.hasPermi('system:subcontract:export')")
    @Log(title = "分包", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSubcontract sysSubcontract)
    {
        List<SysSubcontract> list = sysSubcontractService.selectSysSubcontractList(sysSubcontract);
        ExcelUtil<SysSubcontract> util = new ExcelUtil<SysSubcontract>(SysSubcontract.class);
        util.exportExcel(response, list, "分包数据");
    }

    /**
     * 获取分包详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:subcontract:query')")
    @GetMapping(value = "/{subcontractId}")
    public AjaxResult getInfo(@PathVariable("subcontractId") Long subcontractId)
    {
        return success(sysSubcontractService.selectSysSubcontractBySubcontractId(subcontractId));
    }

    /**
     * 新增分包
     */
    @PreAuthorize("@ss.hasPermi('system:subcontract:add')")
    @Log(title = "分包", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSubcontract sysSubcontract)
    {
        return toAjax(sysSubcontractService.insertSysSubcontract(sysSubcontract));
    }

    /**
     * 修改分包
     */
    @PreAuthorize("@ss.hasPermi('system:subcontract:edit')")
    @Log(title = "分包", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSubcontract sysSubcontract)
    {
        return toAjax(sysSubcontractService.updateSysSubcontract(sysSubcontract));
    }

    /**
     * 删除分包
     */
    @PreAuthorize("@ss.hasPermi('system:subcontract:remove')")
    @Log(title = "分包", businessType = BusinessType.DELETE)
	@DeleteMapping("/{subcontractIds}")
    public AjaxResult remove(@PathVariable Long[] subcontractIds)
    {
        return toAjax(sysSubcontractService.deleteSysSubcontractBySubcontractIds(subcontractIds));
    }
}
