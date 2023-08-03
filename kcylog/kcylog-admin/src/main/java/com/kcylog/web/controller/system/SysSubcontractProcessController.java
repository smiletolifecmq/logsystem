package com.kcylog.web.controller.system;

import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.system.domain.SysSubcontractProcess;
import com.kcylog.system.service.ISysSubcontractProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分包审核单流程Controller
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@RestController
@RequestMapping("/system/subcontractProcess")
public class SysSubcontractProcessController extends BaseController
{
    @Autowired
    private ISysSubcontractProcessService sysSubcontractProcessService;

    /**
     * 查询分包审核单流程列表
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcess:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSubcontractProcess sysSubcontractProcess)
    {
        startPage();
        List<SysSubcontractProcess> list = sysSubcontractProcessService.selectSysSubcontractProcessList(sysSubcontractProcess);
        return getDataTable(list);
    }
}
