package com.kcylog.system.service.impl;

import com.kcylog.system.service.IJzdProcessService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class JzdProcessServiceImpl implements IJzdProcessService {

    // 模板Excel相关常量
    private static final int TEMPLATE_START_TITLE_ROW = 0;
    private static final int TEMPLATE_START_DATA_ROW = 4; // 模板数据起始行索引(0-based)
    private static final int TEMPLATE_END_DATA_ROW = 41; // 模板数据结束行索引(0-based)
    private static final int TEMPLATE_DATA_ROWS = TEMPLATE_END_DATA_ROW - TEMPLATE_START_DATA_ROW + 1; // 模板可容纳数据行数
    private static final int TITLE_MERGE_FIRST_ROW = 0; // 标题合并单元格起始行
    private static final int TITLE_MERGE_LAST_ROW = 1; // 标题合并单元格结束行
    private static final int TITLE_MERGE_FIRST_COL = 1; // 标题合并单元格起始列
    private static final int TITLE_MERGE_LAST_COL = 5; // 标题合并单元格结束列
    private static final int FIELD_START_ROW = 2; // 字段行起始行
    private static final int FIELD_END_ROW = 3; // 字段行结束行
    private static final int DATA_START_COL = 1; // 数据起始列
    private static final int DATA_END_COL = 5; // 数据结束列
    private static final int TOTAL_ROWS = 48;

    // 模板路径，从配置文件读取
    @Value("${jzd.template.path}")
    private String templatePath;

    public ByteArrayOutputStream processMultipleExcels(List<MultipartFile> files, String baseTitle) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            byte[] buffer = new byte[1024];

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                Workbook resultWorkbook = processSingleExcel(file, baseTitle);
                String fileName = "";

                if (baseTitle == null) {
                    baseTitle = "";
                    String[] fileNameSplits = file.getOriginalFilename().split(".");
                    StringBuilder baseTitleBuilder = new StringBuilder(baseTitle);
                    for (int j = 0; j < fileNameSplits.length - 1; j++) {
                        baseTitleBuilder.append(fileNameSplits[j]);
                    }
                    baseTitle = baseTitleBuilder.toString();
                    fileName = baseTitle + "界址点成果表" + ".xlsx";
                }
                else {
                    fileName = baseTitle + "界址点成果表_" + (i + 1) + ".xlsx";
                }
                ZipEntry zipEntry = new ZipEntry(fileName);
                zos.putNextEntry(zipEntry);

                resultWorkbook.write(zos);
                zos.closeEntry();
            }
        }
        return baos;
    }

    @Autowired
    private ResourceLoader resourceLoader;
    public Workbook processSingleExcel(MultipartFile file, String baseTitle) throws Exception {
        // 1. 读取上传的Excel数据
        List<List<String>> uploadData = readUploadExcel(file);
        if (uploadData.isEmpty()) {
            throw new IllegalArgumentException("上传文件中没有有效数据");
        }
//        String title = uploadData.get(0).get(0); // 首行首个数据作为标题
        List<List<String>> dataRows = uploadData.subList(1, uploadData.size()); // 排除标题行的实际数据

        Workbook resultWorkbook = new XSSFWorkbook();
        // 2. 读取模板Excel
        try (InputStream templateIs = resourceLoader.getResource(templatePath).getInputStream();
             Workbook templateWorkbook = new XSSFWorkbook(templateIs);
             ) {

            if (baseTitle == null) {
                baseTitle = "";
                String[] fileNameSplits = file.getOriginalFilename().split(".");
                StringBuilder baseTitleBuilder = new StringBuilder(baseTitle);
                for (int i = 0; i < fileNameSplits.length - 1; i++) {
                    baseTitleBuilder.append(fileNameSplits[i]);
                }
                baseTitle = baseTitleBuilder.toString();
            }

            Sheet templateSheet = templateWorkbook.getSheetAt(0);
            Sheet resultSheet = resultWorkbook.createSheet(templateSheet.getSheetName());

            // 3. 计算需要拆分的页数
            int totalDataCount = dataRows.size();
            int pageCount = (int) Math.ceil((double) totalDataCount / TEMPLATE_DATA_ROWS);

            // 4. 处理每一页
            for (int page = 1; page <= pageCount; page++) {
                // 计算当前页的数据范围
                int startIdx = (page - 1) * TEMPLATE_DATA_ROWS;
                int endIdx = Math.min(startIdx + TEMPLATE_DATA_ROWS, totalDataCount);
                List<List<String>> currentPageData = dataRows.subList(startIdx, endIdx);

                // 复制模板内容到结果表
                copyTemplateContent(templateSheet, resultSheet, resultSheet.getLastRowNum() + 1);

                // 设置当前页标题
                setPageTitle(resultSheet, baseTitle, page, (page - 1) * TOTAL_ROWS + TEMPLATE_START_TITLE_ROW);

                // 填充当前页数据
                fillPageData(resultSheet, currentPageData, (page - 1) * TOTAL_ROWS + TEMPLATE_START_DATA_ROW);
            }
            for (int i = 0; i <= DATA_END_COL; i++) {
                resultSheet.setColumnWidth(i, templateSheet.getColumnWidth(i));
            }
        }
        return resultWorkbook;
    }

    /**
     * 读取上传的Excel文件数据
     */
    private List<List<String>> readUploadExcel(MultipartFile file) throws Exception {
        List<List<String>> result = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    cell.setCellType(CellType.STRING);
                    rowData.add(cell.getStringCellValue() != null ? cell.getStringCellValue().trim() : "");
                }
                if (!rowData.isEmpty()) {
                    result.add(rowData);
                }
            }
        }

        return result;
    }

    /**
     * 复制模板内容到结果表
     */
    private void copyTemplateContent(Sheet templateSheet, Sheet resultSheet, int startRow) {
        // 复制行内容
        for (int i = 0; i <= TEMPLATE_END_DATA_ROW + 6; i++) { // 包含空行
            Row templateRow = templateSheet.getRow(i);
            Row newRow = resultSheet.createRow(startRow + i);

            if (templateRow != null) {
                for (int j = 0; j <= DATA_END_COL; j++) {
                    Cell templateCell = templateRow.getCell(j);
                    if (templateCell != null) {
                        Cell newCell = newRow.createCell(j);
                        // 复制单元格值和样式
                        newCell.setCellValue(templateCell.getStringCellValue());
//                        newCell.setCellStyle(templateCell.getCellStyle());
                        CellStyle cellStyle = resultSheet.getWorkbook().createCellStyle();
                        cellStyle.cloneStyleFrom(templateCell.getCellStyle());
                        newCell.setCellStyle(cellStyle);
                    }
                }
            }
        }

        // 复制合并单元格
        for (int i = 0; i < templateSheet.getNumMergedRegions(); i++) {
            CellRangeAddress cra = templateSheet.getMergedRegion(i);
            CellRangeAddress newCra = new CellRangeAddress(
                    cra.getFirstRow() + startRow,
                    cra.getLastRow() + startRow,
                    cra.getFirstColumn(),
                    cra.getLastColumn()
            );
            resultSheet.addMergedRegion(newCra);
        }
    }

    /**
     * 设置页面标题
     */
    private void setPageTitle(Sheet sheet, String title, int pageNum, int startRow) {
        // 创建标题字符串
        String pageTitle = title + "界址点坐标成果表（2000国家大地坐标系） 表三（" + pageNum + "）";

        // 获取标题单元格并设置值
        Row titleRow = sheet.getRow(startRow + TITLE_MERGE_FIRST_ROW);
        if (titleRow == null) {
            titleRow = sheet.createRow(startRow + TITLE_MERGE_FIRST_ROW);
        }

        Cell titleCell = titleRow.getCell(TITLE_MERGE_FIRST_COL);
        if (titleCell == null) {
            titleCell = titleRow.createCell(TITLE_MERGE_FIRST_COL);
        }

        titleCell.setCellValue(pageTitle);
    }

    /**
     * 填充页面数据
     */
    private void fillPageData(Sheet sheet, List<List<String>> data, int startRow) {
        for (int i = 0; i < data.size(); i++) {
            List<String> rowData = data.get(i);
            Row row = sheet.getRow(startRow + i);
            if (row == null) {
                row = sheet.createRow(startRow + i);
            }

            // 填充数据到对应列（点号、X坐标、Y坐标、反算边长、备注）
            for (int j = 0; j < Math.min(rowData.size(), 5); j++) {
                Cell cell = row.getCell(DATA_START_COL + j);
                if (cell == null) {
                    cell = row.createCell(DATA_START_COL + j);
                }
                cell.setCellValue(rowData.get(j));
            }
        }
    }
}