package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.annotation.Anonymous;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysWebsite;
import com.kcylog.system.service.ISysWebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 网站整合Controller
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
@RestController
@RequestMapping("/system/website")
public class SysWebsiteController extends BaseController
{
    @Autowired
    private ISysWebsiteService sysWebsiteService;

    /**
     * 查询网站整合列表
     */
    @PreAuthorize("@ss.hasPermi('system:website:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWebsite sysWebsite)
    {
        startPage();
        List<SysWebsite> list = sysWebsiteService.selectSysWebsiteList(sysWebsite);
        return getDataTable(list);
    }

    /**
     * 导出网站整合列表
     */
    @PreAuthorize("@ss.hasPermi('system:website:export')")
    @Log(title = "网站整合", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysWebsite sysWebsite)
    {
        List<SysWebsite> list = sysWebsiteService.selectSysWebsiteList(sysWebsite);
        ExcelUtil<SysWebsite> util = new ExcelUtil<SysWebsite>(SysWebsite.class);
        util.exportExcel(response, list, "网站整合数据");
    }

    /**
     * 获取网站整合详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:website:query')")
    @GetMapping(value = "/{webId}")
    public AjaxResult getInfo(@PathVariable("webId") String webId)
    {
        return success(sysWebsiteService.selectSysWebsiteByWebId(webId));
    }

    /**
     * 新增网站整合
     */
    @PreAuthorize("@ss.hasPermi('system:website:add')")
    @Log(title = "网站整合", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWebsite sysWebsite)
    {
        return toAjax(sysWebsiteService.insertSysWebsite(sysWebsite));
    }

    /**
     * 修改网站整合
     */
    @PreAuthorize("@ss.hasPermi('system:website:edit')")
    @Log(title = "网站整合", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWebsite sysWebsite)
    {
        return toAjax(sysWebsiteService.updateSysWebsite(sysWebsite));
    }

    /**
     * 删除网站整合
     */
    @PreAuthorize("@ss.hasPermi('system:website:remove')")
    @Log(title = "网站整合", businessType = BusinessType.DELETE)
	@DeleteMapping("/{webIds}")
    public AjaxResult remove(@PathVariable String[] webIds)
    {
        return toAjax(sysWebsiteService.deleteSysWebsiteByWebIds(webIds));
    }

    /**
     * 头像上传
     */
    @Anonymous
    @PostMapping("/weburl")
    public AjaxResult weburl(@RequestParam("file") MultipartFile file) throws Exception
    {
        if (!file.isEmpty())
        {
            String webImgUrl = com.kcylog.common.utils.file.FileUploadUtils.upload(com.kcylog.common.config.RuoYiConfig.getAvatarPath(), file, com.kcylog.common.utils.file.MimeTypeUtils.IMAGE_EXTENSION);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", webImgUrl);
            return ajax;
        }
        return error("上传图片异常，请联系管理员");
    }


    /**
     * 查询网站整合列表,给整合网站开的接口，不需要验证
     */
    @Anonymous
    @CrossOrigin
    @GetMapping("/web_list")
    public TableDataInfo webList(SysWebsite sysWebsite)
    {
        List<SysWebsite> list = sysWebsiteService.selectSysWebListByNum(sysWebsite);
        return getDataTable(list);
    }

    @Anonymous
    @CrossOrigin
    @PutMapping("/web_num")
    public AjaxResult editNum(@RequestBody SysWebsite sysWebsite)
    {
        return toAjax(sysWebsiteService.updateSysWebsiteNum(sysWebsite));
    }

}

