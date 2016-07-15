package com.kingggg.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import com.kingggg.manager.ApkDownloadManager;

/**
 * 作者：KingGGG on 16/7/13 15:39
 * 描述：调用系统的下载完成更新
 */
public class AppUpdateUtils {
    private static final String DOWNLOAD = "download";
    private static final String DOWNLOAD_ID = "downloadId";

    public static void apkDownloadEvent(Context mContext, String url, String title, String apkName) {
        long downloadId = SharedPreferenceUtils.getLong(mContext, DOWNLOAD, DOWNLOAD_ID, -1L);
        if (-1L != downloadId) {
            ApkDownloadManager apkDownloadManager = ApkDownloadManager.getInstance(mContext);
            int status = apkDownloadManager.getDownloadStatus(downloadId);
            if (DownloadManager.STATUS_FAILED == status) {
                startDownLoad(mContext, url, title, apkName);
            } else {
                Uri uri = apkDownloadManager.getDownloadUri(downloadId);
                if (null != uri) {
                    if (AppUtils.compare(mContext, AppUtils.getApkInfo(mContext, uri.getPath()))) {
                        startInstall(mContext, uri);
                        return;
                    } else {
                        apkDownloadManager.getDownloadManager().remove(downloadId);
                    }
                } else {
                    startDownLoad(mContext, url, title, apkName);
                }
            }
        } else {
            startDownLoad(mContext, url, title, apkName);
        }
    }


    private static void start(Context context, String url, String title, String apkName) {
        long id = ApkDownloadManager.getInstance(context).startDownload(url,
                title, "下载完成后点击打开", apkName);
        SharedPreferenceUtils.saveLong(context, DOWNLOAD, DOWNLOAD_ID, id);
    }

    public static void startInstall(Context context, Uri uri) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }


    private static class DownLoadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context mContext, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                getApkUriToInstall(mContext, intent);
            }
            if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                getApkUriToInstall(mContext, intent);
            }
        }
    }

    private static void startDownLoad(Context mContext, String url, String title, String apkName) {
        start(mContext, url, title, apkName);
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        mContext.registerReceiver(new DownLoadCompleteReceiver(), filter);
    }

    private static void getApkUriToInstall(Context mContext, Intent intent) {
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        ApkDownloadManager apkDownloadManager = ApkDownloadManager.getInstance(mContext);
        Uri uri = apkDownloadManager.getDownloadUri(id);
        if (null != uri) {
            if (AppUtils.compare(mContext, AppUtils.getApkInfo(mContext, uri.getPath()))) {
                startInstall(mContext, uri);
                return;
            }
        }
    }
}
