package com.sicnu.cheer.generalmodule.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 部门信息
 * Created by cheer on 2016/11/7.
 */

public class Department implements Serializable {
    private String id;//部门Id
    @SerializedName("logo_path")
    private String logoPath;//部门图标
    private String name;//部门名称
    @SerializedName("org_code")
    private String orgCode;//部门编号
    private Department parent;//上级部门

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }
}
