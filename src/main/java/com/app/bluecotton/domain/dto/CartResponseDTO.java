package com.app.bluecotton.domain.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Long id;
    private Long memberId;
    private Long productId;
    private Integer cartQuantity;
    private String productName;
    private Integer productPrice;
    private String productPurchaseType;
    private Integer productStock;
    private String productImagePath;
    private String productImageName;

    private String productImageUrl;
}
