package cn.sxf.xls.entity;

import java.io.Serializable;

/**
 * Created by fenglonghui on 2017/7/7.
 */

public class UnitInfo implements Serializable {
    private String title;
    private String icon;
    private String type;
    private String url;
    private String platform;

    public UnitInfo() {
        super();
    }

    public UnitInfo(String title, String icon, String type, String url, String platform) {
        this.title = title;
        this.icon = icon;
        this.type = type;
        this.url = url;
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "Info{" +
                "title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}