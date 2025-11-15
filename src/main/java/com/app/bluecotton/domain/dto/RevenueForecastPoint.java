package com.app.bluecotton.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 예측 결과 한 포인트 (날짜별 예측 매출)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueForecastPoint {

    private String date;
    private Long predictRevenue;
}
