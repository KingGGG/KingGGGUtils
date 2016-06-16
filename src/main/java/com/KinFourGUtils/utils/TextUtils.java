package com.KinFourGUtils.utils;

import android.text.method.DigitsKeyListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：KingGGG on 15/12/7 17:29
 * 描述：
 */
public class TextUtils {
    public static final String TYPE_PHONE = "phone";
    public static final String TYPE_EMAIL = "email";

    public static boolean isEmpty(String string) {
        return android.text.TextUtils.isEmpty(string) || equals(string, "null");
    }

    public static boolean equals(String a, String b) {
        return android.text.TextUtils.equals(a, b);
    }

    /**
     * 验证输入的字符串类别
     *
     * @param str
     * @return
     */
    public static String verifyType(String str) {
        if (TextUtils.isPhoneNum(str)) {
            return TYPE_PHONE;

        }
        if (TextUtils.isEmailAdd(str)) {
            return TYPE_EMAIL;
        }
        return "";
    }

    /**
     * 验证字符串是否为手机号码
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNum(String phoneNum) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNum);

        return m.matches();
    }

    /**
     * 验证字符串是否为邮箱地址
     *
     * @param emailAdd
     * @return
     */
    public static boolean isEmailAdd(String emailAdd) {//  \w 单词字符: [a-zA-Z_0-9]
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(emailAdd);

        return m.matches();
    }

    /**
     * 返回KeyListener 只能输入正确的身份证号
     * @return
     */
    public static DigitsKeyListener getIDCardKeyListener() {
        return DigitsKeyListener.getInstance("0123456789Xx");

    }

}
