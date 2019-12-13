package com.ibyte.sys.attach.store.server;

import com.ibyte.common.exception.KmssRuntimeException;
import com.ibyte.sys.attach.store.AbstractSysAttachStoreProxy;
import com.ibyte.sys.attach.support.encry.ISysAttachEncry;
import com.ibyte.sys.attach.support.encry.SysAttachEncryFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

/**
 * 文件本地存储流代理实现
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachServerProxy extends AbstractSysAttachStoreProxy {

    @Autowired
    private SysAttachEncryFactory sysAttachEncryFactory;

    /**
     * 加密service
     */
    private ISysAttachEncry getEncryService() {
        ISysAttachEncry encryService = sysAttachEncryFactory.getAttachEncryService();
        return encryService;
    }

    /**
     * 解密service
     */
    private ISysAttachEncry getDecryService(String encryMethod) {
        ISysAttachEncry decryService = sysAttachEncryFactory.getAttachEncryService(encryMethod);
        return decryService;
    }


    @Override
    public void writeFile(InputStream inputSream, String filePath, Map<String, String> header) {
        // 当服务器上面不存在附件文件时候，分别构建输入，输出流
        File file = new File(filePath);
        File pfile = file.getParentFile();
        if (!pfile.exists()) {
            pfile.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = null;
        InputStream encryptionInputStream = null;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);

            // 进行附件文件写操作
            encryptionInputStream = getEncryService().initEncryInputStream(inputSream);
            IOUtils.copy(encryptionInputStream, fileOutputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 附件写操作完成后，依次关闭输出流和输入流，释放输出，输入流所在内存空间
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(encryptionInputStream);
            IOUtils.closeQuietly(inputSream);
        }
    }

    @Override
    public String buildFullPath(String catalog, String modelPath, String filePath) {
        return null;
    }

    @Override
    public InputStream readFile(String filePath, String encryMethod) {
        File file = new File(filePath);
        try {
            if (file.exists()) {
                InputStream decryptionInputStream = getDecryService(encryMethod).initDecryInputStream(new FileInputStream(file));
                return decryptionInputStream;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new KmssRuntimeException("sys-attach:sys.attach.msg.error.SysAttachFileDecryFailed", e);
        }
        throw new KmssRuntimeException("sys-attach:sys.attach.msg.error.SysAttachFileNotFound");
    }

    @Override
    public void appendFile(InputStream inputSream, String filePath, long position, Map<String, String> header) {
        if (position == 0) {
            this.writeFile(inputSream, filePath, header);
            return;
        }

        // 当服务器上面不存在附件文件时候，抛异常
        File file = new File(filePath);
        if (!file.exists()) {
            throw new KmssRuntimeException("sys-attach:sys.attach.msg.error.SysAttachFileNotFound");
        }
        RandomAccessFile randomFile = null;
        InputStream encryptionInputStream = null;
        try {
            randomFile = new RandomAccessFile(file, "rw");
            randomFile.seek(position);

            // 进行附件文件写操作
            encryptionInputStream = getEncryService().initEncryInputStream(inputSream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(encryptionInputStream, baos);
            randomFile.write(baos.toByteArray());
        } catch (IOException e) {
            throw new KmssRuntimeException("sys-attach:sys.attach.msg.error.SysAttachFileAppendFailed", e);
        } finally {
            // 附件写操作完成后，依次关闭输出流和输入流，释放输出，输入流所在内存空间
            IOUtils.closeQuietly(randomFile);
            IOUtils.closeQuietly(encryptionInputStream);
            IOUtils.closeQuietly(inputSream);
        }
    }
}
