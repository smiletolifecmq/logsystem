package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.FqProjectProcess;
import com.kcylog.system.domain.SjInfo;
import com.kcylog.system.domain.SysSending;
import com.kcylog.system.domain.SysVersionHandover;
import com.kcylog.system.service.IFqProjectProcessService;
import com.kcylog.system.service.ISysSendingService;
import com.kcylog.system.service.ISysVersionHandoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 送件Controller
 * 
 * @author ruoyi
 * @date 2025-10-29
 */
@RestController
@RequestMapping("/system/sending")
public class SysSendingController extends BaseController
{
    @Autowired
    private ISysSendingService sysSendingService;

    @Autowired
    private IFqProjectProcessService fqProjectProcessService;

    @Autowired
    private ISysVersionHandoverService sysVersionHandoverService;

    /**
     * 查询送件列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysSending sysSending)
    {
        startPage();
        List<SysSending> list = sysSendingService.selectSysSendingList(sysSending);
        return getDataTable(list);
    }

    /**
     * 导出送件列表
     */
    @Log(title = "送件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSending sysSending)
    {
        List<SysSending> list = sysSendingService.selectSysSendingList(sysSending);
        ExcelUtil<SysSending> util = new ExcelUtil<SysSending>(SysSending.class);
        util.exportExcel(response, list, "送件数据");
    }

    /**
     * 获取送件详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysSendingService.selectSysSendingById(id));
    }

    /**
     * 新增送件
     */
    @Log(title = "送件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSending sysSending)
    {
        if (sysSending.getSjInfo() == null || sysSending.getSjInfo().size() == 0){
            return error("请填写送件信息");
        }
        SysSending newObj = new SysSending();
        newObj.setProjectId(sysSending.getProjectId());
        newObj.setSjsj(sysSending.getSjsj());
        newObj.setBz(sysSending.getBz());
        newObj.setVer(sysSending.getVer());
        newObj.setLxfs("");
        for (SjInfo obj : sysSending.getSjInfo()){
            newObj.setLxfs(newObj.getLxfs() + obj.getLx() + "：" + obj.getFs() + "；");
        }
        List<SysSending> list = sysSendingService.selectSysSendingList(newObj);
        if (list != null &&  list.size() > 0){
            return error("当前版本送件已存在，不可以重复提交～");
        }

        FqProjectProcess fqProjectProcess = new FqProjectProcess();
        fqProjectProcess.setVersion(sysSending.getVer());
        fqProjectProcess.setProjectId(sysSending.getProjectId());
        List<FqProjectProcess> obj = fqProjectProcessService.selectFqProjectProcessListInfo(fqProjectProcess);
        if ((obj == null || obj.size() == 0) && Objects.equals(sysSending.getVer(), "V1")){
            return error("提交失败，不存在该版本的项目移交信息～");
        }
        if (!Objects.equals(sysSending.getVer(), "V1")){
            fqProjectProcess = new FqProjectProcess();
            fqProjectProcess.setProjectId(sysSending.getProjectId());
            obj = fqProjectProcessService.selectFqProjectProcessListInfo(fqProjectProcess);
            if (obj == null || obj.size() == 0){
                return error("提交失败，不存在该版本的项目移交信息～");
            }else {
                SysVersionHandover sysVersionHandover = new SysVersionHandover();
                sysVersionHandover.setProcessId(obj.get(0).getId());
                sysVersionHandover.setVer(sysSending.getVer());
                List<SysVersionHandover> listVer = sysVersionHandoverService.selectSysVersionHandoverList(sysVersionHandover);
                if (listVer == null || listVer.size() == 0){
                    return error("提交失败，不存在该版本的项目移交信息～");
                }
            }
        }
        return toAjax(sysSendingService.insertSysSending(newObj));
    }

    /**
     * 修改送件
     */
    @Log(title = "送件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSending sysSending)
    {
        return toAjax(sysSendingService.updateSysSending(sysSending));
    }

    /**
     * 删除送件
     */
    @Log(title = "送件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysSendingService.deleteSysSendingByIds(ids));
    }
}
