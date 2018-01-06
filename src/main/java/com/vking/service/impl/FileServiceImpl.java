package com.vking.service.impl;

import com.vking.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件处理
 * Created by XC on 2018/1/1.
 */
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

            // TODO: 2018/1/1 将targetFile上传到ftp服务器

            // TODO: 2018/1/1 上传完成后，删除upload的文件

        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }


        return null;
    }


}
