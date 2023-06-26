package com.kcylog.system.param;

public class KnowledgeShareParams {
    private Long[] knowledgeIds;
    private Long userId;
    public Long[] getKnowledgeIds() {
        return knowledgeIds;
    }

    public void setKnowledgeIds(Long[] knowledgeIds) {
        this.knowledgeIds = knowledgeIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

