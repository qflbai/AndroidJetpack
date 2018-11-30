package com.qflbai.mvvm.utils.time;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/03/13
 *     desc  : The constants of time.
 * </pre>
 */
public final class TimeConstants {

    /**
     * 毫秒
     */
    public static final int MSEC = 1;
    /**
     * 秒
     */
    public static final int SEC  = 1000;
    /**
     * 分钟
     */
    public static final int MIN  = 60000;
    /**
     * 小时
     */
    public static final int HOUR = 3600000;
    /**
     * 天
     */
    public static final int DAY  = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
