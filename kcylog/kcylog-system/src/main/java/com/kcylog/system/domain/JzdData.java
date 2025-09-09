package com.kcylog.system.domain;

import java.math.BigDecimal;

/**
 * 界址点数据模型
 * 对应上传Excel中的数据行（点号、X坐标、Y坐标、反算边长、备注）
 */
public class JzdData {
    /** 点号 */
    private String pointNo;
    /** X坐标 */
    private BigDecimal xCoord;
    /** Y坐标 */
    private BigDecimal yCoord;
    /** 反算边长 */
    private BigDecimal calcLength;
    /** 备注（默认空） */
    private String remark = "";

    public String getPointNo() {
        return pointNo;
    }

    public void setPointNo(String pointNo) {
        this.pointNo = pointNo;
    }

    public BigDecimal getXCoord() {
        return xCoord;
    }

    public void setXCoord(BigDecimal xCoord) {
        this.xCoord = xCoord;
    }

    public BigDecimal getYCoord() {
        return yCoord;
    }

    public void setYCoord(BigDecimal yCoord) {
        this.yCoord = yCoord;
    }

    public BigDecimal getCalcLength() {
        return calcLength;
    }

    public void setCalcLength(BigDecimal calcLength) {
        this.calcLength = calcLength;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}