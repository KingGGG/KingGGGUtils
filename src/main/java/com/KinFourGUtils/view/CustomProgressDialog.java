package com.KinFourGUtils.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;



/**
 * 作者：KingGGG on 15/12/11 15:04
 * 描述：
 */
public class CustomProgressDialog extends Dialog {
    private static CustomProgressDialog customProgressDialog = null;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setAbleCancle(boolean ableaCancle) {
        this.setCancelable(ableaCancle);
    }

    public static CustomProgressDialog createDialog(Context context) {
        customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.dialog_custom_progress);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (customProgressDialog == null) {
            return;
        }

        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.iv_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }


}
