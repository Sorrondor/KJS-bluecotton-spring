package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.PaymentPrepareRequest;
import com.app.bluecotton.domain.dto.PaymentVerifyRequest;
import com.app.bluecotton.domain.dto.PortOneDTO;
import com.app.bluecotton.domain.dto.PortOneResponse;

import java.util.Map;

public interface PaymentService {



    public PortOneDTO processPayment(Map<String, Object> paymentData);

    public PortOneResponse preparePayment(PaymentPrepareRequest request);

    public void verifyPayment(PaymentVerifyRequest request);

//    public void payAllPendingCandyOrders(Long memberId);

    public void payWithCandy(Long memberId, Long orderId);
}
