package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysManagementResponsibilities;
import com.kcylog.system.domain.SysManagementResponsibilitiesInfo;
import com.kcylog.system.service.ISysManagementResponsibilitiesInfoService;
import com.kcylog.system.service.ISysManagementResponsibilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * 责权发生制开票明细Controller
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
@RestController
@RequestMapping("/system/responsibilitiesInfo")
public class SysManagementResponsibilitiesInfoController extends BaseController
{
    @Autowired
    private ISysManagementResponsibilitiesInfoService sysManagementResponsibilitiesInfoService;

    @Autowired
    private ISysManagementResponsibilitiesService sysManagementResponsibilitiesService;

    /**
     * 查询责权发生制开票明细列表
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilitiesInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysManagementResponsibilitiesInfo sysManagementResponsibilitiesInfo)
    {
        startPage();
        List<SysManagementResponsibilitiesInfo> list = sysManagementResponsibilitiesInfoService.selectSysManagementResponsibilitiesInfoList(sysManagementResponsibilitiesInfo);
        return getDataTable(list);
    }

    /**
     * 导出责权发生制开票明细列表
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilitiesInfo:export')")
    @Log(title = "责权发生制开票明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysManagementResponsibilitiesInfo sysManagementResponsibilitiesInfo)
    {
        List<SysManagementResponsibilitiesInfo> list = sysManagementResponsibilitiesInfoService.selectSysManagementResponsibilitiesInfoList(sysManagementResponsibilitiesInfo);
        int num = 0;
        for (SysManagementResponsibilitiesInfo obj : list){
            num ++;
            obj.setNum(num);
        }
        ExcelUtil<SysManagementResponsibilitiesInfo> util = new ExcelUtil<SysManagementResponsibilitiesInfo>(SysManagementResponsibilitiesInfo.class);
        util.exportExcel(response, list, "责权发生制开票明细数据");
    }

    /**
     * 获取责权发生制开票明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilitiesInfo:query')")
    @GetMapping(value = "/{infoId}")
    public AjaxResult getInfo(@PathVariable("infoId") Long infoId)
    {
        return success(sysManagementResponsibilitiesInfoService.selectSysManagementResponsibilitiesInfoByInfoId(infoId));
    }

    /**
     * 新增责权发生制开票明细
     */
    @Log(title = "责权发生制开票明细", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody SysManagementResponsibilitiesInfo sysManagementResponsibilitiesInfo)
    {
        SysManagementResponsibilities responsibilities = sysManagementResponsibilitiesService.selectSysManagementResponsibilitiesByZqId(sysManagementResponsibilitiesInfo.getZqId());
        BigDecimal money = responsibilities.getZqKkpje().subtract(sysManagementResponsibilitiesInfo.getInfoMoney());
        if (money.compareTo(BigDecimal.ZERO) < 0) {
            return error("可开票金额不足以用于开票");
        }
        SysManagementResponsibilities newObj = new SysManagementResponsibilities();
        newObj.setZqId(sysManagementResponsibilitiesInfo.getZqId());
        newObj.setZqKkpje(money);
        sysManagementResponsibilitiesService.updateSysManagementResponsibilities(newObj);
        return toAjax(sysManagementResponsibilitiesInfoService.insertSysManagementResponsibilitiesInfo(sysManagementResponsibilitiesInfo));
    }

    /**
     * 修改责权发生制开票明细
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilitiesInfo:edit')")
    @Log(title = "责权发生制开票明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysManagementResponsibilitiesInfo sysManagementResponsibilitiesInfo)
    {
        return toAjax(sysManagementResponsibilitiesInfoService.updateSysManagementResponsibilitiesInfo(sysManagementResponsibilitiesInfo));
    }

    /**
     * 删除责权发生制开票明细
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilitiesInfo:remove')")
    @Log(title = "责权发生制开票明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds)
    {
        return toAjax(sysManagementResponsibilitiesInfoService.deleteSysManagementResponsibilitiesInfoByInfoIds(infoIds));
    }
}
