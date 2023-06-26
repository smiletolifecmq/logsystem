package com.kcylog.system.param;

public class WorkbookParams {
    private Long[] workbookIds;
    private Long userId;
    public Long[] getWorkbookIds() {
        return workbookIds;
    }

    public void setWorkbookIds(Long[] workbookIds) {
        this.workbookIds = workbookIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
