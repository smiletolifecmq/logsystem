package com.kcylog.web.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysSubcontract;
import com.kcylog.system.domain.SysSubcontractProcess;
import com.kcylog.system.domain.SysSubcontractProcessConfigInfo;
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
}
