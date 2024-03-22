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
import com.kcylog.system.domain.*;
import com.kcylog.system.service.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 审核单Controller
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
@RestController
@RequestMapping("/system/reviewSub")
public class SysReviewSubController extends BaseController
{
    final int PassStatus = 2;
    final int NoPassStatus = 3;

    final Integer HiredWorkerType = 1;

    @Autowired
    private ISysReviewSubService sysReviewSubService;
    @Autowired
    private ISysProcessConfigInfoService sysProcessConfigInfoService;

    @Autowired
    private ISysReviewSubProcessService sysReviewSubProcessService;

    @Autowired
    private ISysReviewSubEmployeeService sysReviewEmployeeService;

    @Autowired
    private ISysReviewSubSettlementService sysReviewSettlementService;

    @Autowired
    private ISysSettlementAssociateService sysSettlementAssociateService;

    @Autowired
    private ISysProjectRelationService sysProjectRelationService;

    @Autowired
    private ISysEmployeeWorktimeService sysEmployeeWorktimeService;

    @Autowired
    private ISysProjectService sysProjectService;


    /**
     * 查询审核单列表
     */
    @PreAuthorize("@ss.hasPermi('system:reviewSub:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysReviewSub sysReviewSub) throws JsonProcessingException {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysReviewSub.setUserId(userId);
        List<SysReviewSub> list = sysReviewSubService.selectSysReviewSubList(sysReviewSub);

        for(SysReviewSub subcontract:list){
            if (subcontract.getCooperationUnit() != "" && subcontract.getCooperationUnit() != null){
                ObjectMapper objectMapper = new ObjectMapper();
                List<String> cooperationUnitJson = objectMapper.readValue(subcontract.getCooperationUnit(), new TypeReference<List<String>>(){});
                subcontract.setCooperationUnitJson(cooperationUnitJson);
            }
        }

        return getDataTable(list);
    }

    /**
     * 导出审核单列表
     */
    @Log(title = "审核单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysReviewSub sysReviewSub)
    {
        List<SysReviewSub> list = sysReviewSubService.selectSysReviewSubListBySettlementId(sysReviewSub);
        int num = 0;
        for (SysReviewSub sysReviewTemp:list){
            num ++;
            float day = 0;
            Map<String, Integer> map = new HashMap<>();
            String hiredWorkerName = "";
            String workload = "";
            List<SysReviewSubEmployee> reviewEmployee = sysReviewEmployeeService.selectSysReviewSubEmployeeByReviewId(sysReviewTemp.getReviewId());
            sysReviewTemp.setExportSerialNumber(num);
            for (SysReviewSubEmployee employee:reviewEmployee){
                hiredWorkerName = hiredWorkerName + (employee.getName() + "、");
                day += employee.getWorkDay();
                String workloadTemp = employee.getWorkTime();
                String[] strArray = workloadTemp.split(";");
                for (String dayTime:strArray){
                    if (dayTime != ""){
                        map.put(dayTime, 1);
                    }
                }
            }
            if (hiredWorkerName != ""){
                hiredWorkerName = hiredWorkerName.substring(0, hiredWorkerName.length() - 1);
            }
            if (reviewEmployee.size() > 0){
                workload += (reviewEmployee.size() + "人" + day + "天");
            }
            if (map.size() > 0){
                workload += "(";
            }
            for (String key : map.keySet()) {
                workload += (key + "、");
            }
            if (map.size() > 0){
                workload = workload.substring(0, workload.length() - 1);
                workload += ")";
            }
            sysReviewTemp.setHiredWorkerName(hiredWorkerName);
            sysReviewTemp.setWorkload(workload);
        }

        Workbook workbook = new XSSFWorkbook();
        CellStyle wrapCellStyle = workbook.createCellStyle();
        Sheet sheet = this.integrateExcel(workbook, wrapCellStyle);

        int rowIndex = 2; // 从第二行开始，留给行头
        for (SysReviewSub sysReviewTemp : list) {
            Row row = sheet.createRow(rowIndex);

            Cell cell1 = row.createCell(0);
            cell1.setCellValue(sysReviewTemp.getExportSerialNumber());
            cell1.setCellStyle(wrapCellStyle);

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(sysReviewTemp.getSerialNum());
            cell2.setCellStyle(wrapCellStyle);

            Cell cell3 = row.createCell(2);
            cell3.setCellValue(sysReviewTemp.getProjectName());
            cell3.setCellStyle(wrapCellStyle);

            Cell cell4 = row.createCell(3);
            cell4.setCellValue(sysReviewTemp.getWorkload());
            cell4.setCellStyle(wrapCellStyle);

            Cell cell5 = row.createCell(4);
            cell5.setCellValue(sysReviewTemp.getHiredWorkerName());
            cell5.setCellStyle(wrapCellStyle);

            rowIndex++;
        }

        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取审核单详细信息
     */
    @GetMapping(value = "/{reviewId}")
    public AjaxResult getInfo(@PathVariable("reviewId") String reviewId) throws JsonProcessingException {
        SysReviewSub review = sysReviewSubService.selectSysReviewSubByReviewId(reviewId);

        if (review.getCooperationUnit() != null && review.getCooperationUnit() != ""){
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> cooperationUnitJson = objectMapper.readValue(review.getCooperationUnit(), new TypeReference<List<String>>(){});
            review.setCooperationUnitJson(cooperationUnitJson);
        }

        if (review.getProjectRelation() != null){
            review.setProjectId(review.getProjectRelation().getProjectId());
        }
        return success(review);
    }

    /**
     * 新增审核单
     */
    @Log(title = "审核单", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysReviewSub sysReviewSub) throws JsonProcessingException {
        Long userId = SecurityUtils.getUserId();
        Long deptId = SecurityUtils.getDeptId();
        sysReviewSub.setUserId(userId);
        sysReviewSub.setDeptId(deptId);


        if (sysReviewSub.getCooperationUnitJson() != null ){
            ObjectMapper mapper = new ObjectMapper();
            String cooperationUnit = mapper.writeValueAsString(sysReviewSub.getCooperationUnitJson());
            sysReviewSub.setCooperationUnit(cooperationUnit);
        }
        sysReviewSubService.insertSysReviewSub(sysReviewSub);
        List<SysProcessConfigInfo> list = sysProcessConfigInfoService.selectSysProcessConfigInfoListByDeptId(deptId);
        List<SysReviewSubProcess> reviewProcess = new ArrayList<>();
        for (SysProcessConfigInfo configInfo : list) {
            SysReviewSubProcess review = new SysReviewSubProcess();
            review.setReviewId((sysReviewSub.getReviewId()));
            review.setUserId(configInfo.getUserId());
            review.setStatus((long)0);
            review.setNode(Long.parseLong(configInfo.getNode()));
            reviewProcess.add(review);
        }

        SysProject sysProject = new SysProject();
        sysProject.setProjectId(sysReviewSub.getProjectId());
        sysProject.setIsShow(1);
        sysProjectService.updateIsShowByProjectId(sysProject);

        sysReviewSubProcessService.insertSysReviewSubProcessBatch(reviewProcess);
        SysProjectRelation projectRelation = new SysProjectRelation();
        projectRelation.setProjectId(sysReviewSub.getProjectId());
        projectRelation.setReviewType(HiredWorkerType);
        projectRelation.setReviewId(sysReviewSub.getReviewId());
        sysProjectRelationService.insertSysProjectRelation(projectRelation);
        return success(sysReviewSub.getReviewId().intValue());
    }

    /**
     * 修改审核单
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody SysReviewSub sysReviewSub) throws JsonProcessingException {
        SysProjectRelation projectRelation = new SysProjectRelation();
        projectRelation.setProjectId(sysReviewSub.getProjectId());
        projectRelation.setReviewType(HiredWorkerType);
        projectRelation.setReviewId(sysReviewSub.getReviewId());
        sysProjectRelationService.deleteByReviewId(projectRelation);
        sysProjectRelationService.insertSysProjectRelation(projectRelation);

        if(sysReviewSub.getCooperationUnitJson() != null){
            ObjectMapper mapper = new ObjectMapper();
            String cooperationUnit = mapper.writeValueAsString(sysReviewSub.getCooperationUnitJson());
            sysReviewSub.setCooperationUnit(cooperationUnit);
        }
        sysReviewSub.setStartEdit(0);
        return toAjax(sysReviewSubService.updateSysReviewSub(sysReviewSub));
    }

    /**
     * 删除审核单
     */
    @Log(title = "审核单", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{reviewIds}")
    public AjaxResult remove(@PathVariable String[] reviewIds)
    {
        SysProjectRelation projectRelation = new SysProjectRelation();
        projectRelation.setReviewType(HiredWorkerType);
        projectRelation.setReviewId(Long.parseLong(reviewIds[0]));
        sysProjectRelationService.deleteByReviewId(projectRelation);

        sysReviewSubService.deleteSysReviewSubByReviewIds(reviewIds);
        int result = sysReviewSubProcessService.deleteSysReviewSubProcessByReviewIds(reviewIds);
        return toAjax(result);
    }


    /**
     * 发起审核单审核
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_status")
    public AjaxResult setStatus(@RequestBody SysReviewSub sysReviewSub)
    {
        //设置审核单为进行中
        sysReviewSubService.setSysReviewSubStatusByReviewId(sysReviewSub);
        //先将审核单流程状态全部设置成未开始
        sysReviewSubProcessService.setStatusNotStartByReviewId(sysReviewSub.getReviewId());
        //将审核单第一个流程设置成进行中
        int result = sysReviewSubProcessService.setStatusByReviewIdFirst(sysReviewSub.getReviewId());
        return toAjax(result);
    }

    /**
     * 获取待办审核单
     */
    @GetMapping("/upcomingListReview")
    public TableDataInfo upcomingListReview(SysReviewSub sysReviewSub)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysReviewSub.setUserId(userId);
        List<SysReviewSub> list = sysReviewSubService.selectSysUpcomingReviewList(sysReviewSub);
        List<Long> reviewIds = new ArrayList<>();
        for (SysReviewSub review : list){
            reviewIds.add(review.getReviewId());
        }
        if (reviewIds.size() > 0){
            List<SysReviewSubEmployee> reviewEmployee = sysReviewEmployeeService.selectSysReviewEmployeeByReviewIds(reviewIds);
            Map<Long, BigDecimal> reviewEmployeeCostMap = new HashMap<>();
            for (SysReviewSubEmployee value : reviewEmployee){
                if (reviewEmployeeCostMap.containsKey(value.getReviewId())){
                    reviewEmployeeCostMap.put(value.getReviewId(),reviewEmployeeCostMap.get(value.getReviewId()).add(value.getCost()));
                }else {
                    reviewEmployeeCostMap.put(value.getReviewId(),value.getCost());
                }
            }
            for (SysReviewSub review : list){
                if (reviewEmployeeCostMap.containsKey(review.getReviewId())){
                    review.setGuGongMoney(reviewEmployeeCostMap.get(review.getReviewId()));
                }
            }
        }
        return getDataTable(list);
    }


    /**
     * 修改审核是否通过
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_review_process")
    public AjaxResult setReviewProcessStatus(@RequestBody SysReviewSubProcess sysReviewSubProcess)
    {
        //修改审核流程中流程信息
        Long userId = SecurityUtils.getUserId();
        sysReviewSubProcess.setUserId(userId);
        sysReviewSubProcess.setReviewTime(DateUtils.getNowDate());
        sysReviewSubProcessService.setStatusByUserIdAndReviewId(sysReviewSubProcess);

        SysReviewSub review = new SysReviewSub();
        review.setReviewId(sysReviewSubProcess.getReviewId());
        //修改审核单雇工方式
        sysReviewSubService.updateReviewManType(sysReviewSubProcess);
        if (sysReviewSubProcess.getStatus() == (long)this.PassStatus){
            //审核通过
            //获取审核单审核流程
            List<SysReviewSubProcess> list = sysReviewSubProcessService.selectSysReviewSubProcessList(sysReviewSubProcess);
            int passNum = 0;
            for (SysReviewSubProcess obj : list) {
                if (obj.getStatus() == (long)this.PassStatus){
                    passNum ++;
                }
            }
            //判断流程是否都已经通过了
            if (passNum == list.size()){
                //审核单通过
                SysProject sysProject = new SysProject();
                sysProject.setProjectId(review.getReviewId());
                sysProject.setIsShow(0);
                sysProjectService.updateIsShowByReviewId(sysProject);

                review.setStatus((long)this.PassStatus);
                sysReviewSubService.setSysReviewSubStatusByReviewId(review);
            } else if (passNum == list.size() - 2) {
                //审核到终审的前一个
                review.setFinalSecondStatus(1);
                sysReviewSubService.setSysReviewSubFinalSecondStatusByReviewId(review);
                sysReviewSubProcessService.setNextStatusByReviewId(sysReviewSubProcess.getReviewId());
            } else {
                //审核单流程没有全部通过，进入下一个流程审核
                sysReviewSubProcessService.setNextStatusByReviewId(sysReviewSubProcess.getReviewId());
            }

        }else {
            //审核不通过
            review.setStatus((long)this.NoPassStatus);
            sysReviewSubService.setSysReviewSubStatusByReviewId(review);
        }
        return toAjax(1);
    }

    /**
     * 获取全部已通过的审核单
     */
    @GetMapping("/completedListReview")
    public TableDataInfo completedReview(SysReviewSub sysReviewSub)
    {
        startPage();
        List<SysReviewSub> list = sysReviewSubService.selectCompletedReviewList(sysReviewSub);
        List<Long> reviewIds = new ArrayList<>();
        for (SysReviewSub review : list){
            reviewIds.add(review.getReviewId());
        }
        if (reviewIds.size() > 0){
            List<SysReviewSubEmployee> reviewEmployee = sysReviewEmployeeService.selectSysReviewEmployeeByReviewIds(reviewIds);
            Map<Long, BigDecimal> reviewEmployeeCostMap = new HashMap<>();
            for (SysReviewSubEmployee value : reviewEmployee){
                if (reviewEmployeeCostMap.containsKey(value.getReviewId())){
                    reviewEmployeeCostMap.put(value.getReviewId(),reviewEmployeeCostMap.get(value.getReviewId()).add(value.getCost()));
                }else {
                    reviewEmployeeCostMap.put(value.getReviewId(),value.getCost());
                }
            }
            for (SysReviewSub review : list){
                if (reviewEmployeeCostMap.containsKey(review.getReviewId())){
                    review.setGuGongMoney(reviewEmployeeCostMap.get(review.getReviewId()));
                }
            }
        }
        return getDataTable(list);
    }

    @GetMapping("/doneListReview")
    public TableDataInfo doneReview(SysReviewSub sysReviewSub)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysReviewSub.setUserId(userId);
        List<SysReviewSub> list = sysReviewSubService.selectDoneReviewList(sysReviewSub);
        List<Long> reviewIds = new ArrayList<>();
        for (SysReviewSub review : list){
            reviewIds.add(review.getReviewId());
        }
        if (reviewIds.size() > 0){
            List<SysReviewSubEmployee> reviewEmployee = sysReviewEmployeeService.selectSysReviewEmployeeByReviewIds(reviewIds);
            Map<Long, BigDecimal> reviewEmployeeCostMap = new HashMap<>();
            for (SysReviewSubEmployee value : reviewEmployee){
                if (reviewEmployeeCostMap.containsKey(value.getReviewId())){
                    reviewEmployeeCostMap.put(value.getReviewId(),reviewEmployeeCostMap.get(value.getReviewId()).add(value.getCost()));
                }else {
                    reviewEmployeeCostMap.put(value.getReviewId(),value.getCost());
                }
            }
            for (SysReviewSub review : list){
                if (reviewEmployeeCostMap.containsKey(review.getReviewId())){
                    review.setGuGongMoney(reviewEmployeeCostMap.get(review.getReviewId()));
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 批量修改审核单通过
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_batch_review_pass/{reviewIds}")
    public AjaxResult setBatchReviewPass(@PathVariable Long[] reviewIds)
    {
        //修改审核流程中流程信息
        Long userId = SecurityUtils.getUserId();
        SysReviewSubProcess sysReviewSubProcess = new SysReviewSubProcess();
        sysReviewSubProcess.setUserId(userId);
        sysReviewSubProcess.setReviewTime(DateUtils.getNowDate());

        for (Long reviewId:reviewIds){
            sysReviewSubProcess.setReviewId(reviewId);
            sysReviewSubProcess.setStatus((long)this.PassStatus);
            sysReviewSubProcessService.setStatusByUserIdAndReviewId(sysReviewSubProcess);
            SysReviewSub review = new SysReviewSub();
            review.setReviewId(sysReviewSubProcess.getReviewId());
            //审核通过
            //获取审核单审核流程
            List<SysReviewSubProcess> list = sysReviewSubProcessService.selectSysReviewSubProcessList(sysReviewSubProcess);
            int passNum = 0;
            for (SysReviewSubProcess obj : list) {
                if (obj.getStatus() == (long)this.PassStatus){
                    passNum ++;
                }
            }
            //判断流程是否都已经通过了
            if (passNum == list.size()){
                //审核单通过
                SysProject sysProject = new SysProject();
                sysProject.setProjectId(review.getReviewId());
                sysProject.setIsShow(0);
                sysProjectService.updateIsShowByReviewId(sysProject);

                review.setStatus((long)this.PassStatus);
                sysReviewSubService.setSysReviewSubStatusByReviewId(review);
            } else if (passNum == list.size() - 2) {
                //审核到终审的前一个
                review.setFinalSecondStatus(1);
                sysReviewSubService.setSysReviewSubFinalSecondStatusByReviewId(review);
                sysReviewSubProcessService.setNextStatusByReviewId(sysReviewSubProcess.getReviewId());
            } else {
                //审核单流程没有全部通过，进入下一个流程审核
                sysReviewSubProcessService.setNextStatusByReviewId(sysReviewSubProcess.getReviewId());
            }
        }

        return toAjax(1);
    }

    /**
     * 开启编辑
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @PutMapping("/set_start_edit/{reviewId}")
    public AjaxResult setStartEdit(@PathVariable Long reviewId)
    {
        SysReviewSub sysReview = new SysReviewSub();
        sysReview.setStartEdit(1);
        sysReview.setReviewId(reviewId);
        return toAjax(sysReviewSubService.updateStartEditByReviewId(sysReview));
    }

    private Sheet integrateExcel(Workbook workbook, CellStyle wrapCellStyle){
        wrapCellStyle.setWrapText(true);
        wrapCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 设置垂直居中对齐
        wrapCellStyle.setAlignment(HorizontalAlignment.CENTER); // 设置水平居中对齐
        wrapCellStyle.setBorderTop(BorderStyle.THIN);
        wrapCellStyle.setBorderRight(BorderStyle.THIN);
        wrapCellStyle.setBorderBottom(BorderStyle.THIN);
        wrapCellStyle.setBorderLeft(BorderStyle.THIN);
        wrapCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        wrapCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        wrapCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        wrapCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        Sheet sheet = workbook.createSheet("Sheet1");
        // 合并第一行的5个单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

        // 创建第一行，并设置行高
        Row headerRowTemp = sheet.createRow(0);
        headerRowTemp.setHeight((short) 500); // 设置行高，这里设置为500个点

        // 创建第一个合并后的单元格
        Cell mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("请修改成具体明细表名称");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        // 设置合并后的单元格边框颜色为白色
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 4), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 4), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 4), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 4), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 4), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 4), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 4), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 4), sheet);

        // 设置行头
        Row headerRow = sheet.createRow(1);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("序号");
        headerCell1.setCellStyle(wrapCellStyle);
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("项目编号");
        headerCell2.setCellStyle(wrapCellStyle);

        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("项目名称");
        headerCell3.setCellStyle(wrapCellStyle);
        Cell headerCell4 = headerRow.createCell(3);
        headerCell4.setCellValue("工作量");
        headerCell4.setCellStyle(wrapCellStyle);
        Cell headerCell5 = headerRow.createCell(4);
        headerCell5.setCellValue("姓名");
        headerCell5.setCellStyle(wrapCellStyle);

        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 17000); // 设置第一列宽度
        sheet.setColumnWidth(3, 15000);
        sheet.setColumnWidth(4, 9000);
        return sheet;
    }

    /**
     * 修改审核是否通过
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_batch_review_settlement")
    public AjaxResult setReviewSettlementStatus(@RequestBody SysReviewSub sysReviewSub)
    {
        //修改审核单状态
        sysReviewSubService.generateStatement(sysReviewSub);
        //生成结算单
        SysReviewSubSettlement sysReviewSettlement = new SysReviewSubSettlement();
        sysReviewSettlement.setSettlementName(sysReviewSub.getSettlementName());
        sysReviewSettlement.setUserName(SecurityUtils.getUsername());
        sysReviewSettlement.setUserId(SecurityUtils.getUserId());
        sysReviewSettlementService.insertSysReviewSubSettlement(sysReviewSettlement);
        //新增关联关系
        SysSettlementAssociate sysSettlementAssociate = new SysSettlementAssociate();
        for (Long reviewId:sysReviewSub.getReviewIds()){
            sysSettlementAssociate.setSettlementId(sysReviewSettlement.getSettlementId());
            sysSettlementAssociate.setReviewId(reviewId);
            sysSettlementAssociateService.insertSysSettlementAssociate(sysSettlementAssociate);
        }
        return toAjax(1);
    }

    @GetMapping("/settlementListReview")
    public TableDataInfo settlementListReview(SysReviewSub sysReviewSub)
    {
        startPage();
        List<SysReviewSub> list = sysReviewSubService.settlementListReview(sysReviewSub);
        return getDataTable(list);
    }

    @GetMapping(value = "/get_review_by_serialNum/{serialNum}")
    public AjaxResult getReviewBySerialNum(@PathVariable("serialNum") String serialNum){
        return success(sysReviewSubService.getReviewBySerialNum(serialNum));
    }

    @Log(title = "审核单分包", businessType = BusinessType.EXPORT)
    @PostMapping("/subcontractExport")
    public void subcontractExport(HttpServletResponse response, SysReviewSub sysReviewSub) throws JsonProcessingException {
        String reviewIdString = Long.toString(sysReviewSub.getReviewId());
        SysReviewSub subcontract = sysReviewSubService.selectSysReviewSubByReviewId(reviewIdString);

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> cooperationUnitJson = objectMapper.readValue(subcontract.getCooperationUnit(), new TypeReference<List<String>>(){});
        subcontract.setCooperationUnitJson(cooperationUnitJson);

        Workbook workbook = new XSSFWorkbook();
        CellStyle wrapCellStyle = workbook.createCellStyle();
        Sheet sheet = this.integrateExcelSub(workbook, wrapCellStyle);

        //************************************
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));
        Row row = sheet.createRow(2);
        row.setHeight((short) 1000);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("项目名称");
        cell1.setCellStyle(wrapCellStyle);
        Cell cell2 = row.createCell(2);
        cell2.setCellValue(subcontract.getProjectName());
        cell2.setCellStyle(wrapCellStyle);
        Cell cell3 = row.createCell(4);
        cell3.setCellValue("抽签时间");
        cell3.setCellStyle(wrapCellStyle);
        Cell cell4 = row.createCell(5);

        String formattedDate = "";
        Date lotTime = subcontract.getLotTime();
        if (lotTime != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = sdf.format(lotTime);
        }
        cell4.setCellValue(formattedDate);
        cell4.setCellStyle(wrapCellStyle);

        this.regionUtil(sheet,2,2,0,5);
        //************************************
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));
        row = sheet.createRow(3);
        row.setHeight((short) 600);
        cell1 = row.createCell(0);
        cell1.setCellValue("工程编号");
        cell1.setCellStyle(wrapCellStyle);
        cell2 = row.createCell(2);
        cell2.setCellValue(subcontract.getSerialNum());
        cell2.setCellStyle(wrapCellStyle);
        cell3 = row.createCell(4);
        cell3.setCellValue("抽签业务名称");
        cell3.setCellStyle(wrapCellStyle);
        cell4 = row.createCell(5);
        cell4.setCellValue(subcontract.getBusinessName());
        cell4.setCellStyle(wrapCellStyle);

        this.regionUtil(sheet,3,3,0,5);
        //************************************
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 3));
        row = sheet.createRow(4);
        row.setHeight((short) 1000);
        cell1 = row.createCell(0);
        cell1.setCellValue("序号");
        cell1.setCellStyle(wrapCellStyle);
        cell2 = row.createCell(1);
        cell2.setCellValue("协作单位名称");
        cell2.setCellStyle(wrapCellStyle);
        cell3 = row.createCell(4);
        cell3.setCellValue("是否参与抽签");
        cell3.setCellStyle(wrapCellStyle);
        cell4 = row.createCell(5);
        cell4.setCellValue("未参与抽签原因");
        cell4.setCellStyle(wrapCellStyle);

        this.regionUtil(sheet,4,4,0,5);
        //************************************
        int num = 5;
        int index = 1;
        for (String cooperationJson:cooperationUnitJson){
            sheet.addMergedRegion(new CellRangeAddress(num, num, 1, 3));
            row = sheet.createRow(num);
            row.setHeight((short) 500);
            cell1 = row.createCell(0);
            cell1.setCellValue(index);
            cell1.setCellStyle(wrapCellStyle);
            cell2 = row.createCell(1);
            cell2.setCellValue(cooperationJson);
            cell2.setCellStyle(wrapCellStyle);
            cell3 = row.createCell(4);
            cell3.setCellValue("口是  口否");
            cell3.setCellStyle(wrapCellStyle);
            cell4 = row.createCell(5);
            cell4.setCellValue("");
            cell4.setCellStyle(wrapCellStyle);
            this.regionUtil(sheet,num,num,0,5);
            num++;
            index++;
        }
        //************************************
        sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(num, num, 4, 5));
        row = sheet.createRow(num);
        row.setHeight((short) 600);
        cell1 = row.createCell(0);
        cell1.setCellValue("抽签人");
        cell1.setCellStyle(wrapCellStyle);
        cell2 = row.createCell(2);
        cell2.setCellValue("");
        cell2.setCellStyle(wrapCellStyle);
        cell3 = row.createCell(3);
        cell3.setCellValue("中签单位");
        cell3.setCellStyle(wrapCellStyle);
        cell4 = row.createCell(4);
        cell4.setCellValue(subcontract.getWinUnit());
        cell4.setCellStyle(wrapCellStyle);
        this.regionUtil(sheet,num,num,0,5);
        //************************************
        num++;
        sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(num, num, 2, 5));
        row = sheet.createRow(num);
        row.setHeight((short) 600);
        cell1 = row.createCell(0);
        cell1.setCellValue("参与抽签人");
        cell1.setCellStyle(wrapCellStyle);
        cell2 = row.createCell(2);
        cell2.setCellValue("");
        cell2.setCellStyle(wrapCellStyle);
        this.regionUtil(sheet,num,num,0,5);
        //************************************
        num++;
        sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(num, num, 2, 5));
        row = sheet.createRow(num);
        row.setHeight((short) 600);
        cell1 = row.createCell(0);
        cell1.setCellValue("监督人员");
        cell1.setCellStyle(wrapCellStyle);
        cell2 = row.createCell(2);
        cell2.setCellValue("");
        cell2.setCellStyle(wrapCellStyle);
        this.regionUtil(sheet,num,num,0,5);
        //************************************
        num++;
        sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(num, num, 4, 5));
        row = sheet.createRow(num);
        row.setHeight((short) 2000);
        cell1 = row.createCell(0);
        cell1.setCellValue("分公司意见");
        cell1.setCellStyle(wrapCellStyle);
        cell2 = row.createCell(2);
        cell2.setCellValue("");
        cell2.setCellStyle(wrapCellStyle);
        cell3 = row.createCell(3);
        cell3.setCellValue("中签单位意见");
        cell3.setCellStyle(wrapCellStyle);
        cell4 = row.createCell(4);
        cell4.setCellValue("");
        cell4.setCellStyle(wrapCellStyle);
        this.regionUtil(sheet,num,num,0,5);
        //************************************
        num++;
        sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(num, num, 2, 5));
        row = sheet.createRow(num);
        row.setHeight((short) 500);
        cell1 = row.createCell(0);
        cell1.setCellValue("备注");
        cell1.setCellStyle(wrapCellStyle);
        cell2 = row.createCell(2);
        cell2.setCellValue("");
        cell2.setCellStyle(wrapCellStyle);
        this.regionUtil(sheet,num,num,0,5);
        this.finishTheEndTable(sheet,wrapCellStyle,workbook,num);


        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Sheet integrateExcelSub(Workbook workbook, CellStyle wrapCellStyle){
        wrapCellStyle.setWrapText(true);
        wrapCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 设置垂直居中对齐
        wrapCellStyle.setAlignment(HorizontalAlignment.CENTER); // 设置水平居中对齐
        wrapCellStyle.setBorderTop(BorderStyle.THIN);
        wrapCellStyle.setBorderRight(BorderStyle.THIN);
        wrapCellStyle.setBorderBottom(BorderStyle.THIN);
        wrapCellStyle.setBorderLeft(BorderStyle.THIN);
        wrapCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        wrapCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        wrapCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        wrapCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        Sheet sheet = workbook.createSheet("Sheet1");
        // 合并第一行的5个单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        // 创建第一行，并设置行高
        Row headerRowTemp = sheet.createRow(0);
        headerRowTemp.setHeight((short) 500); // 设置行高，这里设置为500个点

        // 创建第一个合并后的单元格
        Cell mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("分包项目抽签记录表");

        CellStyle headerStyleTemp = workbook.createCellStyle();
        headerStyleTemp.cloneStyleFrom(wrapCellStyle); // 将wrapCellStyle的样式克隆给headerStyle
        Font font = workbook.createFont(); // 创建字体对象
        font.setFontName("Arial"); // 设置字体名称
        font.setFontHeightInPoints((short) 16); // 设置字体大小
        font.setBold(true); // 设置字体加粗
        headerStyleTemp.setFont(font); // 应用字体样式到单元格样式中

        mergedCell.setCellStyle(headerStyleTemp); // 应用居中对齐的样式
        // 设置合并后的单元格边框颜色为白色
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 5), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 5), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 5), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 5), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 5), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 5), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 5), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 5), sheet);

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));

        // 创建第二行，并设置行高
        headerRowTemp = sheet.createRow(1);
        headerRowTemp.setHeight((short) 500); // 设置行高，这里设置为500个点
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.cloneStyleFrom(wrapCellStyle); // 将wrapCellStyle的样式克隆给headerStyle
        headerStyle.setAlignment(HorizontalAlignment.LEFT);
        // 创建第一个合并后的单元格
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("编号：请修改成具体编号");
        mergedCell.setCellStyle(headerStyle); // 应用居中对齐的样式
        // 设置合并后的单元格边框颜色为白色
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 5), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(1, 1, 0, 5), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 5), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(1, 1, 0, 5), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 5), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(1, 1, 0, 5), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 5), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(1, 1, 0, 5), sheet);

        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 7000); // 设置第一列宽度
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        return sheet;
    }

    private void finishTheEndTable(Sheet sheet, CellStyle wrapCellStyle, Workbook workbook, int num){
        num ++;
        sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 5));
        Row headerRowTemp = sheet.createRow(num);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle = workbook.createCellStyle();
        headerStyle.cloneStyleFrom(wrapCellStyle); // 将wrapCellStyle的样式克隆给headerStyle
        headerStyle.setAlignment(HorizontalAlignment.LEFT);
        // 创建第一个合并后的单元格
        Cell mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("注：1、参与抽签人员至少由项目负责人、部门主任组成；");
        mergedCell.setCellStyle(headerStyle); // 应用居中对齐的样式
        // 设置合并后的单元格边框颜色为白色
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);

        num++;
        sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 5));
        headerRowTemp = sheet.createRow(num);
        headerStyle = workbook.createCellStyle();
        headerStyle.cloneStyleFrom(wrapCellStyle); // 将wrapCellStyle的样式克隆给headerStyle
        headerStyle.setAlignment(HorizontalAlignment.LEFT);
        // 创建第一个合并后的单元格
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("        2、监督人员应为相关支部纪检委员或分公司总经理授权人（需有授权书）；");
        mergedCell.setCellStyle(headerStyle); // 应用居中对齐的样式
        // 设置合并后的单元格边框颜色为白色
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);

        num ++;
        sheet.addMergedRegion(new CellRangeAddress(num, num, 0, 5));
        headerRowTemp = sheet.createRow(num);
        headerStyle = workbook.createCellStyle();
        headerStyle.cloneStyleFrom(wrapCellStyle); // 将wrapCellStyle的样式克隆给headerStyle
        headerStyle.setAlignment(HorizontalAlignment.LEFT);
        // 创建第一个合并后的单元格
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("        3、分公司意见由分公司总经理签署；中签单位意见由中签单位代表签署。");
        mergedCell.setCellStyle(headerStyle); // 应用居中对齐的样式
        // 设置合并后的单元格边框颜色为白色
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(num, num, 0, 5), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(num, num, 0, 5), sheet);
    }

    private void regionUtil(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol){
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(firstRow, lastRow, firstCol, lastCol), sheet);
    }


    @Log(title = "按部门导出-新模块", businessType = BusinessType.EXPORT)
    @Transactional
    @PostMapping("/exportMonth")
    public void exportMonth(HttpServletResponse response, SysEmployeeWorktime sysEmployeeWorktime)
    {
        //获取符合月份的数据并整合
        List<SysEmployeeWorktime> listExport = sysEmployeeWorktimeService.selectSysEmployeeSubWorktimeListByTime(sysEmployeeWorktime);
        List<Long> reviewExportIdsList = new ArrayList<>();
        List<Long> reviewExportEmployeeIdsList = new ArrayList<>();
        for (SysEmployeeWorktime employeeWorktime:listExport){
            reviewExportIdsList.add(employeeWorktime.getReviewId());
            reviewExportEmployeeIdsList.add(employeeWorktime.getReviewEmployeeId());
        }
        reviewExportIdsList.add((long)0);
        reviewExportEmployeeIdsList.add((long)0);

        Long[] reviewExportIds = reviewExportIdsList.toArray(new Long[reviewExportIdsList.size()]);
        Long[] reviewExportEmployeeIds = reviewExportEmployeeIdsList.toArray(new Long[reviewExportEmployeeIdsList.size()]);

        sysReviewEmployeeService.updateIsJsByReviewEmployeeIds(reviewExportEmployeeIds);
        //.....................

        List<SysReviewSub> list = sysReviewSubService.selectSysReviewSubListByReviewIds(reviewExportIds);
        int num = 0;
        for (SysReviewSub sysReviewTemp:list){
            num ++;
            float day = 0;
            Map<String, Integer> map = new HashMap<>();
            String hiredWorkerName = "";
            String workload = "";
            sysReviewTemp.setReviewExportEmployeeIds(reviewExportEmployeeIds);
            List<SysReviewSubEmployee> reviewEmployee = sysReviewEmployeeService.selectSysReviewEmployeeByReviewIdMonth(sysReviewTemp);
            sysReviewTemp.setExportSerialNumber(num);
            for (SysReviewSubEmployee employee:reviewEmployee){
                hiredWorkerName = hiredWorkerName + (employee.getName() + "、");
                day += employee.getWorkDay();
                String workloadTemp = employee.getWorkTime();
                String[] strArray = workloadTemp.split(";");
                for (String dayTime:strArray){
                    if (dayTime != ""){
                        map.put(dayTime, 1);
                    }
                }
            }
            if (hiredWorkerName != ""){
                hiredWorkerName = hiredWorkerName.substring(0, hiredWorkerName.length() - 1);
            }
            if (reviewEmployee.size() > 0){
                workload += (reviewEmployee.size() + "人" + day + "天");
            }
            if (map.size() > 0){
                workload += "(";
            }
            for (String key : map.keySet()) {
                workload += (key + "、");
            }
            if (map.size() > 0){
                workload = workload.substring(0, workload.length() - 1);
                workload += ")";
            }
            sysReviewTemp.setHiredWorkerName(hiredWorkerName);
            sysReviewTemp.setWorkload(workload);
        }

        Workbook workbook = new XSSFWorkbook();
        CellStyle wrapCellStyle = workbook.createCellStyle();
        Sheet sheet = this.integrateExcel(workbook, wrapCellStyle);

        int rowIndex = 2; // 从第二行开始，留给行头
        for (SysReviewSub sysReviewTemp : list) {
            Row row = sheet.createRow(rowIndex);

            Cell cell1 = row.createCell(0);
            cell1.setCellValue(sysReviewTemp.getExportSerialNumber());
            cell1.setCellStyle(wrapCellStyle);

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(sysReviewTemp.getSerialNum());
            cell2.setCellStyle(wrapCellStyle);

            Cell cell3 = row.createCell(2);
            cell3.setCellValue(sysReviewTemp.getProjectName());
            cell3.setCellStyle(wrapCellStyle);

            Cell cell4 = row.createCell(3);
            cell4.setCellValue(sysReviewTemp.getWorkload());
            cell4.setCellStyle(wrapCellStyle);

            Cell cell5 = row.createCell(4);
            cell5.setCellValue(sysReviewTemp.getHiredWorkerName());
            cell5.setCellStyle(wrapCellStyle);

            rowIndex++;
        }

        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
