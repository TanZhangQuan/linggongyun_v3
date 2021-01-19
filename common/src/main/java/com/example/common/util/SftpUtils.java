package com.example.common.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * sftp工具类
 *
 * @author xxx
 * @version 1.0
 * @date 2014-6-17
 * @time 下午1:39:44
 */
@Slf4j
public class SftpUtils {

    private String host;//服务器连接ip
    private String username;//用户名
    private String password;//密码
    private int port = 22;//端口号
    private ChannelSftp sftp = null;
    private Session sshSession = null;

    public SftpUtils() {

    }

    public SftpUtils(String host, int port, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public SftpUtils(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    /**
     * 通过SFTP连接服务器
     */
    public void connect() throws Exception {

        JSch jsch = new JSch();
        jsch.getSession(username, host, port);
        sshSession = jsch.getSession(username, host, port);
        if (log.isInfoEnabled()) {
            log.info("Session created.");
        }
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        if (log.isInfoEnabled()) {
            log.info("Session connected.");
        }
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        if (log.isInfoEnabled()) {
            log.info("Opening Channel.");
        }
        sftp = (ChannelSftp) channel;
        if (log.isInfoEnabled()) {
            log.info("Connected to " + host + ".");
        }

    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
                if (log.isInfoEnabled()) {
                    log.info("sftp is closed already");
                }
            }
        }
        if (this.sshSession != null) {
            if (this.sshSession.isConnected()) {
                this.sshSession.disconnect();
                if (log.isInfoEnabled()) {
                    log.info("sshSession is closed already");
                }
            }
        }
    }

    /**
     * 下载文件
     *
     * @param remotePathList：远程下载目录
     * @return
     */
    public void downLoadFile(List<String> remotePathList, HttpServletResponse response) throws Exception {

        if (remotePathList != null && remotePathList.size() > 0) {

            Vector<InputStream> vector = new Vector<>();
            for (String remotePath : remotePathList) {
                InputStream inputStream = sftp.get(remotePath);
                vector.add(inputStream);
            }

            OutputStream outputStream = response.getOutputStream();
            // 清空response
            response.reset();
            response.setContentType("application/x-download");//设置response内容的类型 普通下载类型
            response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode("平台对账文件.zip", "UTF-8"));

            Enumeration<InputStream> elements = vector.elements();
            SequenceInputStream sequenceInputStream = new SequenceInputStream(elements);
            try {
                int len;
                byte[] bytes = new byte[1024];
                while ((len = sequenceInputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sequenceInputStream.close();
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory：要列出的目录
     * @throws SftpException
     */
    public Vector listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ChannelSftp getSftp() {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }

}
