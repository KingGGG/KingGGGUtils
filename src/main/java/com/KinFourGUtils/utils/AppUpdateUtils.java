package com.kingggg.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.kingggg.manager.ApkDownloadManager;

/**
 * 作者：KingGGG on 16/7/13 15:39
 * 描述：
 */
public class AppUpdateUtils {
    private static final String DOWNLOAD = "download";
    private static final String DOWNLOAD_ID = "downloadId";

    public static void apkDownloadEvent(Context mContext, String url, String title) {
        long downloadId = SharedPreferenceUtils.getLong(mContext, DOWNLOAD, DOWNLOAD_ID, -1L);
        if (-1L != downloadId) {
            ApkDownloadManager apkDownloadManager = ApkDownloadManager.getInstance(mContext);
            int status = apkDownloadManager.getDownloadStatus(downloadId);
            if (DownloadManager.STATUS_FAILED == status) {
                start(mContext, url, title);
            } else {
                Uri uri = apkDownloadManager.getDownloadUri(downloadId);
                if (null != uri) {
                    if (compare(mContext, getApkInfo(mContext, uri.getPath()))) {
                        startInstall(mContext, uri);
                        return;
                    } else {
                        apkDownloadManager.getDownloadManager().remove(downloadId);
                    }
                }
                start(mContext, url, title);
            }
        } else {
            start(mContext, url, title);
        }
    }


    private static void start(Context context, String url, String title) {
        long id = ApkDownloadManager.getInstance(context).startDownload(url,
                title, "下载完成后点击打开");
        SharedPreferenceUtils.saveLong(context, DOWNLOAD, DOWNLOAD_ID, id);
    }

    public static void startInstall(Context context, Uri uri) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }


    /**
     * 获取apk程序信息
     *
     * @param mContext Context
     * @param path     apk path
     */
    private static PackageInfo getApkInfo(Context mContext, String path) {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info;
        }
        return null;
    }


    /**
     * 下载的apk和当前程序版本比较
     *
     * @param mContext Context*
     * @param apkInfo  apk file's packageInfo
     * @return 如果当前应用版本小于apk的版本则返回true
     */
    private static boolean compare(Context mContext, PackageInfo apkInfo) {
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
}
