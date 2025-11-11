package com.app.bluecotton.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ✅ 정적 리소스 매핑
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///C:/upload/");
    }
}
