//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sicnu.cheer.generalmodule.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class YKTOrganization implements Serializable {
    private static final long serialVersionUID = -7166431547942946910L;
    private String orgId;
    private String parentId;
    private String depName;
    private String depAllName;
    private String depNo;
    private String createUser;
    private Date createDate;
    private String modifyUser;
    private Date modifyDate;
    private List<YKTUser> usersList;
    private YKTAttachments headPortrait;

    public YKTOrganization() {
    }

    public String getOrgId() {
        return this.orgId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public String getDepName() {
        return this.depName;
    }

    public String getDepAllName() {
        return this.depAllName;
    }

    public String getDepNo() {
        return this.depNo;
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

    public List<YKTUser> getUsersList() {
        return this.usersList;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setDepAllName(String depAllName) {
        this.depAllName = depAllName;
    }

    public void setDepNo(String depNo) {
        this.depNo = depNo;
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

    public void setUsersList(List<YKTUser> usersList) {
        this.usersList = usersList;
    }

    public YKTAttachments getHeadPortrait() {
        return this.headPortrait;
    }

    public void setHeadPortrait(YKTAttachments headPortrait) {
        this.headPortrait = headPortrait;
    }
}
