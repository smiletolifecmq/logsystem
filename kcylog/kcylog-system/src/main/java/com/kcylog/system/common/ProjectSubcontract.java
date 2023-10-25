package com.kcylog.system.common;

import com.kcylog.common.annotation.Excel;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.Date;

public class ProjectSubcontract {
    @Excel(name = "工作内容", headerBackgroundColor = IndexedColors.INDIGO)
    private String workcontent;

    @Excel(name = "分包类型", headerBackgroundColor = IndexedColors.INDIGO, readConverterExp = "1=全部,2=局部")
    private Long subType;

    @Excel(name = "预估分包工作量", headerBackgroundColor = IndexedColors.INDIGO)
    private String subWorkload;

    @Excel(name = "实际分包工作量", headerBackgroundColor = IndexedColors.INDIGO)
    private String realWorkload;

    @Excel(name = "中签单位", headerBackgroundColor = IndexedColors.INDIGO)
    private String winUnit;

    @Excel(name = "抽签时间", dateFormat = "yyyy-MM-dd", headerBackgroundColor = IndexedColors.INDIGO)
    private Date lotTime;

    public String getWorkcontent() {
        return workcontent;
    }

    public void setWorkcontent(String workcontent) {
        this.workcontent = workcontent;
    }

    public Long getSubType() {
        return subType;
    }

    public void setSubType(Long subType) {
        this.subType = subType;
    }

    public String getSubWorkload() {
        return subWorkload;
    }

    public void setSubWorkload(String subWorkload) {
        this.subWorkload = subWorkload;
    }

    public String getRealWorkload() {
        return realWorkload;
    }

    public void setRealWorkload(String realWorkload) {
        this.realWorkload = realWorkload;
    }

    public String getWinUnit() {
        return winUnit;
    }

    public void setWinUnit(String winUnit) {
        this.winUnit = winUnit;
    }

    public Date getLotTime() {
        return lotTime;
    }

    public void setLotTime(Date lotTime) {
        this.lotTime = lotTime;
    }
}
