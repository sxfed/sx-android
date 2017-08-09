package cn.sxf.xls.entity;

import java.io.Serializable;

/**
 * Created by fenglonghui on 2017/6/7.
 */

public class GrideInfo implements Serializable{

    private String name;
    private String type;
    private String url;
    private String iconurl;
    private String market;
    private String platform;

    public GrideInfo(){
        super();
    }

    public GrideInfo(String name, String type, String url, String iconurl, String market, String platform) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.iconurl = iconurl;
        this.market = market;
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    @Override
    public String toString() {
        return "GrideInfo{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", iconurl='" + iconurl + '\'' +
                ", url='" + url + '\'' +
                ", market='" + market + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
