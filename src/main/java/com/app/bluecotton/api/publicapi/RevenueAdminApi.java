package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.DailyRevenue;
import com.app.bluecotton.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/revenue")
public class RevenueAdminApi {

    private final RevenueService revenueService;


    @GetMapping("/daily")
    public List<DailyRevenue> getDailyRevenueHistory() {
        return revenueService.getDailyRevenueHistory();
    }


    @GetMapping("/forecast")
    public Map<String, Object> getRevenueForecast(
            @RequestParam(name = "horizon", defaultValue = "7") int horizon
    ) {
        return revenueService.getRevenueDashboard(horizon);
    }
}
