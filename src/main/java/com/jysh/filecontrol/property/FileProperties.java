package com.jysh.filecontrol.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author flamemaster
 * @date 2020/12/9
 */
//先将组件添加至容器中才能使用ConfigurationProperties   或者在Enable时表明类
//@Component
@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileProperties {
    //上传文件路径
    private String uploadDir;
}
