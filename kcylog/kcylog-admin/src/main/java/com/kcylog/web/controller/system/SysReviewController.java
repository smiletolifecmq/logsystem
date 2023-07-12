package com.kcylog.web.controller.system;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审核单Controller
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
@RestController
@RequestMapping("/system/review")
public class SysReviewController extends BaseController
{
    final int PassStatus = 2;
    final int NoPassStatus = 3;

    @Autowired
    private ISysReviewService sysReviewService;
    @Autowired
    private ISysProcessConfigInfoService sysProcessConfigInfoService;

    @Autowired
    private ISysReviewProcessService sysReviewProcessService;

    @Autowired
    private ISysReviewEmployeeService sysReviewEmployeeService;

    @Autowired
    private ISysReviewSettlementService sysReviewSettlementService;

    @Autowired
    private ISysSettlementAssociateService sysSettlementAssociateService;

    /**
     * 查询审核单列表
     */
    @PreAuthorize("@ss.hasPermi('system:review:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysReview sysReview)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysReview.setUserId(userId);
        List<SysReview> list = sysReviewService.selectSysReviewList(sysReview);

        return getDataTable(list);
    }

    /**
     * 导出审核单列表
     */
    @Log(title = "审核单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysReview sysReview)
    {
        List<SysReview> list = sysReviewService.selectSysReviewList(sysReview);
        int num = 0;
        for (SysReview sysReviewTemp:list){
            num ++;
            float day = 0;
            Map<String, Integer> map = new HashMap<>();
            String hiredWorkerName = "";
            String workload = "";
            List<SysReviewEmployee> reviewEmployee = sysReviewEmployeeService.selectSysReviewEmployeeByReviewId(sysReviewTemp.getReviewId());
            sysReviewTemp.setExportSerialNumber(num);
            for (SysReviewEmployee employee:reviewEmployee){
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
        for (SysReview sysReviewTemp : list) {
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
    public AjaxResult getInfo(@PathVariable("reviewId") String reviewId)
    {
        return success(sysReviewService.selectSysReviewByReviewId(reviewId));
    }

    /**
     * 新增审核单
     */
    @Log(title = "审核单", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysReview sysReview)
    {
        Long userId = SecurityUtils.getUserId();
        Long deptId = SecurityUtils.getDeptId();
        sysReview.setUserId(userId);
        sysReview.setDeptId(deptId);
        sysReviewService.insertSysReview(sysReview);
        List<SysProcessConfigInfo> list = sysProcessConfigInfoService.selectSysProcessConfigInfoListByDeptId(deptId);
        List<SysReviewProcess> reviewProcess = new ArrayList<>();
        for (SysProcessConfigInfo configInfo : list) {
            SysReviewProcess review = new SysReviewProcess();
            review.setReviewId((sysReview.getReviewId()));
            review.setUserId(configInfo.getUserId());
            review.setStatus((long)0);
            review.setNode(Long.parseLong(configInfo.getNode()));
            reviewProcess.add(review);
        }
        int result = sysReviewProcessService.insertSysReviewProcessBatch(reviewProcess);

        return toAjax(result);
    }

    /**
     * 修改审核单
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody SysReview sysReview)
    {
        sysReview.setStartEdit(0);
        return toAjax(sysReviewService.updateSysReview(sysReview));
    }

    /**
     * 删除审核单
     */
    @Log(title = "审核单", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{reviewIds}")
    public AjaxResult remove(@PathVariable String[] reviewIds)
    {
        sysReviewService.deleteSysReviewByReviewIds(reviewIds);
        int result = sysReviewProcessService.deleteSysReviewProcessByReviewIds(reviewIds);
        return toAjax(result);
    }


    /**
     * 发起审核单审核
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_status")
    public AjaxResult setStatus(@RequestBody SysReview sysReview)
    {
        //设置审核单为进行中
        sysReviewService.setSysReviewStatusByReviewId(sysReview);
        //先将审核单流程状态全部设置成未开始
        sysReviewProcessService.setStatusNotStartByReviewId(sysReview.getReviewId());
        //将审核单第一个流程设置成进行中
        int result = sysReviewProcessService.setStatusByReviewIdFirst(sysReview.getReviewId());
        return toAjax(result);
    }

    /**
     * 获取待办审核单
     */
    @GetMapping("/upcomingListReview")
    public TableDataInfo upcomingListReview(SysReview sysReview)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysReview.setUserId(userId);
        List<SysReview> list = sysReviewService.selectSysUpcomingReviewList(sysReview);
        return getDataTable(list);
    }


    /**
     * 修改审核是否通过
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_review_process")
    public AjaxResult setReviewProcessStatus(@RequestBody SysReviewProcess sysReviewProcess)
    {
        //修改审核流程中流程信息
        Long userId = SecurityUtils.getUserId();
        sysReviewProcess.setUserId(userId);
        sysReviewProcess.setReviewTime(DateUtils.getNowDate());
        sysReviewProcessService.setStatusByUserIdAndReviewId(sysReviewProcess);

        SysReview review = new SysReview();
        review.setReviewId(sysReviewProcess.getReviewId());
        if (sysReviewProcess.getStatus() == (long)this.PassStatus){
            //审核通过
            //获取审核单审核流程
            List<SysReviewProcess> list = sysReviewProcessService.selectSysReviewProcessList(sysReviewProcess);
            int passNum = 0;
            for (SysReviewProcess obj : list) {
                if (obj.getStatus() == (long)this.PassStatus){
                    passNum ++;
                }
            }
            //判断流程是否都已经通过了
            if (passNum == list.size()){
                //审核单通过
                review.setStatus((long)this.PassStatus);
                sysReviewService.setSysReviewStatusByReviewId(review);
            } else if (passNum == list.size() - 1) {
                //审核到终审的前一个
                review.setFinalSecondStatus(1);
                sysReviewService.setSysReviewFinalSecondStatusByReviewId(review);
                sysReviewProcessService.setNextStatusByReviewId(sysReviewProcess.getReviewId());
            } else {
                //审核单流程没有全部通过，进入下一个流程审核
                sysReviewProcessService.setNextStatusByReviewId(sysReviewProcess.getReviewId());
            }

        }else {
            //审核不通过
            review.setStatus((long)this.NoPassStatus);
            sysReviewService.setSysReviewStatusByReviewId(review);
        }
        return toAjax(1);
    }

    /**
     * 获取全部已通过的审核单
     */
    @GetMapping("/completedListReview")
    public TableDataInfo completedReview(SysReview sysReview)
    {
        startPage();
        List<SysReview> list = sysReviewService.selectCompletedReviewList(sysReview);
        return getDataTable(list);
    }

    @GetMapping("/doneListReview")
    public TableDataInfo doneReview(SysReview sysReview)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysReview.setUserId(userId);
        List<SysReview> list = sysReviewService.selectDoneReviewList(sysReview);
        return getDataTable(list);
    }

    /**
     * 批量修改审核单
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_batch_review_pass/{reviewIds}")
    public AjaxResult setBatchReviewPass(@PathVariable Long[] reviewIds)
    {
        //修改审核流程中流程信息
        Long userId = SecurityUtils.getUserId();
        SysReviewProcess sysReviewProcess = new SysReviewProcess();
        sysReviewProcess.setUserId(userId);
        sysReviewProcess.setReviewTime(DateUtils.getNowDate());

        for (Long reviewId:reviewIds){
            sysReviewProcess.setReviewId(reviewId);
            sysReviewProcess.setStatus((long)this.PassStatus);
            sysReviewProcessService.setStatusByUserIdAndReviewId(sysReviewProcess);
            SysReview review = new SysReview();
            review.setReviewId(sysReviewProcess.getReviewId());
            //审核通过
            //获取审核单审核流程
            List<SysReviewProcess> list = sysReviewProcessService.selectSysReviewProcessList(sysReviewProcess);
            int passNum = 0;
            for (SysReviewProcess obj : list) {
                if (obj.getStatus() == (long)this.PassStatus){
                    passNum ++;
                }
            }
            //判断流程是否都已经通过了
            if (passNum == list.size()){
                //审核单通过
                review.setStatus((long)this.PassStatus);
                sysReviewService.setSysReviewStatusByReviewId(review);
            } else if (passNum == list.size() - 1) {
                //审核到终审的前一个
                review.setFinalSecondStatus(1);
                sysReviewService.setSysReviewFinalSecondStatusByReviewId(review);
                sysReviewProcessService.setNextStatusByReviewId(sysReviewProcess.getReviewId());
            } else {
                //审核单流程没有全部通过，进入下一个流程审核
                sysReviewProcessService.setNextStatusByReviewId(sysReviewProcess.getReviewId());
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
        SysReview sysReview = new SysReview();
        sysReview.setStartEdit(1);
        sysReview.setReviewId(reviewId);
        return toAjax(sysReviewService.updateStartEditByReviewId(sysReview));
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
    public AjaxResult setReviewSettlementStatus(@RequestBody SysReview sysReview)
    {
        //修改审核单状态
        sysReviewService.generateStatement(sysReview);
        //生成结算单
        SysReviewSettlement sysReviewSettlement = new SysReviewSettlement();
        sysReviewSettlement.setSettlementName(sysReview.getSettlementName());
        sysReviewSettlement.setUserName(SecurityUtils.getUsername());
        sysReviewSettlementService.insertSysReviewSettlement(sysReviewSettlement);
        //新增关联关系
        SysSettlementAssociate sysSettlementAssociate = new SysSettlementAssociate();
        for (Long reviewId:sysReview.getReviewIds()){
            sysSettlementAssociate.setSettlementId(sysReviewSettlement.getSettlementId());
            sysSettlementAssociate.setReviewId(reviewId);
            sysSettlementAssociateService.insertSysSettlementAssociate(sysSettlementAssociate);
        }
        return toAjax(1);
    }
}
