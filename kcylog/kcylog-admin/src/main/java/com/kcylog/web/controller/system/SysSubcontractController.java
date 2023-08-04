package com.kcylog.web.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.DateUtils;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.*;
import com.kcylog.system.service.ISysSubcontractProcessConfigInfoService;
import com.kcylog.system.service.ISysSubcontractProcessService;
import com.kcylog.system.service.ISysSubcontractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
    final int PassStatus = 2;
    final int NoPassStatus = 3;

    @Autowired
    private ISysSubcontractService sysSubcontractService;

    @Autowired
    private ISysSubcontractProcessConfigInfoService sysSubcontractProcessConfigInfoService;

    @Autowired
    private ISysSubcontractProcessService sysSubcontractProcessService;

    /**
     * 查询分包列表
     */
    @PreAuthorize("@ss.hasPermi('system:subcontract:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSubcontract sysSubcontract) throws JsonProcessingException {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysSubcontract.setUserId(userId);
        List<SysSubcontract> list = sysSubcontractService.selectSysSubcontractList(sysSubcontract);
        for(SysSubcontract subcontract:list){
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> cooperationUnitJson = objectMapper.readValue(subcontract.getCooperationUnit(), new TypeReference<List<String>>(){});
            subcontract.setCooperationUnitJson(cooperationUnitJson);
        }
        return getDataTable(list);
    }

    /**
     * 导出分包列表
     */
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
    @GetMapping(value = "/{subcontractId}")
    public AjaxResult getInfo(@PathVariable("subcontractId") Long subcontractId) throws JsonProcessingException {
        SysSubcontract sysSubcontract =  sysSubcontractService.selectSysSubcontractBySubcontractId(subcontractId);
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> cooperationUnitJson = objectMapper.readValue(sysSubcontract.getCooperationUnit(), new TypeReference<List<String>>(){});
        sysSubcontract.setCooperationUnitJson(cooperationUnitJson);
        return success(sysSubcontract);
    }

    /**
     * 新增分包
     */
    @Log(title = "分包", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysSubcontract sysSubcontract) throws JsonProcessingException {
        Long userId = SecurityUtils.getUserId();
        Long deptId = SecurityUtils.getDeptId();
        sysSubcontract.setUserId(userId);
        sysSubcontract.setDeptId(deptId);
        ObjectMapper mapper = new ObjectMapper();
        String cooperationUnit = mapper.writeValueAsString(sysSubcontract.getCooperationUnitJson());
        sysSubcontract.setCooperationUnit(cooperationUnit);
        sysSubcontractService.insertSysSubcontract(sysSubcontract);
        List<SysSubcontractProcessConfigInfo> list = sysSubcontractProcessConfigInfoService.selectSysSubcontractProcessConfigInfoListByDeptId(deptId);

        List<SysSubcontractProcess> subcontractProcess = new ArrayList<>();
        for (SysSubcontractProcessConfigInfo configInfo : list) {
            SysSubcontractProcess subcontract = new SysSubcontractProcess();
            subcontract.setSubcontractId((sysSubcontract.getSubcontractId()));
            subcontract.setUserId(configInfo.getUserId());
            subcontract.setStatus((long)0);
            subcontract.setNode(Long.parseLong(configInfo.getNode()));
            subcontractProcess.add(subcontract);
        }
        int result = sysSubcontractProcessService.insertSysSubcontractProcessBatch(subcontractProcess);
        return toAjax(result);
    }

    /**
     * 修改分包
     */
    @Log(title = "分包", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSubcontract sysSubcontract) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String cooperationUnit = mapper.writeValueAsString(sysSubcontract.getCooperationUnitJson());
        sysSubcontract.setCooperationUnit(cooperationUnit);
        return toAjax(sysSubcontractService.updateSysSubcontract(sysSubcontract));
    }

    /**
     * 删除分包
     */
    @Log(title = "分包", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{subcontractIds}")
    public AjaxResult remove(@PathVariable Long[] subcontractIds)
    {
        sysSubcontractService.deleteSysSubcontractBySubcontractIds(subcontractIds);
        int result = sysSubcontractProcessService.deleteSysSubcontractProcessByReviewIds(subcontractIds);
        return toAjax(result);
    }

    /**
     * 发起审核单审核
     */
    @Log(title = "分包", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_status")
    public AjaxResult setStatus(@RequestBody SysSubcontract sysSubcontract)
    {
        //设置审核单为进行中
        sysSubcontractService.setSysSubcontractStatusBySubcontractId(sysSubcontract);
        //先将审核单流程状态全部设置成未开始
        sysSubcontractProcessService.setStatusNotStartBySubcontractId(sysSubcontract.getSubcontractId());
        //将审核单第一个流程设置成进行中
        int result = sysSubcontractProcessService.setStatusBySubcontractIdFirst(sysSubcontract.getSubcontractId());
        return toAjax(result);
    }

    @GetMapping("/upcomingListReview")
    public TableDataInfo upcomingListReview(SysSubcontract sysSubcontract)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysSubcontract.setUserId(userId);
        List<SysSubcontract> list = sysSubcontractService.selectSysUpcomingSubcontractList(sysSubcontract);
        return getDataTable(list);
    }

    /**
     * 修改审核是否通过
     */
    @Log(title = "分包", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_review_process")
    public AjaxResult setSubcontractProcessStatus(@RequestBody SysSubcontractProcess sysSubcontractProcess)
    {
        //修改审核流程中流程信息
        Long userId = SecurityUtils.getUserId();
        sysSubcontractProcess.setUserId(userId);
        sysSubcontractProcess.setReviewTime(DateUtils.getNowDate());
        sysSubcontractProcessService.setStatusByUserIdAndSubcontractId(sysSubcontractProcess);

        SysSubcontract subcontract = new SysSubcontract();
        subcontract.setSubcontractId(sysSubcontractProcess.getSubcontractId());
        if (sysSubcontractProcess.getStatus() == (long)this.PassStatus){
            //审核通过
            //获取审核单审核流程
            List<SysSubcontractProcess> list = sysSubcontractProcessService.selectSysSubcontractProcessList(sysSubcontractProcess);
            int passNum = 0;
            for (SysSubcontractProcess obj : list) {
                if (obj.getStatus() == (long)this.PassStatus){
                    passNum ++;
                }
            }
            //判断流程是否都已经通过了
            if (passNum == list.size()){
                //审核单通过
                subcontract.setStatus((long)this.PassStatus);
                sysSubcontractService.setSysSubcontractStatusBySubcontractId(subcontract);
            }  else {
                //审核单流程没有全部通过，进入下一个流程审核
                sysSubcontractProcessService.setNextStatusBySubcontractId(sysSubcontractProcess.getSubcontractId());
            }

        }else {
            //审核不通过
            subcontract.setStatus((long)this.NoPassStatus);
            sysSubcontractService.setSysReviewStatusBySubcontractId(subcontract);
        }
        return toAjax(1);
    }

    /**
     * 批量修改审核单
     */
    @Log(title = "分包", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_batch_review_pass/{subcontractIds}")
    public AjaxResult setBatchReviewPass(@PathVariable Long[] subcontractIds)
    {
        //修改审核流程中流程信息
        Long userId = SecurityUtils.getUserId();
        SysSubcontractProcess sysSubcontractProcess = new SysSubcontractProcess();
        sysSubcontractProcess.setUserId(userId);
        sysSubcontractProcess.setReviewTime(DateUtils.getNowDate());

        for (Long subcontractId:subcontractIds){
            sysSubcontractProcess.setSubcontractId(subcontractId);
            sysSubcontractProcess.setStatus((long)this.PassStatus);
            sysSubcontractProcessService.setStatusByUserIdAndSubcontractId(sysSubcontractProcess);
            SysSubcontract subcontract = new SysSubcontract();
            subcontract.setSubcontractId(sysSubcontractProcess.getSubcontractId());
            //审核通过
            //获取审核单审核流程
            List<SysSubcontractProcess> list = sysSubcontractProcessService.selectSysSubcontractProcessList(sysSubcontractProcess);
            int passNum = 0;
            for (SysSubcontractProcess obj : list) {
                if (obj.getStatus() == (long)this.PassStatus){
                    passNum ++;
                }
            }
            //判断流程是否都已经通过了
            if (passNum == list.size()){
                //审核单通过
                subcontract.setStatus((long)this.PassStatus);
                sysSubcontractService.setSysReviewStatusBySubcontractId(subcontract);
            } else {
                //审核单流程没有全部通过，进入下一个流程审核
                sysSubcontractProcessService.setNextStatusBySubcontractId(sysSubcontractProcess.getSubcontractId());
            }
        }

        return toAjax(1);
    }

    @GetMapping("/doneListReview")
    public TableDataInfo doneReview(SysSubcontract sysSubcontract)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysSubcontract.setUserId(userId);
        List<SysSubcontract> list = sysSubcontractService.selectDoneReviewList(sysSubcontract);
        return getDataTable(list);
    }

    @GetMapping("/completedListReview")
    public TableDataInfo completedReview(SysSubcontract sysSubcontract)
    {
        startPage();
        List<SysSubcontract> list = sysSubcontractService.selectCompletedReviewList(sysSubcontract);
        return getDataTable(list);
    }
}
