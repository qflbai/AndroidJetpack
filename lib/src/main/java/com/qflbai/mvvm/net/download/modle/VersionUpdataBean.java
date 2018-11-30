package com.qflbai.mvvm.net.download.modle;

/**
 * @author WenXian Bai
 * @Date: 2018/3/7.
 * @Description:
 */

public class VersionUpdataBean {

    /**
     * code : 0
     * msg :
     * version : 1.9.9
     * date : 2017-02-22 15:12:39
     * url : http://f.sun-tech.cn:88/SunTech/suntech.apk
     * forcedupdate : 1
     * desc : 1、更新鉴伪设置
     2、更新扫码功能
     3、更新百度定位SDK，并调整调用的接口
     */

    private int code;
    private String msg;
    private String version;
    private String date;
    private String url;
    private String forcedupdate;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getForcedupdate() {
        return forcedupdate;
    }

    public void setForcedupdate(String forcedupdate) {
        this.forcedupdate = forcedupdate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
