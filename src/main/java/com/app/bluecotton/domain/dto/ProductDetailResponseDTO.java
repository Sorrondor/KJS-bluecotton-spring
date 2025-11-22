package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.shop.ProductPurchaseType;
import com.app.bluecotton.domain.vo.shop.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDetailResponseDTO {

    private Long id;
    private String productName;
    private Integer productPrice;
    private ProductType productType;
    private ProductPurchaseType productPurchaseType;
    private Double productAvgRating;
    private Integer productReviewCount;
    private Integer productLikeCount;
    private String productMainImageUrl;
    private String productSubImageUrl;
    private Integer productIsLiked;
    private List<String> productSubImages;
}
