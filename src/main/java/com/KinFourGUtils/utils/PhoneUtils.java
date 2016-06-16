package com.KinFourGUtils.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.KinFourGUtils.bean.ContactsMemberBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：KingGGG on 16/1/20 14:01
 * 描述：
 */
public class PhoneUtils {
    private static String cameraImageAddress = null;


    /**
     * 跳转到短信界面发送短信
     *
     * @param context
     * @param phone
     * @param message
     */
    public static void sendSmsBySkip(Context context, String phone, String message) {
        Uri uri = Uri.parse("smsto:" + phone);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", message);
        context.startActivity(it);
    }

    /**
     * 选择发送人，然后发送短信
     *
     * @param context
     * @param message
     */
    public static void selectAndSendSms(Context context, String message) {
        Uri uri = Uri.parse("smsto:");
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", message);
        context.startActivity(it);
    }

    /**
     * 直接拨打电话
     *
     * @param context
     * @param phone
     */
    public static void callPhone(Context context, String phone) {
        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
        context.startActivity(phoneIntent);
    }


    /**
     * 开启摄像头
     *
     * @param activity
     * @param requestCode:请求码，用于返回Activity的时候做识别
     */
    public static void openCamera(Activity activity, int requestCode) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        int hashCode = currentTime.hashCode();
        cameraImageAddress = String.valueOf(hashCode);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 调用系统相机

        Uri imageUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), "image" + cameraImageAddress + ".jpg"));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        activity.startActivityForResult(intent, requestCode);

    }

    /**
     * 只有在使用openCamera()函数调用摄像头后，才可以调用该函数获取摄像头拍摄的内容
     *
     * @return
     */
    public static String getCameraResponse() {
        String picturePath = Environment.getExternalStorageDirectory() + "/image" + cameraImageAddress + ".jpg";
        return picturePath;
    }

    /**
     * 开启相册
     *
     * @param activity
     * @param requestCode:请求码，用于返回Activity的时候做识别
     */
    public static void openAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 当从系统相册选择照片后，调用此函数获取选择的内容
     *
     * @param intent
     * @param activity
     * @return
     */
    public static String getAlbumResponse(Intent intent, Activity activity) {
        if (intent == null) {
            return null;
        }
        Uri originalUri = intent.getData();
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        Cursor c = activity.getContentResolver().query(originalUri, filePathColumns, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePathColumns[0]);
        String picturePath = c.getString(columnIndex);
        c.close();

        return picturePath;
    }

    public static List<ContactsMemberBean> getPhoneContacts(Context context) {
        String[] PHONES_PROJECTION = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        ContentResolver resolver = context.getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

        List<ContactsMemberBean> memberList = new ArrayList<ContactsMemberBean>();
        if (phoneCursor != null) {

            ContactsMemberBean member = null;

            while (phoneCursor.moveToNext()) {
                // 得到联系人名称
                String contactName = phoneCursor.getString(0);

                // 得到手机号码
                String phoneNumber = phoneCursor.getString(1);
                Log.i("", "-----联系人：" + contactName + ": " + phoneNumber);

                member = new ContactsMemberBean();
                member.setName(contactName);
                member.setPhone(phoneNumber);

                memberList.add(member);
            }
            phoneCursor.close();
        }
        return memberList;
    }

    // 获取指定Activity的截屏，保存到png文件
    private static Bitmap getScreenBitmap(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("TAG", "" + statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    /**
     * 截屏,保存为JPG
     *
     * @param activity
     */
    public static String takeScreenShotSaveByJPG(Activity activity, String folderName, String fileName) {
        return FileUtils.saveBitmapInSDCard(getScreenBitmap(activity), folderName, fileName, FileUtils.TYPE_JPG);
    }

    /**
     * 截屏,保存为PNG
     *
     * @param activity
     */
    public static String takeScreenShotSaveByPNG(Activity activity, String folderName, String fileName) {
        return FileUtils.saveBitmapInSDCard(getScreenBitmap(activity), folderName, fileName, FileUtils.TYPE_PNG);
    }


    /**
     * 截屏,保存为PART(这样子可以不用显示在图库里面)
     *
     * @param activity
     */
    public static String takeScreenShotSaveByPart(Activity activity, String folderName, String fileName) {
        return FileUtils.saveBitmapInSDCard(getScreenBitmap(activity), folderName, fileName, FileUtils.TYPE_PART);
    }

    /**
     * 通过ip138网站获取外网IP
     * @return
     */
    public static String getPhoneNetworkIP() {
        URL infoUrl = null;
        InputStream inStream = null;
        try {
            infoUrl = new URL("http://ip38.com");
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inStream, "utf-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                    stringBuilder.append(line + "\n");
                inStream.close();

                line = stringBuilder.substring(stringBuilder.indexOf("您的本机IP地址：") + 15, stringBuilder.indexOf("来自"));
                line = line.substring(line.indexOf(">") + 1, line.indexOf("<"));
                return line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
