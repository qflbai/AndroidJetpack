package com.qflbai.lib.utils;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/3/27.
 */

public class CacheUtils {
    private static final String PATH = "qflbai/files";
    /**
     * @param context 上下文环境
     * @param url     用请求地址作为键
     * @param value   用value数据作为缓存内容
     */
    public static void savesNewPagerData(Context context, String url, String value) {
        if (Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            ///mnt/sdcard/yunnannews/files/llkskljskljklsjklsllsl
            try {
                String fileName = MD5Encoder.encode(url);//llkskljskljklsjklsllsl
                ///mnt/sdcard/beijingnews/files/llkskljskljklsjklsllsl
                File file = new File(Environment.getExternalStorageDirectory()
                        + PATH, fileName);
                File parentFile = file.getParentFile();//mnt/sdcard/beijingnews/files
                if (!parentFile.exists()) {
                    //创建目录
                    parentFile.mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                //保存文本数据
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(value.getBytes());
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            SpUtil.putString(context, url, value);
        }
    }

    /**
     * @param context 上下文环境
     * @param url     用请求地址作为键
     * @param value   默认值给空字符串
     * @return
     */
    public static String getSaveNewPagerData(Context context, String url, String
            value) {
        String result = "";
        if (Environment.getExternalStorageState().equals(Environment
                .MEDIA_MOUNTED)) {
            try {
                String fileName = MD5Encoder.encode(url);
                File file = new File(Environment.getExternalStorageDirectory()
                        + PATH, fileName);
                if (file.exists()) {
                    FileInputStream is = new FileInputStream(file);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) != -1) {
                        stream.write(buffer, 0, length);
                    }
                    is.close();
                    stream.close();
                    result = stream.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            result = SpUtil.getString(context, url, value);
        }
        return result;
    }
}
