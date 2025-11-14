package com.app.bluecotton.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVerifyRequest {
    private String impUid;
    private String merchantUid;
    private String paymentType;
    private String pgProvider;
    private String easyPayProvider;
    private Long memberId;
}
