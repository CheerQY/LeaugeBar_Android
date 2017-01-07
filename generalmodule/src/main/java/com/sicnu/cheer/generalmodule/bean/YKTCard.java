//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sicnu.cheer.generalmodule.bean;

import java.io.Serializable;
import java.util.Date;

public class YKTCard implements Serializable {
    private static final long serialVersionUID = 2693973297157328320L;
    private String cId;
    private String cardSerial;
    private String cardNo;
    private String cardSource;
    private String cardStatus;
    private String bindingModule;
    private String isBinding;
    private String username;
    private String jobNumber;
    private String position;
    private String depName;
    private String depNo;
    private String depId;
    private String createUser;
    private Date createDate;
    private String modifyUser;
    private Date modifyDate;
    private YKTUser yktUser;

    public YKTCard() {
    }

    public String getcId() {
        return this.cId;
    }

    public String getCardSerial() {
        return this.cardSerial;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public String getCardSource() {
        return this.cardSource;
    }

    public String getCardStatus() {
        return this.cardStatus;
    }

    public String getBindingModule() {
        return this.bindingModule;
    }

    public String getIsBinding() {
        return this.isBinding;
    }

    public String getUsername() {
        return this.username;
    }

    public String getJobNumber() {
        return this.jobNumber;
    }

    public String getPosition() {
        return this.position;
    }

    public String getDepName() {
        return this.depName;
    }

    public String getDepNo() {
        return this.depNo;
    }

    public String getDepId() {
        return this.depId;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public String getModifyUser() {
        return this.modifyUser;
    }

    public Date getModifyDate() {
        return this.modifyDate;
    }

    public YKTUser getYktUser() {
        return this.yktUser;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public void setCardSerial(String cardSerial) {
        this.cardSerial = cardSerial;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setCardSource(String cardSource) {
        this.cardSource = cardSource;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public void setBindingModule(String bindingModule) {
        this.bindingModule = bindingModule;
    }

    public void setIsBinding(String isBinding) {
        this.isBinding = isBinding;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setDepNo(String depNo) {
        this.depNo = depNo;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setYktUser(YKTUser yktUser) {
        this.yktUser = yktUser;
    }
}
