package com.KinFourGUtils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * 作者：KingGGG on 16/4/1 13:56
 * 描述：
 */
public class BitmapUtils {
    /**
     * 通过Uri获取Bitmap
     *
     * @param context
     * @param uri
     * @return
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = 4;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, bitmapOptions);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 50;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;// 每次都减�?10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
