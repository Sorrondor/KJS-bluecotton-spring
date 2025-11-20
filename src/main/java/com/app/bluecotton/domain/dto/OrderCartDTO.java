package com.app.bluecotton.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCartDTO {
    private Long id;
    private Long memberId;
    private Long productId;
    private Long cartId;
    private Integer orderQuantity;
    private Date orderCreateAt;
    private Character orderStatus;
}
