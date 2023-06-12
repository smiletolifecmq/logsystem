package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.system.domain.SysProcessConfigInfo;
import com.kcylog.system.service.ISysProcessConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程配置过程Controller
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
@RestController
@RequestMapping("/system/processConfigInfo")
public class SysProcessConfigInfoController extends BaseController
{
    @Autowired
    private ISysProcessConfigInfoService sysProcessConfigInfoService;

    /**
     * 查询流程配置过程列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysProcessConfigInfo sysProcessConfigInfo)
    {
        startPage();
        List<SysProcessConfigInfo> list = sysProcessConfigInfoService.selectSysProcessConfigInfoList(sysProcessConfigInfo);
        return getDataTable(list);
    }

    /**
     * 获取流程配置过程详细信息
     */
    @GetMapping(value = "/{infoId}")
    public AjaxResult getInfo(@PathVariable("infoId") String infoId)
    {
        return success(sysProcessConfigInfoService.selectSysProcessConfigInfoByInfoId(infoId));
    }

    /**
     * 新增流程配置过程
     */
    @Log(title = "流程配置过程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProcessConfigInfo sysProcessConfigInfo)
    {
        return toAjax(sysProcessConfigInfoService.insertSysProcessConfigInfo(sysProcessConfigInfo));
    }

    /**
     * 修改流程配置过程
     */
    @Log(title = "流程配置过程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProcessConfigInfo sysProcessConfigInfo)
    {
        return toAjax(sysProcessConfigInfoService.updateSysProcessConfigInfo(sysProcessConfigInfo));
    }

    /**
     * 删除流程配置过程
     */
    @Log(title = "流程配置过程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable String[] infoIds)
    {
        return toAjax(sysProcessConfigInfoService.deleteSysProcessConfigInfoByInfoIds(infoIds));
    }
}
