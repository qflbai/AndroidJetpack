package com.qflbai.lib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author WenXian Bai
 * @Date: 2018/2/26.
 * @Description:
 */

public class AppToast {

    private static Toast toast = null;

    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
