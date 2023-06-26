package com.kcylog.system.param;

public class TechnicalUpdateParams {
    private Long[] technicalIds;
    private Long userId;
    public Long[] getTechnicalIds() {
        return technicalIds;
    }

    public void setTechnicalIds(Long[] technicalIds) {
        this.technicalIds = technicalIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
