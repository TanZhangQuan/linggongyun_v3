package com.example.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件读取工具类
 */
@Slf4j
public class FileUtil {

    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        }

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流  
        FileInputStream fis = new FileInputStream(filePath);
        // 创建一个长度为10240的Buffer
        byte[] bbuf = new byte[10240];
        // 用于保存实际读取的字节数  
        int hasRead = 0;
        while ((hasRead = fis.read(bbuf)) > 0) {
            sb.append(new String(bbuf, 0, hasRead));
        }
        fis.close();
        return sb.toString();
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }


    public static byte[] readFileUrlByBytes(String fileUrl) throws IOException {
        CloseableHttpResponse closeableHttpResponse = HttpClientUtils.urlGet(fileUrl);
        if (closeableHttpResponse == null) {
            throw new FileNotFoundException(fileUrl);
        }
        InputStream inputStream = closeableHttpResponse.getEntity().getContent();
        if (inputStream == null) {
            throw new FileNotFoundException(fileUrl);
        } else {
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream(count);
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(inputStream);
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
                bos.close();
                closeableHttpResponse.close();
            }
        }
    }

    /**
     * 获取URL的文件名和后缀
     *
     * @param fileUrl
     * @return
     */
    public static String getFileName(String fileUrl) {

        String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";
        Pattern pat = Pattern.compile("[\\w]+[.](" + suffixes + ")");//正则判断
        Matcher matcher = pat.matcher(fileUrl);//条件匹配
        while (matcher.find()) {
            String fileName = matcher.group();//截取文件名后缀名
            log.info("fileName: {}", fileName);
            return fileName;
        }

        return "";
    }

    public static void deleteFile(File file) {

        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
            log.info("所删除的文件不存在");
        }
    }

    /**
     * 如果目录不存在就创建目录
     *
     * @param path
     */
    public static void mkdirs(String path) {
        File file = new File(path);
        String fs = file.getParent();
        file = new File(fs);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

}
