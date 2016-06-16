package com.KinFourGUtils.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.KinFourGUtils.view.CustomProgressDialog;


/**
 * 作者：KingGGG on 15/12/11 15:02
 * 描述：
 */
public class DialogUtil {
    private static CustomProgressDialog progressDialog;
    private static Dialog dialog;

    /**
     * 获取提示的对话框Dialog对象,在调用该函数时，参数context不能为空，其余的参数可为空，但是相关的内容不会显示
     *
     * @param context
     * @param hintTitle       :对话框的title
     * @param hintMessage     :对话框给用户的提示
     * @param okButtonStr     ：对话框的确定按钮文字
     * @param cancleButtonStr ：对话框的否定按钮文字
     * @param okListener      ：确定按钮的监听器
     * @param cancleListener  ：取消按钮的监听器
     * @return
     */
    public static Dialog getHintDialog(Context context, String hintTitle, String hintMessage, String okButtonStr, String cancleButtonStr,
                                       DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancleListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (!TextUtils.isEmpty(hintTitle)) {
            builder.setTitle(hintTitle);
        }

        if (!TextUtils.isEmpty(hintMessage)) {
            builder.setMessage(hintMessage);
        }

        if (!TextUtils.isEmpty(okButtonStr)) {
            builder.setPositiveButton(okButtonStr, okListener);
        }

        if (!TextUtils.isEmpty(cancleButtonStr)) {
            builder.setNegativeButton(cancleButtonStr, cancleListener);
        }

        Dialog dialog = builder.create();
        return dialog;
    }

    /**
     * 显示提示的对话框,在调用该函数时，参数context不能为空，其余的参数可为空，但是相关的内容不会显示
     *
     * @param context
     * @param hintTitle       :对话框的title
     * @param hintMessage     :对话框给用户的提示
     * @param okButtonStr     ：对话框的确定按钮文字
     * @param cancleButtonStr ：对话框的否定按钮文字
     * @param okListener      ：确定按钮的监听器
     * @param cancleListener  ：取消按钮的监听器
     * @return
     */
    public static Dialog showHintDialog(Context context, String hintTitle, String hintMessage, String okButtonStr, String cancleButtonStr,
                                        DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancleListener) {

        if (!(context instanceof Activity)) {
            return null;
        }

        Dialog dialog = getHintDialog(context, hintTitle, hintMessage, okButtonStr, cancleButtonStr, okListener, cancleListener);
        dialog.setCancelable(false);
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    // 用于控制其他设备登陆对话框，只显示一个
    public static boolean hasShowLoginOtherDialog = false;
    public static Dialog loginOtherDialog = null;

    public static void showHintDialog(final Activity activity, String hintTitle, String hintMessage, String okButtonStr, String cancleButtonStr,
                                      DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancleListener, boolean cancleOutside) {

        Dialog dialog = getHintDialog(activity, hintTitle, hintMessage, okButtonStr, cancleButtonStr, okListener, cancleListener);

        dialog.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    activity.finish();
                }
                return false;
            }
        });
        dialog.setCanceledOnTouchOutside(cancleOutside);

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // private OnKeyListener backKeyListener = ;

    public static void closeInput(Context context, TextView editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 显示加载的对话框，注意这里的isForbidCancle参数，这个参数表示是否可以手动的取消当前的加载对话框
     *
     * @param context
     * @param isForbidCancle :true表示可以手动取消当前的加载框，false表示不可以
     */
    public static void showLoadingDialog(Context context, boolean isForbidCancle) {
        if (!(context instanceof Activity)) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(context);
        } else if (progressDialog.isShowing()) {
            return;
        }

        forbidCancle(isForbidCancle);

        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 设置加载框是否可以被手动取消
    private static void forbidCancle(boolean isForbidCancle) {
        if (progressDialog == null) {
            return;
        }
        progressDialog.setCancelable(isForbidCancle);
        progressDialog.setCanceledOnTouchOutside(isForbidCancle);
        progressDialog.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
                closeLoadingDialog();
                return true;
            }
        });
    }

    /**
     * 关闭加载框
     */
    public static void closeLoadingDialog() {
        if (progressDialog != null) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog = null;
        }
    }

    /**
     * 显示让用户输入信息的对话框
     *
     * @param context
     * @param dialogTitle     :对话框的title
     * @param okButtonStr     :对话框的确定按钮文字
     * @param OkListener      :对话框的取消按钮文字
     * @param cancleButtonStr :确定按钮的监听器
     * @param cancleListener  :取消按钮的监听器
     */
    public static void showInputDialog(Context context, EditText editText, String dialogTitle, String okButtonStr,
                                       DialogInterface.OnClickListener OkListener, String cancleButtonStr, DialogInterface.OnClickListener cancleListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(dialogTitle)) {
            builder.setTitle(dialogTitle);
        }

        builder.setView(editText);

        if (!TextUtils.isEmpty(okButtonStr)) {
            builder.setPositiveButton(okButtonStr, OkListener);
        }

        if (!TextUtils.isEmpty(cancleButtonStr)) {
            builder.setNegativeButton(cancleButtonStr, cancleListener);
        }
        dialog = builder.create();
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean loadingDialogIsShow() {
        if (progressDialog != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void closeInputDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
