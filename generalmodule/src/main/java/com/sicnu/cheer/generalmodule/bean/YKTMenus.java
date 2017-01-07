//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sicnu.cheer.generalmodule.bean;

import java.io.Serializable;
import java.util.List;

public class YKTMenus implements Serializable {
    private static final long serialVersionUID = 2584858721671400962L;
    private String id;
    private String appcode;
    private String appname;
    private String apporder;
    private List<YKTMenus> child;

    public YKTMenus() {
    }

    public String getId() {
        return this.id;
    }

    public String getAppcode() {
        return this.appcode;
    }

    public String getAppname() {
        return this.appname;
    }

    public String getApporder() {
        return this.apporder;
    }

    public List<YKTMenus> getChild() {
        return this.child;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public void setApporder(String apporder) {
        this.apporder = apporder;
    }

    public void setChild(List<YKTMenus> child) {
        this.child = child;
    }
}
