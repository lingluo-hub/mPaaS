package com.ibyte.sys.attach.util;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.buf.HexUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public class MD5Util {

    public static String hex(byte[] data) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        md5.update(data);
        String hex = HexUtils.toHexString(md5.digest());
        return String.valueOf(hex);
    }

    public static String hex(File file) {
        MessageDigest md5;
        InputStream inputStream = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[4096];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        String hex = HexUtils.toHexString(md5.digest());
        return String.valueOf(hex);
    }
}
