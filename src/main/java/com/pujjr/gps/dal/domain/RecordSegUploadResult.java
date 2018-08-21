package com.pujjr.gps.dal.domain;

import java.util.Date;

public class RecordSegUploadResult {
    private String gpsNo;

    private String appId;

    private String returnCode;

    private String returnMsg;

    private Integer uploadTimes;

    private Date uploadDate;

    private Date reuploadDate;

    private String uploadData;

    private String returnData;

    public String getGpsNo() {
        return gpsNo;
    }

    public void setGpsNo(String gpsNo) {
        this.gpsNo = gpsNo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Integer getUploadTimes() {
        return uploadTimes;
    }

    public void setUploadTimes(Integer uploadTimes) {
        this.uploadTimes = uploadTimes;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getReuploadDate() {
        return reuploadDate;
    }

    public void setReuploadDate(Date reuploadDate) {
        this.reuploadDate = reuploadDate;
    }

    public String getUploadData() {
        return uploadData;
    }

    public void setUploadData(String uploadData) {
        this.uploadData = uploadData;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }
}