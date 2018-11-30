package com.qflbai.mvvm.net.url;


import com.qflbai.mvvm.LibBuildConfig;

/**
 * @author WenXian Bai
 * @Date: 2018/3/14.
 * @Description: 网络请求服务器地址
 * url格式：protocol :// hostname[:port] / path /
 */

public class NetBaseUrl {
    /**
     * http协议
     */
    private static final String URL_HTTP = "http://";
    /**
     * 端口号
     */
    private static final String URL_PORT = ":80";
    /**
     * https协议
     */
    private static final String URL_HTTPS = "https://";
    /**
     * 协议
     */
    public static final String URL_PROTOCOL = URL_HTTP;

    /**
     * 文件服务器ip
     */
    private static final String FILE_SERVE_IP = "http://t.sun-tech.cn:8011";

    /**
     * 扫码平台服务器ip
     */
    private static final String scan_server_ip = "http://s.sun-tech.cn/";
    /**
     * IM服务器地址
     */
    private static final String IM_SERVER_IP = "http://im.sun-tech.cn";

    /**
     * 非默认ip(文件服务ip)
     */
    private static final String NO_DEFAULT_IP_FILE = FILE_SERVE_IP;

    /**
     * app release模式默认ip
     */
    private static final String URL_BASE_PATH = scan_server_ip;

    /**
     * debug模式默认ip
     */
    private static final String DEBUG_URL_IP = scan_server_ip;


    /**
     * release模式默认路径
     */
    private static String BASE_URL = URL_BASE_PATH;

    /**
     * debug模式默认路径
     */
    private static String DEBUG_BASE_URL = DEBUG_URL_IP;

    /**
     * 获取默认请求地址
     *
     * @return
     */
    public static String getBaseUrl() {
        return LibBuildConfig.isDebug() ? DEBUG_BASE_URL : BASE_URL;
        // return BASE_URL;
    }

    /**
     * 获取文件服务ip
     * @return
     */
    public static String getNoDefaultIpFile() {
        return NO_DEFAULT_IP_FILE;
    }
    
    /**
     * 获取IM服务器IP
     * @return
     */
    public static String getImServerIp() {
        return IM_SERVER_IP;
    }

    /**
     * 设置网络请求地址
     */
    public static void setBaseURL(String baseUrl) {
        String tag = "/";
        String substring = baseUrl.substring(baseUrl.length() - 1, baseUrl.length());
        if (!substring.equals(tag)) {
            baseUrl = baseUrl + tag;
        }
        BASE_URL = baseUrl;
        DEBUG_BASE_URL = baseUrl;
    }

}
