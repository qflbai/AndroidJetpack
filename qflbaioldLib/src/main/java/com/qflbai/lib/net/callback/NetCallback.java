package com.qflbai.lib.net.callback;

import io.reactivex.disposables.Disposable;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description: 网络请求回调
 */

public interface NetCallback {

    /**
     * 开始网络请求（网络请求没开始）
     * @param disposable
     */
    void onSubscribe(Disposable disposable);

    /**
     * 网路请求成功
     *
     * @param jsonString 响应数据
     */
    void onResponse(String jsonString);

    /**
     * 网络请求失败
     *
     * @param e
     */
    void onError(Throwable e);
}
