package com.kcylog.web.controller.system;

import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.system.domain.SysReviewProcess;
import com.kcylog.system.domain.SysReviewSubProcess;
import com.kcylog.system.service.ISysReviewProcessService;
import com.kcylog.system.service.ISysReviewSubProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 审核单流程Controller
 */
@RestController
@RequestMapping("/system/process")
public class SysReviewProcessController extends BaseController
{
    @Autowired
    private ISysReviewProcessService sysReviewProcessService;

    @Autowired
    private ISysReviewSubProcessService sysReviewSubProcessService;

    /**
     * 查询审核单流程列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysReviewProcess sysReviewProcess)
    {
        List<SysReviewProcess> list = sysReviewProcessService.selectSysReviewProcessList(sysReviewProcess);
        return getDataTable(list);
    }

    @GetMapping("/subList")
    public TableDataInfo subList(SysReviewSubProcess sysReviewSubProcess)
    {
        List<SysReviewSubProcess> list = sysReviewSubProcessService.selectSysReviewSubProcessList(sysReviewSubProcess);
        return getDataTable(list);
    }
}
