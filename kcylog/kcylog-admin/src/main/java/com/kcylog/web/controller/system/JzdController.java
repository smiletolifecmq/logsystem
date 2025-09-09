package com.kcylog.web.controller.system;

import com.kcylog.common.core.controller.BaseController;
import com.kcylog.system.service.IJzdProcessService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/gis/jzd")
//@CrossOrigin(origins = "*")
public class JzdController extends BaseController {

    @Autowired
    private IJzdProcessService jzdProcessService;

    /**
     * 单文件上传处理 - 返回Excel文件
     * @param files 上传的Excel文件
     * @param baseTitle 标题前缀（参数title）
     * @return 响应Excel文件
     */
    @PostMapping("/process")
    public ResponseEntity<byte[]> uploadSingleExcel(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(required = false, name = "baseTitle") String baseTitle) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        if(files.size() == 1) {
            Workbook workbook = jzdProcessService.processSingleExcel(files.get(0), baseTitle);
            workbook.write(baos);

            String originalFileName = files.get(0).getOriginalFilename();
            String downloadFileName = originalFileName.replaceAll("\\.(xls|xlsx)$", "_输出.xlsx");
            String encodedFileName = URLEncoder.encode(downloadFileName, StandardCharsets.UTF_8.name());

            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", encodedFileName);
        } else if (files.size() > 1) {
            baos = jzdProcessService.processMultipleExcels(files, baseTitle);


            String zipFileName = "界址点Excel处理结果_" + System.currentTimeMillis() + ".zip";
            String encodedZipName = URLEncoder.encode(zipFileName, StandardCharsets.UTF_8.name());

            headers.setContentType(MediaType.parseMediaType("application/zip"));
            headers.setContentDispositionFormData("attachment", encodedZipName);
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }

}