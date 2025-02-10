package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysClientele;
import com.kcylog.system.domain.SysClienteleJtinfo;
import com.kcylog.system.domain.SysClienteleLlinfo;
import com.kcylog.system.domain.SysClienteleProjectinfo;
import com.kcylog.system.service.ISysClienteleJtinfoService;
import com.kcylog.system.service.ISysClienteleLlinfoService;
import com.kcylog.system.service.ISysClienteleProjectinfoService;
import com.kcylog.system.service.ISysClienteleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 客户信息Controller
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
@RestController
@RequestMapping("/system/clientele")
public class SysClienteleController extends BaseController
{
    @Autowired
    private ISysClienteleService sysClienteleService;

    @Autowired
    private ISysClienteleJtinfoService sysClienteleJtinfoService;

    @Autowired
    private ISysClienteleLlinfoService sysClienteleLlinfoService;

    @Autowired
    private ISysClienteleProjectinfoService sysClienteleProjectinfoService;

    /**
     * 查询客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:clientele:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysClientele sysClientele)
    {
        startPage();
        List<SysClientele> list = sysClienteleService.selectSysClienteleList(sysClientele);
        return getDataTable(list);
    }

    /**
     * 导出客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:clientele:export')")
    @Log(title = "客户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysClientele sysClientele)
    {
        List<SysClientele> list = sysClienteleService.selectSysClienteleList(sysClientele);
        ExcelUtil<SysClientele> util = new ExcelUtil<SysClientele>(SysClientele.class);
        util.exportExcel(response, list, "客户信息数据");
    }

    /**
     * 获取客户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:clientele:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysClienteleService.selectSysClienteleById(id));
    }

    /**
     * 新增客户信息
     */
    @PreAuthorize("@ss.hasPermi('system:clientele:add')")
    @Log(title = "客户信息", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody SysClientele sysClientele)
    {
        Long userId = SecurityUtils.getUserId();
        String userName = SecurityUtils.getUsername();
        sysClientele.setCreateid(userId);
        sysClientele.setCreatename(userName);

        sysClienteleService.insertSysClientele(sysClientele);
        for (SysClienteleJtinfo obj1 : sysClientele.getJtInfo()) {
            obj1.setClienteleId(sysClientele.getId());
            sysClienteleJtinfoService.insertSysClienteleJtinfo(obj1);
        }

        for (SysClienteleLlinfo obj2 : sysClientele.getLlInfo()) {
            obj2.setClienteleId(sysClientele.getId());
            sysClienteleLlinfoService.insertSysClienteleLlinfo(obj2);
        }

        for (SysClienteleProjectinfo obj3 : sysClientele.getProjectInfo()) {
            obj3.setClienteleId(sysClientele.getId());
            sysClienteleProjectinfoService.insertSysClienteleProjectinfo(obj3);
        }
        return toAjax(1);
    }

    /**
     * 修改客户信息
     */
    @PreAuthorize("@ss.hasPermi('system:clientele:edit')")
    @Log(title = "客户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    public AjaxResult edit(@RequestBody SysClientele sysClientele)
    {
        sysClienteleJtinfoService.deleteSysClienteleJtinfoById(sysClientele.getId());
        sysClienteleLlinfoService.deleteSysClienteleLlinfoById(sysClientele.getId());
        sysClienteleProjectinfoService.deleteSysClienteleProjectinfoById(sysClientele.getId());
        for (SysClienteleJtinfo obj1 : sysClientele.getJtInfo()) {
            obj1.setClienteleId(sysClientele.getId());
            sysClienteleJtinfoService.insertSysClienteleJtinfo(obj1);
        }

        for (SysClienteleLlinfo obj2 : sysClientele.getLlInfo()) {
            obj2.setClienteleId(sysClientele.getId());
            sysClienteleLlinfoService.insertSysClienteleLlinfo(obj2);
        }

        for (SysClienteleProjectinfo obj3 : sysClientele.getProjectInfo()) {
            obj3.setClienteleId(sysClientele.getId());
            sysClienteleProjectinfoService.insertSysClienteleProjectinfo(obj3);
        }
        return toAjax(sysClienteleService.updateSysClientele(sysClientele));
    }

    /**
     * 删除客户信息
     */
    @PreAuthorize("@ss.hasPermi('system:clientele:remove')")
    @Log(title = "客户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysClienteleService.deleteSysClienteleByIds(ids));
    }
}
