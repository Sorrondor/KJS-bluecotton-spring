package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.shop.PaymentSocialVO;
import com.app.bluecotton.domain.vo.shop.PaymentStatus;
import com.app.bluecotton.domain.vo.shop.PaymentVO;
import com.app.bluecotton.service.OrderService;
import com.app.bluecotton.service.PaymentService;
import com.app.bluecotton.service.PaymentSocialService;
import com.app.bluecotton.util.PortOneClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Port;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment/*")
public class PaymentApi {

    private final PaymentService paymentService;
    private final PaymentSocialService paymentSocialService;


    @PostMapping("prepare")
    public ResponseEntity<PortOneResponse> preparePayment(
            @RequestBody @Valid PaymentPrepareRequest request) {

        log.info("Payment preparation request received: {}", request);

        if (request.getMemberId() == null) {
            request.setMemberId(100L);
        }

        try {
            PortOneResponse response = paymentService.preparePayment(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Payment preparation failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping("verify")
    public ResponseEntity<ApiResponseDTO<PortOneDTO>> verify(@RequestBody @Valid PaymentVerifyRequest request) {

        paymentService.verifyPayment(request);

        return ResponseEntity.ok(ApiResponseDTO.of("결제 검증 및 처리 성공", null));
    }




}
