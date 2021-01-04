package com.jysh.filecontrol.service;

import com.jysh.filecontrol.exception.FileException;
import com.jysh.filecontrol.property.FileProperties;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author flamemaster
 * @date 2020/12/8
 */
@Service
public class FileService {
    //文件存储地址
    private final Path fileStorageLocation;
    @Autowired
    public FileService(FileProperties fileProperties){
        this.fileStorageLocation = Paths.get(fileProperties.getUploadDir()).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileException("无法创建文件存储目录",e);
        }
    }

    /***
     *
     * @param file
     * @return 文件名
     */
    public String storeFile(MultipartFile file){
        //获得文件名
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //加上当前时间戳确保文件名唯一
        String temp[]=fileName.split("\\.");
        fileName=temp[0]+System.currentTimeMillis()+"."+temp[1];

        try {
            //检查文件名是否含有非法字符
            if(fileName.contains("..")){
                throw new FileException("文件名中存在非法字符！"+fileName);
            }
            System.out.println(fileName);
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            //以复制的形式存储文件
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;

        } catch (FileException | IOException e) {
            throw new FileException("无法存储文件 " + fileName + "。 请再次尝试", e);
        }
    }

    /***
     *
     * @param fileName
     * @return 文件对象
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            //将传入的文件名加上头获得具体文件名
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            //以URL资源的方式得到文件
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileException("File not found " + fileName, ex);
        }
    }

    public Path deletePath(String fileName){
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        return filePath;
    }
}
