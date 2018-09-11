package com.qflbai.lib.net.url;

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
    private static String mToken;

    /**
     * 设置token
     *
     * @param token
     */
    public static void setToken(String token) {
        mToken = token;
    }

    public static String getToken() {
        return mToken;
    }

    /**
     * 登陆
     */
    public static final String LOGIN = "a/login";

    /**
     * 退出登录
     */
    public static final String QUIT = "a/logout";

    /**
     * 总部接口地址
     */
    public static class App {
        /**
         * 获取权限接口
         */
        public static final String APP_USER_LIMIT = "a/sys/user/appUser";

    }


}
