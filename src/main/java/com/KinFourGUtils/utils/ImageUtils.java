package com.KinFourGUtils.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 作者：KingGGG on 15/12/22 17:52
 * 描述：
 */
public class ImageUtils {
    /**
     * 旋转Bitmap
     *
     * @param b
     * @param rotateDegree
     * @return
     */
    //使用这个方法进行图片旋转、缩放、裁剪
    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();// 新建立矩阵
        matrix.postRotate((float) rotateDegree); // 旋转指定<旋转角度>
        //createBitmap(Bitmap source, int x, int y, int width, int height, Matrix m, boolean filter)
        //x+width要小于或等于source.getWidth()，y+height要小于或等于source.getHeight()
        //从图片source b 的左上角(0,0)到图片宽和高截图
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
        return rotaBitmap;
    }
}
