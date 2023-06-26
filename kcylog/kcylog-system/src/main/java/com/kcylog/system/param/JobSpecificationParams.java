package com.kcylog.system.param;

public class JobSpecificationParams {
    private Long[] jobIds;
    private Long userId;
    public Long[] getJobIds() {
        return jobIds;
    }

    public void setJobIds(Long[] jobIds) {
        this.jobIds = jobIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
