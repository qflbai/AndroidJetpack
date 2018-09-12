package com.qflbai.lib;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.Locale;

/**
 * @author WenXian Bai
 * @Date: 2018/3/27.
 * @Description: 系统工具
 */

public class SystemUtil {
    /**
     * 获取系统当前的语言
     */
    public static String getSystemtLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();

        return language;
    }

    /**
     * 判断是否是中文系统
     */
    public static boolean isZh(Context context) {
        return getSystemtLanguage(context).equals("zh");
    }

    /**
     * 获取SDK版本号
     *
     * @return
     */
    public static int getSDKVersion() {
        return Integer.valueOf(android.os.Build.VERSION.SDK_INT);
    }

    /**
     * 获取应用包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取包信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(context), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return packageInfo;
    }

    /**
     * 获取当前应用版本编号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo.versionCode;
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo.versionName;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取标题栏高度
     *
     * @return
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(
                    tv.data, context.getResources().getDisplayMetrics()
            );
        }
        return 0;
    }

    /**
     * 获取屏幕最小宽度
     *
     * @return
     */
    public static float getSwMin(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int widthPixels = dm.widthPixels;

        float density = dm.density;

        float widthDP = widthPixels / density;
        return widthDP;
    }

    /**
     * 获取屏幕密度
     *
     * @param activity
     * @return
     */
    public static float getScreenDensity(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        float density = dm.densityDpi;
        return density;
    }

}
