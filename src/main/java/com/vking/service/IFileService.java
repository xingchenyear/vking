package com.vking.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * file
 * Created by gch_x on 2018/1/1.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);

}
