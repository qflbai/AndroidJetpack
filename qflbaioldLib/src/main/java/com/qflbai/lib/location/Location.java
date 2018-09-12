package com.qflbai.lib.location;

import java.io.Serializable;

/**
 * 百度定位信息类
 * Created by Administrator on 2015/4/2.
 */
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 定位时间
     */
    public String time;
    /**
     * 错误码
     */
    public int locType;

    /**
     * 纬度
     */
    public double latitude;
    /**
     * 经度
     */
    public double longitude;

    /**
     * 半径
     */
    public float radius;

    /**
     * 加速度
     */
    public float speed;

    /**
     * 卫星数量
     */
    public int satelliteNumber;

    /**
     * 地址
     */
    public String addr;

    /**
     * 方向
     */
    public float direction;

}
