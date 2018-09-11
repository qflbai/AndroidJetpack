package com.qflbai.lib.net.listener;

import java.io.File;

/**
 * @author WenXian Bai
 * @Date: 2018/3/29.
 * @Description: 下载监听
 */

public interface DownloadListener {
    /**
     * 开始下载
     */
    void onStartDownload();

    /**
     * 下载成功
     * @param result
     */
    void onSuccess(File result);

    /**
     *  下载进度
     * @param total 文件大小
     * @param current 当前进度
     */
    void onLoading(long total, long current);

    /**
     * 下载完成
     */
    void onFinished();

    /**
     * 下载失败
     * @param ex
     */
    void onFail(Throwable ex);
}
