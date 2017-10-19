package com.mmall.service.UserServiceImpl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.until.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2017/10/14.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        //拿到上传文件的原始文件名
        String fileName = file.getOriginalFilename();
        //扩展名
        //abc.jpg
        String fileExtensionName =fileName.substring(fileName.lastIndexOf(".")+1);
        //为了防止名字重复的文件会被覆盖，采取如下拼接方式
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("上传文件开始：上传的文件名{},上传的路径{},上传的新文件名{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()){
            //获得写的权限（在tomcat中创建文件，不一定有权限发布文件）
            fileDir.setWritable(true);
            //mkdirs 和 mkdir 的区别是创建多个和一个的区别
            fileDir.mkdirs();
        }

        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功了

            //  将targetFile上传到ftp服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到ftp服务器上
            //  上传完成后删除upload下面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常！",e);
            return null;
        }
        return targetFile.getName();
    }
}
