package com.KinFourGUtils.volley;

import com.android.volley.AuthFailureError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 作者：KingGGG on 16/4/7 10:41
 * 描述：
 */
public class PostFileRequest extends PostRequest {
    private static final String BOUNDARY = "---------------------------7d931c5d043e";
    private static final String ENTRY_BOUNDARY = "--" + BOUNDARY;
    private static final String END_BOUNDARY = ENTRY_BOUNDARY + "--\r\n";

    private String urlPath;
    private FormFile[] files;
    private Map<String, String> params;
    private StringBuilder textEntity;

    public PostFileRequest(String urlPath, FormFile[] files, Map<String, String> params, ResponseListener listener) {
        super(urlPath, null, listener);
        this.files = files;
        this.params = params;
        this.urlPath = urlPath;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        URL url = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        int port = url.getPort() == -1 ? 80 : url.getPort();

        String requestMethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
        try {
            outStream.write(requestMethod.getBytes());

            String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
            outStream.write(accept.getBytes());
            String language = "Accept-Language: zh-CN\r\n";
            outStream.write(language.getBytes());
            String contentType = "Content-Type: multipart/form-data; boundary=" + BOUNDARY + "\r\n";
            outStream.write(contentType.getBytes());
            String contentLength = "Content-Length: " + getDataLength() + "\r\n";
            outStream.write(contentLength.getBytes());
            String alive = "Connection: keep-alive\r\n";
            outStream.write(alive.getBytes());
            String host = "Host: " + url.getHost() + ":" + port + "\r\n";
            outStream.write(host.getBytes());
            //写完HTTP请求头后根据HTTP协议再写一个回车换行
            outStream.write("\r\n".getBytes());
            //把所有文本类型的实体数据发送出来
            outStream.write(textEntity.toString().getBytes());
            //把所有文件类型的实体数据发送出来
            if (files != null && files.length != 0) {
                for (FormFile uploadFile : files) {
                    StringBuilder fileEntity = new StringBuilder();
                    fileEntity.append("--");
                    fileEntity.append(BOUNDARY);
                    fileEntity.append("\r\n");
                    fileEntity.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName() + "\";filename=\"" + uploadFile.getFileName() + "\"\r\n");
                    fileEntity.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");
                    outStream.write(fileEntity.toString().getBytes());
                    if (uploadFile.getInStream() != null) {
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = uploadFile.getInStream().read(buffer, 0, 1024)) != -1) {
                            outStream.write(buffer, 0, len);
                        }
                        uploadFile.getInStream().close();
                    } else {
                        outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
                    }
                    outStream.write("\r\n".getBytes());
                }
            }
            //下面发送数据结束标志，表示数据已经结束
            outStream.write(END_BOUNDARY.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }


    @Override
    public String getBodyContentType() {
        return "multipart/form-data; boundary=" + BOUNDARY;
    }

    private int getDataLength() {
        int fileDataLength = 0;
        if (files != null && files.length != 0) {
            for (FormFile uploadFile : files) {//得到文件类型数据的总长度
                StringBuilder fileExplain = new StringBuilder();
                fileExplain.append("--");
                fileExplain.append(BOUNDARY);
                fileExplain.append("\r\n");
                fileExplain.append("Content-Disposition: form-data;name=\"" + uploadFile.getParameterName() + "\";filename=\"" + uploadFile.getFileName() + "\"\r\n");
                fileExplain.append("Content-Type: " + uploadFile.getContentType() + "\r\n\r\n");
                fileExplain.append("\r\n");
                fileDataLength += fileExplain.length();
                if (uploadFile.getInStream() != null) {
                    fileDataLength += uploadFile.getFile().length();
                } else {
                    fileDataLength += uploadFile.getData().length;
                }
            }
        }

        textEntity = new StringBuilder();
        if (params != null && !params.isEmpty())

        {
            for (Map.Entry<String, String> entry : params.entrySet()) {//构造文本类型参数的实体数据
                textEntity.append("--");
                textEntity.append(BOUNDARY);
                textEntity.append("\r\n");
                textEntity.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n\r\n");
                textEntity.append(entry.getValue());
                textEntity.append("\r\n");
            }
        }

        //计算传输给服务器的实体数据总长度
        return textEntity.toString().getBytes().length + fileDataLength + END_BOUNDARY.getBytes().length;
    }
}
