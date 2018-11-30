package com.qflbai.mvvm.net.callback;


import com.qflbai.mvvm.net.body.ServerResult;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description: 网络请求回调
 */

public interface DataNetCallback extends BaseNetCallback {


    /**
     * 网路请求成功
     *
     * @param data 请求成功数据
     */
    void onOkResponse(Object data);

    /**
     * 业务错误
     *
     * @param serverResult 服务器响应结果
     */
    void onFailResponse(ServerResult serverResult);

}
