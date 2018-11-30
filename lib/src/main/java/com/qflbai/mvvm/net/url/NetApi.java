package com.qflbai.mvvm.net.url;


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
     * 总部接口地址
     */
    public static class SDK {
        /**
         * 授权校验
         */
        public static final String AUTHORIZATION_VERIFY = "app/plugins/checksdk";
        /**
         * 后台解码
         */
        public static final String SERVE_DECODE = "app/plugins/scan";
        /**
         * 扫码信息上传
         */
        public static final String CODE_UPLODE = "decodeinfos/insert";
    
        /** 第三方登录检测接口 : 该接口提供用户服务器用户验证功能  IM服务器提供接口*/
        public  static String CHECK_UUID_IS_BIND_URL = "SunTechRestFul/checkuuidisbinduseraccount";
    
        /**第三方登录绑定接口 : 该接口实现用户名与UID绑定的功能 IM服务器提供接口*/
        public  static String BIND_USER_WITH_UID_URL = "SunTechRestFul/adduserbindaccount";
        
        /** 找回密码 IM服务器提供接口*/
        public  static String RESET_PASSWORD_URL = "/SunTechRestFul/forgotpassword";
    }

    public static class App {
        /**
         * 更新版本
         */
        public static final String UPDATA_VERSION="app/plugins/updateAppVersion";
        /**
         * 手机兼容接口
         */
        public static final String PHONE_COMPATIBILITY_CHECK= "app/plugins/incompatibleModel/check";
    }
}
