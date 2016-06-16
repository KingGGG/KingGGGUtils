package com.KinFourGUtils.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 作者：KingGGG on 15/12/2 15:29
 * 描述：
 */
public class ToastUtils {
    private static Toast toast = null;

    private static void toastInit(Context context, String message, int time) {
        toast = Toast.makeText(context, message, time);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static void showLong(int resId, Context context) {
        toastInit(context, context.getString(resId), 1);
    }

    public static void showLong(String message, Context mContext) {
        toastInit(mContext, message, 1);
    }

    public static void showShort(int resId, Context context) {
        toastInit(context, context.getString(resId), 0);
    }

    public static void showShort(String message, Context mContext) {
        toastInit(mContext, message, 0);
    }
}
