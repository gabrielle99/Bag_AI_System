package com.tianci.common.fastdfs;

import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Component
public class FastdfsClient {
    @Resource
    private FastFileStorageClient storageClient;

    /**
     * Upload File Method
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     * @param fileName full path of file
     * @param extName extension of file, not include (.)
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName) throws Exception {
        StorePath s = storageClient.uploadFile(FileUtils.readFileToByteArray(new File(fileName)),extName);
        String result = s.getFullPath();
        return result;
    }

    public String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null);
    }

    /**
     * Upload file method
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     * @param fileContent content of file, byte array
     * @param extName extension of file
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName) throws Exception {
        StorePath s = storageClient.uploadFile(fileContent,extName);
        String result = s.getFullPath();
        return result;
    }

    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null);
    }

    /**
     * file download method
     */
    public byte[] downFile(String fileId) throws Exception {
        return storageClient.downloadFile("",fileId);
    }

    /**
     * file download method
     */
    public byte[] downGroupFile(String group, String fileId) throws Exception {
        return storageClient.downloadFile(group,fileId);
    }

    public int delFile(String fileId) throws Exception {
        storageClient.deleteFile(fileId);
        return 1;
    }
}
