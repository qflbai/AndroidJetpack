package com.qflbai.lib.net.interceptor;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/3/14.
 * @Description:
 */

public class TokenHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
       // String token = NetApi.getToken();
        String token ="";
        Request originalRequest = chain.request();
        if (TextUtils.isEmpty(token) && token != null) {
            Request updateRequest = originalRequest.newBuilder()
                    .header("token", token)
                    .build();
            return chain.proceed(updateRequest);
        }

        return chain.proceed(originalRequest);
    }
}
