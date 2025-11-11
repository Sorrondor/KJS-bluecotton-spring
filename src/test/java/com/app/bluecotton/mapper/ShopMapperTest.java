package com.app.bluecotton.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ShopMapperTest {

    @Autowired
    ShopMapper shopMapper;

    @Test
    void selectProductsByFilter() {
        Map<String,Object> params = new HashMap<>();
        params.put("clothing", "CLOTHING");
        params.put("order", "신상품순");
        log.info("{}",shopMapper.selectProductsByFilter(params));
    }

    @Test
    void selectProductDetailInfo() {

        Long productId = 1L;
        log.info("상품 상세 정보: {}", shopMapper.selectProductDetailInfo(productId));
    }


    @Test
    void selectProductReviewStatsTest() {
        Map<String,Object> reviewParams = new HashMap<>();
        reviewParams.put("id", 1L);
        reviewParams.put("type", null);      // photo면 사진 리뷰만, null이면 전체
        reviewParams.put("sort", "ratingHigh");
//        log.info("상품 리뷰 목록: {}", shopMapper.selectProductReviewStats(reviewParams));
    }

}