package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoAssess;
import com.kcylog.system.service.ISysGeoAssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 评定表Controller
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
@RestController
@RequestMapping("/system/geoAssess")
public class SysGeoAssessController extends BaseController
{
    @Autowired
    private ISysGeoAssessService sysGeoAssessService;

    /**
     * 查询评定表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoAssess sysGeoAssess)
    {
        startPage();
        List<SysGeoAssess> list = sysGeoAssessService.selectSysGeoAssessList(sysGeoAssess);
        return getDataTable(list);
    }

    /**
     * 导出评定表列表
     */
    @Log(title = "评定表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoAssess sysGeoAssess)
    {
        List<SysGeoAssess> list = sysGeoAssessService.selectSysGeoAssessList(sysGeoAssess);
        ExcelUtil<SysGeoAssess> util = new ExcelUtil<SysGeoAssess>(SysGeoAssess.class);
        util.exportExcel(response, list, "评定表数据");
    }

    /**
     * 获取评定表详细信息
     */
    @GetMapping(value = "/{assessId}")
    public AjaxResult getInfo(@PathVariable("assessId") Long assessId)
    {
        return success(sysGeoAssessService.selectSysGeoAssessByAssessId(assessId));
    }

    /**
     * 新增评定表
     */
    @Log(title = "评定表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoAssess sysGeoAssess)
    {
        return toAjax(sysGeoAssessService.insertSysGeoAssess(sysGeoAssess));
    }

    /**
     * 修改评定表
     */
    @Log(title = "评定表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoAssess sysGeoAssess)
    {
        return toAjax(sysGeoAssessService.updateSysGeoAssess(sysGeoAssess));
    }

    /**
     * 删除评定表
     */
    @Log(title = "评定表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{assessIds}")
    public AjaxResult remove(@PathVariable Long[] assessIds)
    {
        return toAjax(sysGeoAssessService.deleteSysGeoAssessByAssessIds(assessIds));
    }
}
