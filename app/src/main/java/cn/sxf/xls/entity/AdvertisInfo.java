package cn.sxf.xls.entity;

import java.io.Serializable;

/**
 * Created by fenglonghui on 2017/6/6.
 */

import java.io.Serializable;

/**
 * 广告图
 * @author  flh
 */
public class AdvertisInfo implements Serializable{

    private String url;
    private String position;
    private String type;
    private String secondaryUrl;
    private String title;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }


    public AdvertisInfo() {
    }

    public AdvertisInfo(String url, String position, String type,String secondaryUrl) {
        this.url = url;
        this.position = position;
        this.type = type;
        this.secondaryUrl = secondaryUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getSecondaryUrl() {
        return secondaryUrl;
    }

    public void setSecondaryUrl(String secondaryUrl) {
        this.secondaryUrl = secondaryUrl;
    }

    @Override
    public String toString() {
        return "AdvertisInfo{" +
                "url='" + url + '\'' +
                ", position='" + position + '\'' +
                ", type='" + type + '\'' +
                ", secondaryUrl='" + secondaryUrl + '\'' +
                '}';
    }
}
