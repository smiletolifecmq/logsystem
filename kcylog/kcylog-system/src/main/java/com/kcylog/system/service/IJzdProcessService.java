package com.kcylog.system.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 界址点Excel处理服务接口
 */
public interface IJzdProcessService {

    /**
     * 处理单个上传Excel文件
     * @param file 上传文件
     * @param baseTitle 标题前缀
     * @return 处理后的Excel工作簿
     */
    public Workbook processSingleExcel(MultipartFile file, String baseTitle) throws Exception;

    /**
     * 处理多个上传Excel文件，打包为ZIP
     * @param files 上传文件列表
     * @param baseTitle 标题前缀
     * @return ZIP字节数组
     */
    ByteArrayOutputStream processMultipleExcels(List<MultipartFile> files, String baseTitle) throws Exception;
}