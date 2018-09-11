package com.qflbai.lib.base;

import android.app.Application;
import android.content.Context;


/**
 * @author WenXian Bai
 * @Date: 2018/3/22.
 * @Description: 全局应用APP状态基类
 */

public class BaseApplication extends Application {

    /**
     * 上下文对象
     */
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }


    public static Context getAppContext(){
        return mContext;
    }

}
