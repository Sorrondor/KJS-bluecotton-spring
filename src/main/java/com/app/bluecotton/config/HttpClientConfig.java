package com.app.bluecotton.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
public class HttpClientConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        // HttpClient 기반 RequestFactory
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();

        // 🔥 타임아웃을 여기서 설정해야 Spring 3.4에서도 경고 없이 안전함
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);

        return builder
                .requestFactory(() -> factory)
                .build();
    }
}
