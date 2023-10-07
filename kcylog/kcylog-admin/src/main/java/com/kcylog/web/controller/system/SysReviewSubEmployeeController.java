package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.exception.ServiceException;
import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.*;
import com.kcylog.system.service.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 雇工实际工作内容记录Controller
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/system/reviewSubEmployee")
public class SysReviewSubEmployeeController extends BaseController
{
    @Autowired
    private ISysEmployeeWorktimeService sysEmployeeWorktimeService;

    @Autowired
    private ISysReviewSubEmployeeService sysReviewEmployeeService;

    @Autowired
    private ISysReviewSubService sysReviewService;

    @Autowired
    private ISysEmployeeService sysEmployeeService;

    @Autowired
    private ISysReviewSubProcessService sysReviewProcessService;
    /**
     * 查询雇工实际工作内容记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysReviewSubEmployee sysReviewEmployee)
    {
        startPage();
        List<SysReviewSubEmployee> list = sysReviewEmployeeService.selectSysReviewSubEmployeeList(sysReviewEmployee);

        return getDataTable(list);
    }

    /**
     * 导出雇工实际工作内容记录列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysReviewSub sysReview)
    {
        Map<String, Integer> keyValueMap = new HashMap<>();
        Map<String, Integer> serialNumMap = new HashMap<>();
        List<SysReviewSubEmployee> listTemp = sysReviewEmployeeService.selectSysReviewSubEmployeeListJoinReview(sysReview);
        List<SysReviewSubEmployee> list = new ArrayList<>();

        int keyIndex = 0;
        String serialNum = "";
        String projectName = "";
        for (SysReviewSubEmployee reviewEmployee:listTemp){
            boolean serialNumValue = serialNumMap.containsKey(reviewEmployee.getReview().getSerialNum());
            if (!serialNumValue) {
                serialNum = serialNum + reviewEmployee.getReview().getSerialNum() + "、";
                projectName = projectName + reviewEmployee.getReview().getProjectName() + "、";
                serialNumMap.put(reviewEmployee.getReview().getSerialNum(),1);
            }
            boolean containsValue = keyValueMap.containsKey(reviewEmployee.getIdCard());
            if (containsValue) {
                int index =  keyValueMap.get(reviewEmployee.getIdCard());
                list.get(index).setWorkDay(list.get(index).getWorkDay() + reviewEmployee.getWorkDay());
                list.get(index).setCost(list.get(index).getCost().add(reviewEmployee.getCost()));
            } else {
                keyValueMap.put(reviewEmployee.getIdCard(), keyIndex);
                list.add(reviewEmployee);
                keyIndex ++;
            }
        }
        if (serialNum != ""){
            serialNum = serialNum.substring(0, serialNum.lastIndexOf("、"));
            projectName = projectName.substring(0, projectName.lastIndexOf("、"));
        }
        int num = 0;
        for (SysReviewSubEmployee reviewEmployee:list){
            num ++;
            reviewEmployee.setExportSerialNumber(num);
        }

        //样式定义
        Workbook workbook = new XSSFWorkbook();
        CellStyle wrapCellStyle = workbook.createCellStyle();
        CellStyle headerStyle = workbook.createCellStyle();
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
        headerStyle.cloneStyleFrom(wrapCellStyle); // 将wrapCellStyle的样式克隆给headerStyle
        Font font = workbook.createFont(); // 创建字体对象
        font.setFontName("Arial"); // 设置字体名称
        font.setFontHeightInPoints((short) 16); // 设置字体大小
        font.setBold(true); // 设置字体加粗
        headerStyle.setFont(font); // 应用字体样式到单元格样式中
        //************************************************************

        //设置第一行数据
        Sheet sheet = workbook.createSheet("Sheet1");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        Row headerRowTemp = sheet.createRow(0);
        headerRowTemp.setHeight((short) 500); // 设置行高，这里设置为500个点
        Cell mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("福州市勘测院有限公司项目劳务费签领表");
        mergedCell.setCellStyle(headerStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(0, 0, 0, 10), sheet);
        //************************************************************

        //设置第二行数据
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));
        headerRowTemp = sheet.createRow(1);
        headerRowTemp.setHeight((short) 500); // 设置行高，这里设置为500个点
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("请修改成具体时间");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(1, 1, 0, 10), sheet);
        //************************************************************

        //设置第三行数据
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 8, 10));
        headerRowTemp = sheet.createRow(2);
        headerRowTemp.setHeight((short) 500); // 设置行高，这里设置为500个点
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("项目名称：" + projectName);
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        mergedCell = headerRowTemp.createCell(8);
        mergedCell.setCellValue("项目编号：" + serialNum);
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(2, 2, 0, 10), sheet);
        //************************************************************

        // 设置行头
        Row headerRow = sheet.createRow(3);
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("序号");
        headerCell1.setCellStyle(wrapCellStyle);
        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("姓名");
        headerCell2.setCellStyle(wrapCellStyle);

        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("天数");
        headerCell3.setCellStyle(wrapCellStyle);
        Cell headerCell4 = headerRow.createCell(3);
        headerCell4.setCellValue("金额/天（元）");
        headerCell4.setCellStyle(wrapCellStyle);
        Cell headerCell5 = headerRow.createCell(4);
        headerCell5.setCellValue("应付劳务费");
        headerCell5.setCellStyle(wrapCellStyle);

        Cell headerCell6 = headerRow.createCell(5);
        headerCell6.setCellValue("应扣税费");
        headerCell6.setCellStyle(wrapCellStyle);
        Cell headerCell7 = headerRow.createCell(6);
        headerCell7.setCellValue("应扣个税");
        headerCell7.setCellStyle(wrapCellStyle);
        Cell headerCell8 = headerRow.createCell(7);
        headerCell8.setCellValue("实发劳务费");
        headerCell8.setCellStyle(wrapCellStyle);
        Cell headerCell9 = headerRow.createCell(8);
        headerCell9.setCellValue("开户行和银行卡号");
        headerCell9.setCellStyle(wrapCellStyle);
        Cell headerCell10 = headerRow.createCell(9);
        headerCell10.setCellValue("身份证号");
        headerCell10.setCellStyle(wrapCellStyle);
        Cell headerCell11 = headerRow.createCell(10);
        headerCell11.setCellValue("领取人签字");
        headerCell11.setCellStyle(wrapCellStyle);

        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 2000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 6000);
        sheet.setColumnWidth(9, 7000);
        sheet.setColumnWidth(10, 4000);

        float totalDay = 0;
        BigDecimal totalCost = new BigDecimal(0);

        int rowIndex = 4; // 从第二行开始，留给行头
        for (SysReviewSubEmployee sysReviewEmployee : list) {
            Row row = sheet.createRow(rowIndex);

            Cell cell1 = row.createCell(0);
            cell1.setCellValue(sysReviewEmployee.getExportSerialNumber());
            cell1.setCellStyle(wrapCellStyle);

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(sysReviewEmployee.getName());
            cell2.setCellStyle(wrapCellStyle);

            Cell cell3 = row.createCell(2);
            cell3.setCellValue(sysReviewEmployee.getWorkDay());
            cell3.setCellStyle(wrapCellStyle);

            Cell cell4 = row.createCell(3);
            cell4.setCellValue(200);
            cell4.setCellStyle(wrapCellStyle);

            Cell cell5 = row.createCell(4);
            cell5.setCellValue(sysReviewEmployee.getExportPayableServiceFee());
            cell5.setCellStyle(wrapCellStyle);

            Cell cell6 = row.createCell(5);
            cell6.setCellValue(sysReviewEmployee.getExportTaxDeductible());
            cell6.setCellStyle(wrapCellStyle);

            Cell cell7 = row.createCell(6);
            cell7.setCellValue(sysReviewEmployee.getExportSelfDeductible());
            cell7.setCellStyle(wrapCellStyle);

            Cell cell8 = row.createCell(7);
            cell8.setCellValue(sysReviewEmployee.getCost().toString());
            cell8.setCellStyle(wrapCellStyle);

            Cell cell9 = row.createCell(8);
            cell9.setCellValue(sysReviewEmployee.getExportBankAndCardNumber());
            cell9.setCellStyle(wrapCellStyle);

            Cell cell10 = row.createCell(9);
            cell10.setCellValue(sysReviewEmployee.getIdCard());
            cell10.setCellStyle(wrapCellStyle);

            Cell cell11 = row.createCell(10);
            cell11.setCellValue(sysReviewEmployee.getExportRecipients());
            cell11.setCellStyle(wrapCellStyle);

            totalDay += sysReviewEmployee.getWorkDay();
            totalCost = totalCost.add(sysReviewEmployee.getCost());

            rowIndex++;
        }

        // 合并前两个单元格，第 n 行
        int firstColIndex = 0; // 第一个单元格的列索引（从 0 开始）
        int lastColIndex = 1;  // 最后一个单元格的列索引（从 0 开始）
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, firstColIndex, lastColIndex));
        // 创建第 n 行，并设置行高
        Row row = sheet.createRow(rowIndex);
        row.setHeight((short) 500); // 设置行高，这里设置为500个点
        // 创建第一个合并后的单元格
        mergedCell = row.createCell(firstColIndex);
        mergedCell.setCellValue("合计"); // 留空
        mergedCell.setCellStyle(wrapCellStyle);
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex, lastColIndex), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex, lastColIndex), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex, lastColIndex), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex, lastColIndex), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex, lastColIndex), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex, lastColIndex), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex, lastColIndex), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex, lastColIndex), sheet);
        Cell cell12 = row.createCell(2);
        cell12.setCellValue(totalDay);
        cell12.setCellStyle(wrapCellStyle);
        Cell cell13 = row.createCell(3);
        cell13.setCellValue(200);
        cell13.setCellStyle(wrapCellStyle);
        Cell cell15 = row.createCell(4);
        cell15.setCellValue("");
        cell15.setCellStyle(wrapCellStyle);
        Cell cell16 = row.createCell(5);
        cell16.setCellValue("");
        cell16.setCellStyle(wrapCellStyle);
        Cell cell17 = row.createCell(6);
        cell17.setCellValue("");
        cell17.setCellStyle(wrapCellStyle);
        Cell cell18 = row.createCell(7);
        cell18.setCellValue(totalCost.toString());
        cell18.setCellStyle(wrapCellStyle);
        Cell cell19 = row.createCell(8);
        cell19.setCellValue("");
        cell19.setCellStyle(wrapCellStyle);
        Cell cell20 = row.createCell(9);
        cell20.setCellValue("");
        cell20.setCellStyle(wrapCellStyle);
        Cell cell21 = row.createCell(10);
        cell21.setCellValue("");
        cell21.setCellStyle(wrapCellStyle);

        rowIndex++;
        // 合并前两个单元格，第 n 行
        int firstColIndex1 = 0; // 第一个单元格的列索引（从 0 开始）
        int lastColIndex1 = 1;  // 最后一个单元格的列索引（从 0 开始）
        int firstColIndex2 = 2; // 第一个单元格的列索引（从 0 开始）
        int lastColIndex2 = 10;  // 最后一个单元格的列索引（从 0 开始）
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, firstColIndex1, lastColIndex1));
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, firstColIndex2, lastColIndex2));
        // 创建第 n 行，并设置行高
        row = sheet.createRow(rowIndex);
        row.setHeight((short) 500); // 设置行高，这里设置为500个点
        // 创建第一个合并后的单元格
        mergedCell = row.createCell(firstColIndex1);
        mergedCell.setCellValue("大写合计");
        mergedCell.setCellStyle(wrapCellStyle);
        mergedCell = row.createCell(firstColIndex2);
        mergedCell.setCellValue("应付劳务费合计： ，实发劳务费合计： 。");
        mergedCell.setCellStyle(wrapCellStyle);
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex1, lastColIndex1), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex1, lastColIndex1), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex1, lastColIndex1), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex1, lastColIndex1), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex1, lastColIndex1), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex1, lastColIndex1), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex1, lastColIndex1), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex1, lastColIndex1), sheet);

        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex2, lastColIndex2), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex2, lastColIndex2), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex2, lastColIndex2), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex2, lastColIndex2), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex2, lastColIndex2), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex2, lastColIndex2), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(rowIndex, rowIndex, firstColIndex2, lastColIndex2), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(rowIndex, rowIndex, firstColIndex2, lastColIndex2), sheet);

        rowIndex ++;
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 3));
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 7, 8));
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 9, 10));
        row = sheet.createRow(rowIndex);
        row.setHeight((short) 1000); // 设置行高，这里设置为500个点
        // 创建第一个合并后的单元格
        mergedCell = row.createCell(0);
        mergedCell.setCellValue("分公司分管领导:");
        mergedCell = row.createCell(4);
        mergedCell.setCellValue("部门负责人:");
        mergedCell = row.createCell(7);
        mergedCell.setCellValue("项目负责人:");
        mergedCell = row.createCell(9);
        mergedCell.setCellValue("制表人:");

        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取雇工实际工作内容记录详细信息
     */
    @GetMapping(value = "/{reviewEmployeeId}")
    public AjaxResult getInfo(@PathVariable("reviewEmployeeId") Long reviewEmployeeId)
    {
        return success(sysReviewEmployeeService.selectSysReviewSubEmployeeByReviewEmployeeId(reviewEmployeeId));
    }

    /**
     * 新增雇工实际工作内容记录
     */
    @Log(title = "雇工实际工作内容记录", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody SysReviewSubEmployee sysReviewEmployee)
    {
        //判断金额
        SysReviewSub sysReview = sysReviewService.selectSysReviewSubByReviewId(sysReviewEmployee.getReviewId().toString());
        List<SysReviewSubEmployee> list = sysReviewEmployeeService.selectSysReviewSubEmployeeList(sysReviewEmployee);
        BigDecimal money = BigDecimal.valueOf(0.00);
        for (SysReviewSubEmployee employee : list) {
            money = money.add(employee.getCost());
        }
        money = money.add(sysReviewEmployee.getCost());
        int result = sysReview.getBudgetMoney().compareTo(money);
        if (result < 0) {
            throw new ServiceException("审核单雇工实际总费用大于预估费用～");
        }

        //判断雇工时间
        SysEmployee sysEmployee = sysEmployeeService.selectSysEmployeeByIdCard(sysReviewEmployee.getIdCard());

        sysReviewEmployeeService.insertSysReviewSubEmployee(sysReviewEmployee);
        for (WorkTimeStamp reviewEmployee :sysReviewEmployee.getWorkTimeStamp()){
            SysEmployeeWorktime employeeWorktime = new SysEmployeeWorktime();
            employeeWorktime.setEmployeeId(sysEmployee.getEmployeeId());
            employeeWorktime.setStartTime(reviewEmployee.getStartTime());
            employeeWorktime.setEndTime(reviewEmployee.getEndTime());
            employeeWorktime.setReviewId(sysReviewEmployee.getReviewId());
            employeeWorktime.setReviewEmployeeId(sysReviewEmployee.getReviewEmployeeId());
            List<SysEmployeeWorktime> listWorktime = sysEmployeeWorktimeService.selectSysEmployeeWorktimeExists(employeeWorktime);
            if (listWorktime.size() > 0){
                throw new ServiceException("该雇工在这时间段已经安排了工作～");
            }
            sysEmployeeWorktimeService.insertSysEmployeeWorktime(employeeWorktime);
        }

        //修改审核单最终填写事件
        sysReview.setFinalTime(DateUtils.getNowDate());
        sysReviewService.updateSysReviewSubFinalTime(sysReview);

        return toAjax(1);
    }

    /**
     * 修改雇工实际工作内容记录
     */
    @Log(title = "雇工实际工作内容记录", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    public AjaxResult edit(@RequestBody SysReviewSubEmployee sysReviewEmployee)
    {
        SysReviewSub sysReview = sysReviewService.selectSysReviewSubByReviewId(sysReviewEmployee.getReviewId().toString());
        List<SysReviewSubEmployee> list = sysReviewEmployeeService.selectSysReviewSubEmployeeListNotItself(sysReviewEmployee);
        BigDecimal money = BigDecimal.valueOf(0.00);
        for (SysReviewSubEmployee employee : list) {
            money = money.add(employee.getCost());
        }
        money = money.add(sysReviewEmployee.getCost());
        int result = sysReview.getBudgetMoney().compareTo(money);
        if (result < 0) {
            throw new ServiceException("审核单雇工实际总费用大于预估费用～");
        }


        SysEmployee sysEmployee = sysEmployeeService.selectSysEmployeeByIdCard(sysReviewEmployee.getIdCard());

        if (sysReviewEmployee.getWorkTimeStamp() != null){
            Long[] reviewRemployeeId = {sysReviewEmployee.getReviewEmployeeId()};
            sysEmployeeWorktimeService.deleteSysEmployeeWorktimeByReviewEmployeeIds(reviewRemployeeId);
            for (WorkTimeStamp reviewEmployee :sysReviewEmployee.getWorkTimeStamp()){
                SysEmployeeWorktime employeeWorktime = new SysEmployeeWorktime();
                employeeWorktime.setEmployeeId(sysEmployee.getEmployeeId());
                employeeWorktime.setStartTime(reviewEmployee.getStartTime());
                employeeWorktime.setEndTime(reviewEmployee.getEndTime());
                employeeWorktime.setReviewId(sysReviewEmployee.getReviewId());
                employeeWorktime.setReviewEmployeeId(sysReviewEmployee.getReviewEmployeeId());
                List<SysEmployeeWorktime> listWorktime = sysEmployeeWorktimeService.selectSysEmployeeWorktimeExists(employeeWorktime);
                if (listWorktime.size() > 0){
                    throw new ServiceException("该雇工在这时间段已经安排了工作～");
                }
                sysEmployeeWorktimeService.insertSysEmployeeWorktime(employeeWorktime);
            }
        }

        return toAjax(sysReviewEmployeeService.updateSysReviewSubEmployee(sysReviewEmployee));
    }

    /**
     * 删除雇工实际工作内容记录
     */
    @Log(title = "雇工实际工作内容记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reviewEmployeeIds}")
    @Transactional
    public AjaxResult remove(@PathVariable Long[] reviewEmployeeIds)
    {
        sysEmployeeWorktimeService.deleteSysEmployeeWorktimeByReviewEmployeeIds(reviewEmployeeIds);
        return toAjax(sysReviewEmployeeService.deleteSysReviewSubEmployeeByReviewEmployeeIds(reviewEmployeeIds));
    }

    @PostMapping("/outsourcingExport")
    public void outsourcingExport(HttpServletResponse response, SysReviewSub sysReview)
    {
        Map<String, Integer> keyValueMap = new HashMap<>();
        Map<String, Integer> serialNumMap = new HashMap<>();

        List<SysReviewSubEmployee> listTemp = sysReviewEmployeeService.selectSysReviewSubEmployeeListJoinReview(sysReview);
        List<SysReviewSubEmployee> list = new ArrayList<>();

        int keyIndex = 0;
        String serialNum = "";
        String projectName = "";
        for (SysReviewSubEmployee reviewEmployee:listTemp){
            boolean serialNumValue = serialNumMap.containsKey(reviewEmployee.getReview().getSerialNum());
            if (!serialNumValue) {
                serialNum = serialNum + reviewEmployee.getReview().getSerialNum() + "、";
                projectName = projectName + reviewEmployee.getReview().getProjectName() + "、";
                serialNumMap.put(reviewEmployee.getReview().getSerialNum(),1);
            }
            boolean containsValue = keyValueMap.containsKey(reviewEmployee.getIdCard());
            if (containsValue) {
                int index =  keyValueMap.get(reviewEmployee.getIdCard());
                list.get(index).setWorkDay(list.get(index).getWorkDay() + reviewEmployee.getWorkDay());
                list.get(index).setCost(list.get(index).getCost().add(reviewEmployee.getCost()));
            } else {
                keyValueMap.put(reviewEmployee.getIdCard(), keyIndex);
                list.add(reviewEmployee);
                keyIndex ++;
            }
        }
        if (serialNum != ""){
            serialNum = serialNum.substring(0, serialNum.lastIndexOf("、"));
            projectName = projectName.substring(0, projectName.lastIndexOf("、"));
        }

        int num = 0;
        for (SysReviewSubEmployee reviewEmployee:list){
            num ++;
            reviewEmployee.setExportSerialNumber(num);
        }

        //样式定义
        Workbook workbook = new XSSFWorkbook();
        Font font = workbook.createFont(); // 创建字体对象
        font.setFontName("Arial"); // 设置字体名称
        font.setFontHeightInPoints((short) 16); // 设置字体大小
        CellStyle wrapCellStyle = workbook.createCellStyle();
        CellStyle headerStyle = workbook.createCellStyle();
        CellStyle lowStyle = workbook.createCellStyle();
        wrapCellStyle.setWrapText(true);
        wrapCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 设置垂直居中对齐
        wrapCellStyle.setAlignment(HorizontalAlignment.JUSTIFY); // 设置水平居中对齐
        wrapCellStyle.setBorderTop(BorderStyle.THIN);
        wrapCellStyle.setBorderRight(BorderStyle.THIN);
        wrapCellStyle.setBorderBottom(BorderStyle.THIN);
        wrapCellStyle.setBorderLeft(BorderStyle.THIN);
        wrapCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        wrapCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        wrapCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        wrapCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        wrapCellStyle.setFont(font);
        headerStyle.cloneStyleFrom(wrapCellStyle); // 将wrapCellStyle的样式克隆给headerStyle
        lowStyle.cloneStyleFrom(wrapCellStyle); // 将wrapCellStyle的样式克隆给headerStyle
        font = workbook.createFont(); // 创建字体对象
        font.setFontName("Arial"); // 设置字体名称
        font.setFontHeightInPoints((short) 24); // 设置字体大小
        font.setBold(true); // 设置字体加粗
        headerStyle.setFont(font); // 应用字体样式到单元格样式中
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        //************************************************************

        //设置第一行数据
        Sheet sheet = workbook.createSheet("Sheet1");
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 2000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 3000);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(7, 3000);
        sheet.setColumnWidth(8, 6000);
        sheet.setColumnWidth(9, 7000);
        sheet.setColumnWidth(10, 4000);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        Row headerRowTemp = sheet.createRow(0);
        headerRowTemp.setHeight((short) 700); // 设置行高，这里设置为500个点
        Cell mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("福州市勘测院有限公司临时劳务外包申请表");
        mergedCell.setCellStyle(headerStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(0, 0, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(0, 0, 0, 10), sheet);
        //************************************************************

        //设置第二行数据
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));
        headerRowTemp = sheet.createRow(1);
        headerRowTemp.setHeight((short) 500); // 设置行高，这里设置为500个点
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("  申请部门：                  申请日期：");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(1, 1, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(1, 1, 0, 10), sheet);
        //************************************************************

        //设置第三行数据
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 6, 10));
        headerRowTemp = sheet.createRow(2);
        headerRowTemp.setHeight((short) 5000); // 设置行高，这里设置为500个点
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("项目名称：" + projectName);
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        mergedCell = headerRowTemp.createCell(6);
        mergedCell.setCellValue("项目编号：" + serialNum);
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(2, 2, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(2, 2, 0, 10), sheet);
        //************************************************************
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 10));
        headerRowTemp = sheet.createRow(3);
        headerRowTemp.setHeight((short) 700); // 设置行高，这里设置为500个点
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("申请原因：");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(3, 3, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(3, 3, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(3, 3, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(3, 3, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(3, 3, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(3, 3, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(3, 3, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(3, 3, 0, 10), sheet);
        //************************************************************
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 5));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 6, 10));
        headerRowTemp = sheet.createRow(4);
        headerRowTemp.setHeight((short) 700); // 设置行高，这里设置为500个点
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("工种：");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        mergedCell = headerRowTemp.createCell(6);
        mergedCell.setCellValue("不含税工天价格（或每次等价格）：200元");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(4, 4, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(4, 4, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(4, 4, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(4, 4, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(4, 4, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(4, 4, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(4, 4, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(4, 4, 0, 10), sheet);

        String workInfo = "姓名、总天数：";
        for (SysReviewSubEmployee sysReviewEmployee : list) {
            workInfo = workInfo + sysReviewEmployee.getName() + "，总共" + sysReviewEmployee.getWorkDay() + "天、";
        }
        workInfo = workInfo.substring(0, workInfo.lastIndexOf("、"));

        //************************************************************
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 5));
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 10));
        headerRowTemp = sheet.createRow(5);
        headerRowTemp.setHeight((short) 2000); // 设置行高，这里设置为500个点
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue(workInfo);
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        mergedCell = headerRowTemp.createCell(6);
        mergedCell.setCellValue("确认工天价格方式：按劳务协议书\n确认人员签名：");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(5, 5, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(5, 5, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(5, 5, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(5, 5, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(5, 5, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(5, 5, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(5, 5, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(5, 5, 0, 10), sheet);
        //************************************************************
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 6, 10));
        headerRowTemp = sheet.createRow(6);
        headerRowTemp.setHeight((short) 2000); // 设置行高，这里设置为500个点
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("项目负责人：");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        mergedCell = headerRowTemp.createCell(6);
        mergedCell.setCellValue("分院分管生产副院长：");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(6, 6, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(6, 6, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(6, 6, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(6, 6, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(6, 6, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(6, 6, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(6, 6, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(6, 6, 0, 10), sheet);
        //************************************************************
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 5));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 6, 10));
        headerRowTemp = sheet.createRow(7);
        headerRowTemp.setHeight((short) 1000); // 设置行高，这里设置为500个点
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("分院长：");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        mergedCell = headerRowTemp.createCell(6);
        mergedCell.setCellValue("院分管领导：");
        mergedCell.setCellStyle(wrapCellStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(7, 7, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(7, 7, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(7, 7, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(7, 7, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(7, 7, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(7, 7, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(7, 7, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(7, 7, 0, 10), sheet);
        //************************************************************

        //************************************************************
        lowStyle.setAlignment(HorizontalAlignment.CENTER); // 设置水平居中对齐
        sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 10));
        headerRowTemp = sheet.createRow(8);
        headerRowTemp.setHeight((short) 1000); // 设置行高，这里设置为500个点
        mergedCell = headerRowTemp.createCell(0);
        mergedCell.setCellValue("注：劳务作业不得超过三个月，申请表由项目负责人提出申请，经审批后执行。");
        mergedCell.setCellStyle(lowStyle); // 应用居中对齐的样式
        RegionUtil.setBorderTop(BorderStyle.THIN, new CellRangeAddress(8, 8, 0, 10), sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), new CellRangeAddress(8, 8, 0, 10), sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, new CellRangeAddress(8, 8, 0, 10), sheet);
        RegionUtil.setRightBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(8, 8, 0, 10), sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, new CellRangeAddress(8, 8, 0, 10), sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(8, 8, 0, 10), sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, new CellRangeAddress(8, 8, 0, 10), sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.WHITE.getIndex(), new CellRangeAddress(8, 8, 0, 10), sheet);

        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/confirm_employee_info/{reviewId}")
    public AjaxResult confirmEmployeeInfo(@PathVariable Long reviewId)
    {
        //修改是否填写雇工信息
        sysReviewService.updateFinalHireByReviewId(reviewId);
        //修改填写雇工审核状态
        sysReviewProcessService.updateStatusByUserIdReviewId(reviewId);
        //重新开启审核
        SysReviewSubProcess sysReviewProcess = new SysReviewSubProcess();
        sysReviewProcess.setReviewId(reviewId);
        List<SysReviewSubProcess> list = sysReviewProcessService.selectSysReviewSubProcessList(sysReviewProcess);
        Long reviewProcessId = list.get(list.size()-3).getReviewProcessId();
        sysReviewProcessService.reSetStatusByReviewProcessId(reviewProcessId);
        return toAjax(1);
    }
}