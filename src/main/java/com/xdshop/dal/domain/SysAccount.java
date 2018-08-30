package com.xdshop.dal.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "userId"})
public class SysAccount {
    private String id;

    private String accountId;

    private String accountName;

    private String password;

    private String branchId;

    private String postId;

    private String mobile;

    private String weixinId;

    private String email;

    private String status;

    private String loginStatus;

    private Date lastHeartbeatTime;

    private Date createTime;

    private String createId;

    private Date updateTime;

    private String updateId;

    private String reserver1;

    private String reserver2;

    private String reserver3;

    private String reserver4;

    private String reserver5;

    private Boolean invokeCallcenter;

    private String callcenterLoginPasswd;

    private String callcenterExtensionTelephone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Date getLastHeartbeatTime() {
        return lastHeartbeatTime;
    }

    public void setLastHeartbeatTime(Date lastHeartbeatTime) {
        this.lastHeartbeatTime = lastHeartbeatTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getReserver1() {
        return reserver1;
    }

    public void setReserver1(String reserver1) {
        this.reserver1 = reserver1;
    }

    public String getReserver2() {
        return reserver2;
    }

    public void setReserver2(String reserver2) {
        this.reserver2 = reserver2;
    }

    public String getReserver3() {
        return reserver3;
    }

    public void setReserver3(String reserver3) {
        this.reserver3 = reserver3;
    }

    public String getReserver4() {
        return reserver4;
    }

    public void setReserver4(String reserver4) {
        this.reserver4 = reserver4;
    }

    public String getReserver5() {
        return reserver5;
    }

    public void setReserver5(String reserver5) {
        this.reserver5 = reserver5;
    }

    public Boolean getInvokeCallcenter() {
        return invokeCallcenter;
    }

    public void setInvokeCallcenter(Boolean invokeCallcenter) {
        this.invokeCallcenter = invokeCallcenter;
    }

    public String getCallcenterLoginPasswd() {
        return callcenterLoginPasswd;
    }

    public void setCallcenterLoginPasswd(String callcenterLoginPasswd) {
        this.callcenterLoginPasswd = callcenterLoginPasswd;
    }

    public String getCallcenterExtensionTelephone() {
        return callcenterExtensionTelephone;
    }

    public void setCallcenterExtensionTelephone(String callcenterExtensionTelephone) {
        this.callcenterExtensionTelephone = callcenterExtensionTelephone;
    }
}