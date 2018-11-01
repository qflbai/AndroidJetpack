package com.qflbai.library.net.interceptor;


import com.qflbai.library.utils.log.LogUtil;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author WenXian Bai
 * @Date: 2018/3/14.
 * @Description: Log 拦截器
 */

public class LogInterceptor implements Interceptor {
    String tag = "LogInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        Headers headers = request.headers();

        Response response = chain.proceed(request);
        try {
            LogUtil.i(tag, "request url:" + url.toString());
            LogUtil.i(tag, "request headers:" + headers.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
