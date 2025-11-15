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
    
    @Value("${forecast.api.url}")
    private String forecastApiUrl;

    /**
     * FastAPI 예측 서버 호출
     *
     * @param history 과거 매출 데이터
     * @param horizon 며칠 뒤까지 예측할지
     * @return 예측 결과 (없으면 빈 객체)
     */
    public RevenueForecastResponse requestForecast(List<DailyRevenue> history, int horizon) {

        // DailyRevenue -> ForecastRequest.Point 로 변환
        List<ForecastRequest.Point> series = history.stream()
                .map(d -> new ForecastRequest.Point(
                        d.getDate().toString(), // LocalDate -> "YYYY-MM-DD"
                        d.getRevenue()
                ))
                .collect(Collectors.toList());

        ForecastRequest body = new ForecastRequest(series, horizon);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ForecastRequest> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<RevenueForecastResponse> response = restTemplate.exchange(
                    forecastApiUrl,                      // ex) http://localhost:8000/forecast
                    HttpMethod.POST,
                    entity,
                    RevenueForecastResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("[ForecastClient] 예측 성공: {}건",
                        response.getBody().getData() != null
                                ? response.getBody().getData().size()
                                : 0);
                return response.getBody();
            } else {
                log.warn("[ForecastClient] 예측 실패 status={}", response.getStatusCode());
                return new RevenueForecastResponse();
            }

        } catch (Exception e) {
            log.error("[ForecastClient] 예측 요청 중 예외 발생", e);
            return new RevenueForecastResponse();
        }
    }
}
