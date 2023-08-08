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
import com.kcylog.system.domain.SysSubcontract;
import com.kcylog.system.domain.SysSubcontractProcess;
import com.kcylog.system.domain.SysSubcontractProcessConfigInfo;
import com.kcylog.system.service.ISysSubcontractProcessConfigInfoService;
import com.kcylog.system.service.ISysSubcontractProcessService;
import com.kcylog.system.service.ISysSubcontractService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void export(HttpServletResponse response, SysSubcontract sysSubcontract) throws JsonProcessingException {
        SysSubcontract subcontract =  sysSubcontractService.selectSysSubcontractBySubcontractId(sysSubcontract.getSubcontractId());
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> cooperationUnitJson = objectMapper.readValue(subcontract.getCooperationUnit(), new TypeReference<List<String>>(){});
        subcontract.setCooperationUnitJson(cooperationUnitJson);

        Workbook workbook = new XSSFWorkbook();
        CellStyle wrapCellStyle = workbook.createCellStyle();
        Sheet sheet = this.integrateExcel(workbook, wrapCellStyle);

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

    @Log(title = "分包", businessType = BusinessType.UPDATE)
    @PutMapping("/set_is_print/{subcontractId}")
    public AjaxResult setIsPrint(@PathVariable Long subcontractId)
    {
        return toAjax(sysSubcontractService.setSubcontractIsPrintBySubcontractId(subcontractId));
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
        mergedCell.setCellValue("        3、分院意见由分公司总监理签署；中签单位意见由中签单位代表签署。");
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

    @GetMapping(value = "/get_subcontract_by_serialNum/{serialNum}")
    public AjaxResult getSubcontractBySerialNum(@PathVariable("serialNum") String serialNum){
        return success(sysSubcontractService.getSubcontractBySerialNum(serialNum));
    }
}
