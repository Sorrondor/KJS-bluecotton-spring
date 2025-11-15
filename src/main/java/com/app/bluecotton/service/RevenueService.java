package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.DailyRevenue;
import com.app.bluecotton.domain.dto.RevenueForecastResponse;
import com.app.bluecotton.mapper.OrderMapper;
import com.app.bluecotton.util.ForecastClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final OrderMapper orderMapper;
    private final ForecastClient forecastClient;

    /** 1) DB에서 일자별 매출 집계 가져오기 */
    public List<DailyRevenue> getDailyRevenueHistory() {
        return orderMapper.selectDailyRevenue();
    }

    /** 2) FastAPI에 예측 요청 보내기 */
    public RevenueForecastResponse getRevenueForecast(int horizon) {
        List<DailyRevenue> history = getDailyRevenueHistory();
        // 데이터가 아예 없으면 빈 응답
        if (history == null || history.isEmpty()) {
            return new RevenueForecastResponse();
        }
        return forecastClient.requestForecast(history, horizon);
    }

    /** 3) 관리자 대시보드용: 과거 + 예측 한번에 반환 */
    public Map<String, Object> getRevenueDashboard(int horizon) {
        Map<String, Object> result = new HashMap<>();
        List<DailyRevenue> history = getDailyRevenueHistory();
        RevenueForecastResponse forecast = getRevenueForecast(horizon);

        result.put("history", history);
        result.put("forecast", forecast.getData()); // List<RevenueForecastPoint>
        return result;
    }
}
