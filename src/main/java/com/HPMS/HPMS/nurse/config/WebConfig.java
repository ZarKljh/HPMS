package com.HPMS.HPMS.nurse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration("nurseConfig")
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath();
        System.out.println("📂 ResourceHandler maps to: " + uploadPath);

        registry.addResourceHandler("/resource/static/img/nurse/**")
                .addResourceLocations("file:" + uploadPath.toString() + "/");
    }
}
