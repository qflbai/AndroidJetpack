package com.qflbai.lib.utils.ui.toast;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description: Toast
 */

public class ToastUtil {
    private static Toast mToast;

    /**
     * 正常显示
     *
     * @param context
     * @param text
     */
    public static void show(Context context, String text) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.show();
    }

    /**
     * 长时间显示
     */
    public static void showLong(Context context, String text) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.show();
    }


    /**
     * 中间显示
     */
    public static void showCenter(Context context, String text) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

}
