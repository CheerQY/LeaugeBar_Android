//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sicnu.cheer.generalmodule.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class YKTUser implements Serializable {
    private static final long serialVersionUID = 8791246517280613816L;
    private String uId;
    /** @deprecated */
    @Deprecated
    private String username;
    private String password;
    private String jobNumber;
    private String name;
    private String mobile;
    private String email;
    private String qq;
    private String position;
    private Map<String, String> positionMap;
    private String isLocked;
    private String createUser;
    private Date createDate;
    private String modifyUser;
    private Date modifyDate;
    private String depNo;
    private YKTOrganization organization;
    private List<YKTOrganization> allOrgs;
    private String orgAllName;
    private YKTOrganization company;
    private List<YKTCard> cardsList;
    private YKTAttachments headPortrait;
    private String channelId;
    private String deviceType;
    private List settingsList;
    private Map settingsMap;
    private String checkPhone;

    public YKTUser() {
    }

    public String getuId() {
        return this.uId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getJobNumber() {
        return this.jobNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public String getQq() {
        return this.qq;
    }

    public String getPosition() {
        return this.position;
    }

    public Map<String, String> getPositionMap() {
        return this.positionMap;
    }

    public String getIsLocked() {
        return this.isLocked;
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

    public String getDepNo() {
        return this.depNo;
    }

    public YKTOrganization getOrganization() {
        return this.organization;
    }

    public String getOrgAllName() {
        return this.orgAllName;
    }

    public List<YKTOrganization> getAllOrgs() {
        return this.allOrgs;
    }

    public YKTOrganization getCompany() {
        return this.company;
    }

    public List<YKTCard> getCardsList() {
        return this.cardsList;
    }

    public List getSettingsList() {
        return this.settingsList;
    }

    public Map getSettingsMap() {
        return this.settingsMap;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPositionMap(Map<String, String> positionMap) {
        this.positionMap = positionMap;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
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

    public void setDepNo(String depNo) {
        this.depNo = depNo;
    }

    public void setOrganization(YKTOrganization organization) {
        this.organization = organization;
    }

    public void setOrgAllName(String orgAllName) {
        this.orgAllName = orgAllName;
    }

    public void setAllOrgs(List<YKTOrganization> allOrgs) {
        this.allOrgs = allOrgs;
    }

    public void setCompany(YKTOrganization company) {
        this.company = company;
    }

    public void setCardsList(List<YKTCard> cardsList) {
        this.cardsList = cardsList;
    }

    public YKTAttachments getHeadPortrait() {
        return this.headPortrait;
    }

    public void setHeadPortrait(YKTAttachments headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setSettingsList(List settingsList) {
        this.settingsList = settingsList;
    }

    public void setSettingsMap(Map settingsMap) {
        this.settingsMap = settingsMap;
    }

    public String getCheckPhone() {
        return this.checkPhone;
    }

    public void setCheckPhone(String checkPhone) {
        this.checkPhone = checkPhone;
    }
}
