package com.kcylog.web.controller.system;

import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.system.domain.SysCarReviewProcess;
import com.kcylog.system.service.ISysCarReviewProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 车辆使用审核单流程Controller
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@RestController
@RequestMapping("/system/carReviewProcess")
public class SysCarReviewProcessController extends BaseController
{
    @Autowired
    private ISysCarReviewProcessService sysCarReviewProcessService;

    /**
     * 查询车辆使用审核单流程列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysCarReviewProcess sysCarReviewProcess)
    {
        startPage();
        List<SysCarReviewProcess> list = sysCarReviewProcessService.selectSysCarReviewProcessList(sysCarReviewProcess);
        return getDataTable(list);
    }
}
