package com.jysh.filecontrol.controller;

import com.jysh.filecontrol.dto.UploadFileResponse;
import com.jysh.filecontrol.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author flamemaster
 * @date 2020/12/8
 */
@RestController
public class FileController {
    //打印日志所属的类
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private FileService fileService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file){
        //真实的名字，不同于存储在服务器中的名字
        String realName = StringUtils.cleanPath(file.getOriginalFilename());
        //存储文件并得到本地绝对存储路径
        String fileName = fileService.storeFile(file);

        String fileDownloadUri= ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/").path(fileName).toUriString();
        //返回给前端参数
        return new UploadFileResponse(realName,fileDownloadUri,file.getContentType(),file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.stream(files).map(this::uploadFile).collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        //把文件以Resource的形式读出
        Resource resource = fileService.loadFileAsResource(fileName);
        System.out.println(resource);
        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("无法确定文件类型。");
        }

        if(contentType == null){
            contentType = "application/octet-stream";
        }
        System.out.println("啊啊啊");
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+ resource.getFilename() + "\"")
                .body(resource);
    }
}
