package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysReview;
import com.kcylog.system.domain.SysReviewSettlement;
import com.kcylog.system.domain.SysSettlementAssociate;
import com.kcylog.system.service.ISysReviewService;
import com.kcylog.system.service.ISysReviewSettlementService;
import com.kcylog.system.service.ISysSettlementAssociateService;
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
@RequestMapping("/system/settlement")
public class SysReviewSettlementController extends BaseController
{
    final int PassStatus = 2;
    final int NoPassStatus = 3;
    @Autowired
    private ISysReviewSettlementService sysReviewSettlementService;

    @Autowired
    private ISysSettlementAssociateService sysSettlementAssociateService;

    @Autowired
    private ISysReviewService sysReviewService;

    /**
     * 查询结算单列表
     */
    @PreAuthorize("@ss.hasPermi('system:settlement:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysReviewSettlement sysReviewSettlement)
    {
        startPage();
        List<SysReviewSettlement> list = sysReviewSettlementService.selectSysReviewSettlementList(sysReviewSettlement);
        return getDataTable(list);
    }

    /**
     * 导出结算单列表
     */
    @Log(title = "结算单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysReviewSettlement sysReviewSettlement)
    {
        List<SysReviewSettlement> list = sysReviewSettlementService.selectSysReviewSettlementList(sysReviewSettlement);
        ExcelUtil<SysReviewSettlement> util = new ExcelUtil<SysReviewSettlement>(SysReviewSettlement.class);
        util.exportExcel(response, list, "结算单数据");
    }

    /**
     * 获取结算单详细信息
     */
    @GetMapping(value = "/{settlementId}")
    public AjaxResult getInfo(@PathVariable("settlementId") Long settlementId)
    {
        return success(sysReviewSettlementService.selectSysReviewSettlementBySettlementId(settlementId));
    }

    /**
     * 新增结算单
     */
    @Log(title = "结算单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysReviewSettlement sysReviewSettlement)
    {
        return toAjax(sysReviewSettlementService.insertSysReviewSettlement(sysReviewSettlement));
    }

    /**
     * 修改结算单
     */
    @Log(title = "结算单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysReviewSettlement sysReviewSettlement)
    {
        return toAjax(sysReviewSettlementService.updateSysReviewSettlement(sysReviewSettlement));
    }

    /**
     * 删除结算单
     */
    @Log(title = "结算单", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{settlementIds}")
    public AjaxResult remove(@PathVariable Long[] settlementIds)
    {
        sysReviewSettlementService.deleteSysReviewSettlementBySettlementIds(settlementIds);
        SysSettlementAssociate settlementAssociate = new SysSettlementAssociate();
        SysReview review = new SysReview();
        for (Long settlementId:settlementIds){
            settlementAssociate.setSettlementId(settlementId);
            List<SysSettlementAssociate> settlementAssociateList = sysSettlementAssociateService.selectSysSettlementAssociateList(settlementAssociate);
            for (SysSettlementAssociate settlement:settlementAssociateList){
                review.setReviewId(settlement.getReviewId());
                review.setStatus((long)this.PassStatus);
                sysReviewService.setSysReviewStatusByReviewId(review);
            }
        }
        return toAjax(1);
    }
}
