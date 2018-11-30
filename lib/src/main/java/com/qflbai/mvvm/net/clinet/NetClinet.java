package com.qflbai.mvvm.net.clinet;

import com.qflbai.mvvm.net.interceptor.DownloadInterceptor;
import com.qflbai.mvvm.net.interceptor.LogInterceptor;
import com.qflbai.mvvm.net.interceptor.TokenHeaderInterceptor;
import com.qflbai.mvvm.net.listener.DownloadListener;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description:
 */

public class NetClinet {
    private static OkHttpClient okHttpClient = null;
    private static OkHttpClient okHttpLoginClient = null;
    private static OkHttpClient okHttpDownloadClient = null;
    /**
     * 网络读取超时时间
     */
    private static final long READ_TIME_OUT = 30;
    /*
     * 网络连接超时时间
     */
    private static final long CONNECT_TIME_OUT = 30;
    /**
     * 网络写入时间
     */
    private static final long WRITE_TIME_OUT = 30;
    /**
     * 时间单位（分钟）
     */
    private static TimeUnit timeUnit = TimeUnit.SECONDS;


    private NetClinet() {
    }

    /**
     * 适用于所有带token请求
     *
     * @return
     */
    public static synchronized OkHttpClient getInstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIME_OUT, timeUnit)
                    .readTimeout(READ_TIME_OUT, timeUnit)
                    .writeTimeout(WRITE_TIME_OUT, timeUnit)
                    .addNetworkInterceptor(new TokenHeaderInterceptor())
                    .addInterceptor(new LogInterceptor())
                    .build();
        }
        return okHttpClient;
    }

    /**
     * 适用于不带token请求(登陆)
     *
     * @return
     */
    public static synchronized OkHttpClient getLoginInstance() {
        if (okHttpLoginClient == null) {
            okHttpLoginClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIME_OUT, timeUnit)
                    .readTimeout(READ_TIME_OUT, timeUnit)
                    .writeTimeout(WRITE_TIME_OUT, timeUnit)
                    .addInterceptor(new LogInterceptor())
                    .build();
        }
        return okHttpLoginClient;
    }

    /**
     * 适用于不带token请求
     *
     * @return
     */
    public static synchronized OkHttpClient getDownloadInstance(DownloadListener downloadListener) {
        if (okHttpDownloadClient == null) {
            okHttpDownloadClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIME_OUT, timeUnit)
                    .readTimeout(READ_TIME_OUT, timeUnit)
                    .writeTimeout(WRITE_TIME_OUT, timeUnit)
                    .addInterceptor(new DownloadInterceptor(downloadListener))
                    .addInterceptor(new LogInterceptor())
                    .build();
        }
        return okHttpDownloadClient;
    }


}