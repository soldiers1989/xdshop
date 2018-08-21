package com.pujjr.gps.dal.domain;

import java.util.Date;

public class GpsInstoreHis {
    private String instoreHisId;

    private String receiveId;

    private String storeId;

    private String createAccountId;

    private Date createTime;

    public String getInstoreHisId() {
        return instoreHisId;
    }

    public void setInstoreHisId(String instoreHisId) {
        this.instoreHisId = instoreHisId;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCreateAccountId() {
        return createAccountId;
    }

    public void setCreateAccountId(String createAccountId) {
        this.createAccountId = createAccountId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}