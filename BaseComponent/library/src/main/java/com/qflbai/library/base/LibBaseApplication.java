package com.qflbai.library.base;

import android.app.Application;
import android.content.Context;

/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public class LibBaseApplication extends Application {
    /**
     * 上下文对象
     */
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }


    public static Context getAPPContext() {
        return mContext;
    }
}
