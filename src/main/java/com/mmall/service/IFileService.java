package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017/10/14.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
