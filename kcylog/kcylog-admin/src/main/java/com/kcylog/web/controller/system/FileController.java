package com.kcylog.web.controller.system;


import com.kcylog.common.config.RuoYiConfig;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.utils.file.FileUploadUtils;
import com.kcylog.common.utils.file.FileUtils;
import com.kcylog.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.aspose.cad.Image;
import com.aspose.cad.fileformats.cad.CadImage;
import com.aspose.cad.imageoptions.DxfOptions;

import java.io.File;
import java.util.Locale;

@RestController
@RequestMapping("/system/file")
public class FileController extends BaseController
{
    @Autowired
    private ServerConfig serverConfig;

    @PostMapping
    public AjaxResult upload(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = "http://192.168.110.112:9090/prod-api" + fileName;

            String newFileNameString = fileName.replace("/profile/upload", "");
            // 获取上传文件的原始路径
            File uploadedFile = new File(filePath + newFileNameString);

            // 调用dwg2dxf方法将DWG文件转换为DXF文件
            File dxfFile = dwg2dxf(uploadedFile, uploadedFile.getPath());

            // 构建DXF文件的URL
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            ajax.put("dxfUrl", dxfFile.getPath());  // 返回DXF文件的URL
            ajax.put("dxfFileName", dxfFile.getName());  // 返回DXF文件名

            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    public File dwg2dxf(File file, String path){
        String dxfPath;
        if (file.getName().endsWith(".dwg")){
            dxfPath = path.replace(".dwg",".dxf");
        }else if (file.getName().endsWith(".DWG")){
            dxfPath = path.replace(".DWG",".dxf");
        }else {
            throw new RuntimeException("文件格式错误～");
        }
        Locale.setDefault(new Locale("zh", "CN"));
        File dxfFile = new File(dxfPath);
        CadImage cadImage = (CadImage) Image.load(file.getPath());
        cadImage.save(dxfFile.getPath(), new DxfOptions());
        return dxfFile;
    }
}
