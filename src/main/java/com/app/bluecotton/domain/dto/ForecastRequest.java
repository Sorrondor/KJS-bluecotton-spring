package com.app.bluecotton.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * FastAPI 예측 서버에 보내는 요청 DTO
 * - series: 과거 매출 데이터 리스트
 * - horizon: 며칠 뒤까지 예측할지
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecastRequest {

    private List<Point> series;
    private int horizon;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Point {
        private String date;
        private Long revenue;
    }
}
