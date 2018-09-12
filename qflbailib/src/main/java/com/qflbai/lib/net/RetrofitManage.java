package com.qflbai.lib.net;

import com.qflbai.lib.net.clinet.NetClinet;
import com.qflbai.lib.net.listener.DownloadListener;
import com.qflbai.lib.net.retrofit.RetrofitService;
import com.qflbai.lib.net.retrofit.converter.FastJsonConverterFactory;
import com.qflbai.lib.net.url.NetBaseUrl;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description:
 */

public class RetrofitManage {
    /**
     * 一般retrofit
     */
    private Retrofit retrofit;

    /**
     * 下载retrorfit
     */
    private Retrofit downLoadRetrofit;

    private static RetrofitManage mRetrofitManage;

    private RetrofitManage(){}

    public static RetrofitManage getInstance(){
        synchronized (RetrofitManage.class) {
            if (mRetrofitManage == null) {
                if(mRetrofitManage==null) {
                    mRetrofitManage = new RetrofitManage();
                }
            }
        }
        return mRetrofitManage;
    }

    /**
     * 获取Retrofit
     *
     * @param baseUrl
     * @return
     */
    private Retrofit createRetrofit(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(NetClinet.getInstance())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 获取RetrofitService
     *
     * @return
     */
    public RetrofitService createService() {
        // 获取服务器地址
        String baseUrl = NetBaseUrl.getBaseUrl();
        return createRetrofit(baseUrl).create(RetrofitService.class);
    }

    /**
     * 获取RetrofitService
     *
     * @param baseUrl ip地址
     * @return
     */
    public RetrofitService createService(String baseUrl) {
        return createRetrofit(baseUrl).create(RetrofitService.class);
    }


    /**
     * 获取Retrofit(下载)
     *
     * @param baseUrl
     * @return
     */
    private Retrofit createDownloadRetrofit(String baseUrl, DownloadListener downloadListener) {
        if (downLoadRetrofit == null) {
            downLoadRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(NetClinet.getDownloadInstance(downloadListener))
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return downLoadRetrofit;
    }

    /**
     * 获取下载RetrofitService(用于下载)
     *
     * @return
     */
    public RetrofitService createDownloadService(String baseUrl, DownloadListener downloadListener) {
        // 获取服务器地址
        //String baseUrl = NetBaseUrl.getBaseUrl();
        return createDownloadRetrofit(baseUrl, downloadListener).create(RetrofitService.class);
    }

}