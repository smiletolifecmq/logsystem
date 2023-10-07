package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.*;
import com.kcylog.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 结算单Controller
 * 
 * @author ruoyi
 * @date 2023-07-12
 */
@RestController
@RequestMapping("/system/settlementSub")
public class SysReviewSubSettlementController extends BaseController
{
    final int PassStatus = 2;
    final int NoPassStatus = 3;
    @Autowired
    private ISysReviewSubSettlementService sysReviewSettlementService;

    @Autowired
    private ISysSettlementAssociateService sysSettlementAssociateService;

    @Autowired
    private ISysReviewSubService sysReviewService;

    /**
     * 查询结算单列表
     */
    @PreAuthorize("@ss.hasPermi('system:settlementSub:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysReviewSubSettlement sysReviewSettlement)
    {
        startPage();
        List<SysReviewSubSettlement> list = sysReviewSettlementService.selectSysReviewSubSettlementList(sysReviewSettlement);
        return getDataTable(list);
    }

    /**
     * 导出结算单列表
     */
    @Log(title = "结算单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysReviewSubSettlement sysReviewSettlement)
    {
        List<SysReviewSubSettlement> list = sysReviewSettlementService.selectSysReviewSubSettlementList(sysReviewSettlement);
        ExcelUtil<SysReviewSubSettlement> util = new ExcelUtil<SysReviewSubSettlement>(SysReviewSubSettlement.class);
        util.exportExcel(response, list, "结算单数据");
    }

    /**
     * 获取结算单详细信息
     */
    @GetMapping(value = "/{settlementId}")
    public AjaxResult getInfo(@PathVariable("settlementId") Long settlementId)
    {
        return success(sysReviewSettlementService.selectSysReviewSubSettlementBySettlementId(settlementId));
    }

    /**
     * 新增结算单
     */
    @Log(title = "结算单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysReviewSubSettlement sysReviewSettlement)
    {
        return toAjax(sysReviewSettlementService.insertSysReviewSubSettlement(sysReviewSettlement));
    }

    /**
     * 修改结算单
     */
    @Log(title = "结算单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysReviewSubSettlement sysReviewSettlement)
    {
        return toAjax(sysReviewSettlementService.updateSysReviewSubSettlement(sysReviewSettlement));
    }

    /**
     * 删除结算单
     */
    @Log(title = "结算单", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{settlementIds}")
    public AjaxResult remove(@PathVariable Long[] settlementIds)
    {
        sysReviewSettlementService.deleteSysReviewSubSettlementBySettlementIds(settlementIds);
        SysSettlementAssociate settlementAssociate = new SysSettlementAssociate();
        SysReviewSub review = new SysReviewSub();
        for (Long settlementId:settlementIds){
            settlementAssociate.setSettlementId(settlementId);
            List<SysSettlementAssociate> settlementAssociateList = sysSettlementAssociateService.selectSysSettlementAssociateList(settlementAssociate);
            for (SysSettlementAssociate settlement:settlementAssociateList){
                review.setReviewId(settlement.getReviewId());
                review.setStatus((long)this.PassStatus);
                sysReviewService.setSysReviewSubStatusByReviewId(review);
            }
            sysSettlementAssociateService.deleteSysSettlementAssociateBySettlementId(settlementId);
        }
        return toAjax(1);
    }

    @Log(title = "结算单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/confirm_settlement/{settlementId}")
    public AjaxResult confirmSettlement(@PathVariable Long settlementId)
    {
        return toAjax(sysReviewSettlementService.updateIsSettlementBySettlementId(settlementId));
    }
}
