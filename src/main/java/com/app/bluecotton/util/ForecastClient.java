package com.app.bluecotton.util;

import com.app.bluecotton.domain.dto.DailyRevenue;
import com.app.bluecotton.domain.dto.ForecastRequest;
import com.app.bluecotton.domain.dto.RevenueForecastResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForecastClient {

    private final RestTemplate restTemplate;

    @Value("${forecast.url:http://127.0.0.1:8001/forecast}")
    private String forecastUrl;

    public RevenueForecastResponse requestForecast(List<DailyRevenue> history, int horizon) {

        // 1) 요청 DTO 만들기
        List<ForecastRequest.Point> series = history.stream()
                .map(d -> new ForecastRequest.Point(d.getDate(), d.getRevenue()))
                .collect(Collectors.toList());

        ForecastRequest request = new ForecastRequest(series, horizon);

        log.info("[ForecastClient] POST {} request={}", forecastUrl, request);

        // 2) JSON 보내기 위한 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3) HttpEntity 구성
        HttpEntity<ForecastRequest> entity = new HttpEntity<>(request, headers);

        // 4) 요청 보내기
        ResponseEntity<RevenueForecastResponse> response =
                restTemplate.postForEntity(
                        forecastUrl,
                        entity,
                        RevenueForecastResponse.class
                );

        RevenueForecastResponse body = response.getBody();
        log.info("[ForecastClient] 응답 status={} body={}", response.getStatusCode(), body);

        return body;
    }
}
