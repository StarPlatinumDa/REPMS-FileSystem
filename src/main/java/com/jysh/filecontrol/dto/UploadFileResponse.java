package com.jysh.filecontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author flamemaster
 * @date 2020/12/9
 */
@Getter
@Setter
@AllArgsConstructor
public class UploadFileResponse {
    //文件名
    private String fileName;
    //文件下载地址
    private String fileDownloadUri;
    //文件类型
    private String fileType;
    //文件大小
    private long size;

}
