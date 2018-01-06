package com.vking.service.impl;

import com.google.common.collect.Lists;
import com.vking.service.IFileService;
import com.vking.util.FTPUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件处理
 * Created by XC on 2018/1/1.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService{

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName= UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上产文件，上传文件内容为：{},上传文件路径为：{},新文件名字为：{}",fileName,path,uploadFileName);
        File fileDir = new File(path);
        if (fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件上传成功

            //已经将targetFile上传到ftp服务器
            FTPUntil.uploadFile(Lists.newArrayList(targetFile));

            //上传完成后，删除upload的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }


        return targetFile.getName();
    }


}
