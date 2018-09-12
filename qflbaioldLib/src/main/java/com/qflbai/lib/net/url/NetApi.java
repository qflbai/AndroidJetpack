package com.qflbai.lib.net.url;

import android.content.Context;

import com.qflbai.lib.net.constant.sp.SpConstantValue;
import com.qflbai.lib.utils.sharedpreferences.SpUtil;

import static com.qflbai.lib.net.constant.sp.SpConstantValue.DEFAULT_VALUE_TOKEN;

/**
 * @author WenXian Bai
 * @Date: 2018/3/14.
 * @Description: 网络请求接口 （建议所有网络请求接口地址统一写在这里，便于管理）
 */

public final class NetApi {
    private NetApi() {
    }

    /**
     * 上下文
     */
    private static Context mContent;

    /**
     * 设置token
     *
     * @param context
     * @param token
     */
    public static void setToken(Context context, String token) {
        mContent = context;
        SpUtil.putString(context, SpConstantValue.KEY_TOKEN, token);
    }

    public static String getToken() {
        String string = SpUtil.getString(mContent, SpConstantValue.KEY_TOKEN, DEFAULT_VALUE_TOKEN);
        return string;
    }

    /**
     * 登陆
     */
    public static final String LOGIN = "a/login";

    /**
     * 总部接口地址
     */
    public static class Headquarters {
    }
}
