package com.jysh.filecontrol;

import com.jysh.filecontrol.property.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@SpringBootApplication
@EnableConfigurationProperties({
        FileProperties.class
})
public class FilecontrolApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilecontrolApplication.class, args);
    }

}
