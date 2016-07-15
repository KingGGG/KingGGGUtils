package com.kingggg.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 作者：KingGGG on 16/7/15 10:01
 * 描述：
 */
public class AppUtils {


    /**
     * 获取当前应用的版本
     *
     * @param mContext
     * @return
     */
    private static int getAppVersion(Context mContext) {
        String localPackage = mContext.getPackageName();
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(localPackage, 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 下载的apk和当前程序版本比较
     *
     * @param mContext Context*
     * @param apkInfo  apk file's packageInfo
     * @return 如果当前应用版本小于apk的版本则返回true
     */
    public static boolean compare(Context mContext, PackageInfo apkInfo) {
        if (null == apkInfo) {
            return false;
        }
        String localPackage = mContext.getPackageName();
        if (TextUtils.equals(apkInfo.packageName, localPackage)) {
            try {
                PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(localPackage, 0);
                if (apkInfo.versionCode > packageInfo.versionCode) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 获取apk程序信息
     *
     * @param mContext Context
     * @param path     apk path
     */
    public static PackageInfo getApkInfo(Context mContext, String path) {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (null != info) {
            return info;
        }
        return null;
    }
}
