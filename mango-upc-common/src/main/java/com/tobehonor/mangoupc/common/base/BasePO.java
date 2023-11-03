package com.tobehonor.mangoupc.common.base;

import java.util.Date;

/**
 * Base PO
 *
 * @author William Chan
 * @since 2023/7/10
 */
public class BasePO {
    
    private Long createdBy;
    
    private Date creationTime;
    
    private Long lastUpdatedBy;
    
    private Date lastUpdateTime;
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreationTime() {
        return this.creationTime == null ? null : (Date) this.creationTime.clone();
    }
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime == null ? null : (Date) creationTime.clone();
    }
    
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    public Date getLastUpdateTime() {
        return this.lastUpdateTime == null ? null : (Date) this.lastUpdateTime.clone();
    }
    
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime == null ? null : (Date) lastUpdateTime.clone();
    }
}
