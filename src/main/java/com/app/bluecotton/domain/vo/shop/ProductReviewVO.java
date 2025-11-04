package com.app.bluecotton.domain.vo.shop;

import lombok.Data;

import java.util.Date;

@Data
public class ProductReviewVO {
    private Long id;
    private Date productReviewCreateAt;
    private Integer productReviewRating;
    private String productReviewContent;
    private Long productId;
    private Long memberId;
}
