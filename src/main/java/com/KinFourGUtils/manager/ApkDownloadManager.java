package com.kingggg.manager;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

/**
 * 作者：KingGGG on 16/7/13 15:40
 * 描述：
 */
public class ApkDownloadManager {

    private DownloadManager downloadManager;
    private Context context;
    private static ApkDownloadManager instance;

    private ApkDownloadManager(Context mContext) {
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        this.context = mContext.getApplicationContext();
    }

    public static ApkDownloadManager getInstance(Context mContext) {
        if (instance == null) {
            instance = new ApkDownloadManager(mContext);
        }
        return instance;
    }


    /**
     * @param uri
     * @param title
     * @param description
     * @return apkDownloadEvent id
     */
    public long startDownload(String uri, String title, String description, String apkName) {
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(uri));

        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //设置文件的保存的位置[三种方式]
        //第一种
        //file:///storage/emulated/0/Android/data/your-package/files/Download/update.apk
//        req.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "update.apk");
        //第二种
        //file:///storage/emulated/0/Download/update.apk
        req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "apkName");
        //第三种 自定义文件路径
        //req.setDestinationUri()

        // 设置一些基本显示信息
        req.setTitle(title);
        req.setDescription(description);
        req.setMimeType("application/vnd.android.package-archive");

        return downloadManager.enqueue(req);

    }


    /**
     * 获取文件保存的路径
     *
     * @param downloadId an ID for the apkDownloadEvent, unique across the system.
     *                   This ID is used to make future calls related to this apkDownloadEvent.
     * @return file path
     * @see
     */
    public String getDownloadPath(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                }
            } finally {
                c.close();
            }
        }
        return null;
    }


    /**
     * 获取保存文件的地址
     *
     * @param downloadId an ID for the apkDownloadEvent, unique across the system.
     *                   This ID is used to make future calls related to this apkDownloadEvent.
     * @see
     */
    public Uri getDownloadUri(long downloadId) {
        return downloadManager.getUriForDownloadedFile(downloadId);
    }

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }


    /**
     * 获取下载状态
     *
     * @param downloadId an ID for the apkDownloadEvent, unique across the system.
     *                   This ID is used to make future calls related to this apkDownloadEvent.
     * @return int
     * @see DownloadManager#STATUS_PENDING
     * @see DownloadManager#STATUS_PAUSED
     * @see DownloadManager#STATUS_RUNNING
     * @see DownloadManager#STATUS_SUCCESSFUL
     * @see DownloadManager#STATUS_FAILED
     */
    public int getDownloadStatus(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                }
            } finally {
                c.close();
            }
        }
        return -1;
    }


}
