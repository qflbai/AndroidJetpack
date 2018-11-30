package com.qflbai.library.utils.log;

import android.util.Log;

import com.qflbai.library.LibBuildConfig;


/**
 * @author WenXian Bai
 * @Date: 2018/3/14.
 * @Description: log
 */
public class LogUtil {

    /**
     * 以级别为 v 的形式输出LOG
     */
    public static void v(String tag, String msg) {
        if (isDebug()) {
            Log.v(tag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String tag, String msg) {
        if (isDebug()) {
            Log.d(tag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String tag, String msg) {
        if (isDebug()) {
            Log.i(tag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String tag, String msg) {
        if (isDebug()) {
            Log.w(tag, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String tag, String msg) {
        if (isDebug()) {
            Log.e(tag, msg);
        }
    }

    private static boolean isDebug() {
        return LibBuildConfig.isDebug();
    }

    /**
     * 异常log打印
     *
     * @param tag
     * @param msg
     * @param throwable
     */
    public static void ex(String tag, String msg, final Throwable throwable) {
        e(tag, msg);
        if (isDebug()) {
            throwable.printStackTrace();
        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                  /*  try {
                        ExceptionLogSave.getInstance().saveCrashInfoFile(BaseApplication.getContext(), LibConfig.SUNTECH_PATH, LibConfig.FILE_NAME_EX, throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }
            };

            thread.start();

           // Executors.newCachedThreadPool().execute(thread);
        }

    }
}
