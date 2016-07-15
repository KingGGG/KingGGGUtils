package com.kingggg.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * 作者：KingGGG on 15/12/22 17:31
 * 描述：
 */
public class FileUtils {
    public static final String ENCODING = "UTF-8";
    private static final String TAG = "FileUtils";
    private static final File parentPath = Environment.getExternalStorageDirectory();//从SD卡中得到图片根路径
    private static String storagePath = "";
    public static final int SIZE_TYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZE_TYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZE_TYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZE_TYPE_GB = 4;// 获取文件大小单位为GB的double值
    public static final String TYPE_JPG = ".jpg";
    public static final String TYPE_PNG = ".png";
    public static final String TYPE_PART = ".part";

    /**
     * 初始化保存路径
     *
     * @return SD卡中图片保存路径
     */
    private static String initPath(String folderName) {
        if (storagePath.equals("")) {
            storagePath = parentPath.getAbsolutePath() + "/" + folderName;//图片路径
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();//如果路径下的文件夹不存在，则创建
            }
        }
        return storagePath;
    }

    /**
     * 保存Bitmap到sdcard
     *
     * @param bitmap
     */
    public static String saveBitmapInSDCard(Bitmap bitmap, String folderName, String fileName, String fileType) {
        String path = initPath(folderName);
        fileName = !TextUtils.isEmpty(fileName) ? fileName : (System.currentTimeMillis() + "");
        String jpegName = path + "/" + fileName + fileType;
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jpegName;
    }

    /**
     * 保存字节流到sdcard
     *
     * @param outputStream
     */
    public static String saveByeOutputStream2JPG(ByteArrayOutputStream outputStream, String folderName, String fileName) {
        String path = initPath(folderName);
        fileName = !TextUtils.isEmpty(fileName) ? fileName : (System.currentTimeMillis() + "");
        String jpegName = path + "/" + fileName + ".jpg";
        try {
            FileOutputStream fos = new FileOutputStream(new File(jpegName));//将压缩后的图片保存到本地上指定路径中
            fos.write(outputStream.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jpegName;
    }


    /**
     * 获取<指定文件路径>和<指定单位>的文件大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型:1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {//如果<指定文件路径>是一个目录，即文件夹，则调用getFileSizes（）
                blockSize = getFileSizes(file);
            } else {//如果<指定文件路径>是一个文件，则调用getFileSize（）
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {//如果文件夹下还是文件夹则迭代
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZE_TYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZE_TYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZE_TYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZE_TYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 将文件转化成Bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getFileToBitmap(String path) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(fis);
    }

    /**
     * 从assets 文件夹中获取文件并读取数据
     */
    public static String getFromAssets(Context mContext, String fileName) {
        String result = "";
        try {
            InputStream in = mContext.getResources().getAssets().open(fileName);
            //获取文件的字节数
            int length = in.available();
            //创建byte数组
            byte[] buffer = new byte[length];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = EncodingUtils.getString(buffer, ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断一个文件是否存在
     *
     * @param folderName
     * @param fileName
     * @return
     */
    public static boolean isFileExists(String folderName, String fileName, String fileType) {
        storagePath = parentPath.getAbsolutePath() + "/" + folderName + "/" + fileName + fileType;//图片路径
        File f = new File(storagePath);
        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 组装文件地址
     *
     * @param folderName
     * @param fileName
     * @return
     */
    public static String getFilePath(String folderName, String fileName) {
        return parentPath.getAbsolutePath() + "/" + folderName + "/" + fileName;
    }

    /**
     * 获取文件类型
     *
     * @param url
     * @return
     */
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void recursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                recursionDeleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 把String保存在文件中
     *
     * @param toSaveString
     * @param filePath
     */
    public static void saveFile(String toSaveString, String filePath) {
        try {
            File saveFile = new File(filePath);
            if (!saveFile.exists()) {
                File dir = new File(saveFile.getParent());
                dir.mkdirs();
                saveFile.createNewFile();
            }

            FileOutputStream outStream = new FileOutputStream(saveFile);
            outStream.write(toSaveString.getBytes());
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容
     *
     * @param filePath
     * @return
     */

    public static String readFile(String filePath) {
        String str = "";
        try {
            File readFile = new File(filePath);
            if (!readFile.exists()) {
                return null;
            }
            FileInputStream inStream = new FileInputStream(readFile);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            str = stream.toString();
            stream.close();
            inStream.close();
            return str;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.e("Ukey",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
