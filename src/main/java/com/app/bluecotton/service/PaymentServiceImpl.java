package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.PaymentPrepareRequest;
import com.app.bluecotton.domain.dto.PaymentVerifyRequest;
import com.app.bluecotton.domain.dto.PortOneDTO;
import com.app.bluecotton.domain.dto.PortOneResponse;
import com.app.bluecotton.domain.vo.shop.OrderVO;
import com.app.bluecotton.domain.vo.shop.PaymentSocialVO;
import com.app.bluecotton.domain.vo.shop.PaymentStatus;
import com.app.bluecotton.domain.vo.shop.PaymentVO;
import com.app.bluecotton.repository.OrderDAO;
import com.app.bluecotton.repository.PaymentDAO;
import com.app.bluecotton.service.PaymentSocialService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;
    private final PaymentSocialService paymentSocialService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderDAO orderDAO;
    private final OrderService orderService;

    private static final String PORTONE_API_BASE_URL = "https://api.iamport.kr";
    private static final String GET_TOKEN_URL = PORTONE_API_BASE_URL + "/users/getToken";
    private static final String GET_PAYMENT_INFO_URL = PORTONE_API_BASE_URL + "/payments/{imp_uid}";
    private static final String PREPARE_PAYMENT_URL = PORTONE_API_BASE_URL + "/payments/prepare";

    @Value("${portone.imp-key}")
    private String impKey;

    @Value("${portone.imp-secret}")
    private String impSecret;


    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("imp_key", impKey);
        requestBody.put("imp_secret", impSecret);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(GET_TOKEN_URL, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                if (root.get("code").asInt() == 0) {
                    return root.get("response").get("access_token").asText();
                }
            }
            throw new RuntimeException("PortOne Access Token 발급 실패: " + response.getBody());
        } catch (Exception e) {
            log.error("토큰 발급 중 예외 발생", e);
            throw new RuntimeException("PortOne API 통신 오류(토큰 발급)", e);
        }
    }

    private String generateMerchantUid(Long orderId) {
        return "ORD_" + orderId + "_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }


    private PortOneDTO getPaymentInfoFromPortOne(String accessToken, String impUid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(headers);

        try {
            Map<String, String> uriVariables = Collections.singletonMap("imp_uid", impUid);
            ResponseEntity<PortOneDTO> response =
                    restTemplate.exchange(GET_PAYMENT_INFO_URL, HttpMethod.GET, request, PortOneDTO.class, uriVariables);

            PortOneDTO result = response.getBody();

            if (result == null || result.getCode() != 0) {
                throw new RuntimeException("PortOne 결제 정보 조회 실패: " +
                        (result != null ? result.getMessage() : "응답 없음"));
            }

            return result;
        } catch (Exception e) {
            log.error("결제 정보 조회 중 에러 발생", e);
            throw new RuntimeException("PortOne API 통신 오류(결제 조회)", e);
        }
    }


    private void verifyPayment(String merchantUid, PortOneDTO portOnePaymentInfo) {
        Long dbAmount = paymentDAO.findExpectedAmountByMerchantUid(merchantUid);

        if (dbAmount == null || dbAmount <= 0) {
            throw new RuntimeException("DB에서 주문번호에 대한 예상 금액을 찾을 수 없습니다.");
        }

        Long actualPaidAmount = portOnePaymentInfo.getResponse().getAmount();
        String paidStatus = portOnePaymentInfo.getResponse().getStatus();

        if (!"paid".equals(paidStatus)) {
            throw new RuntimeException("결제 상태가 'paid'가 아닙니다: " + paidStatus);
        }

        log.info("결제 검증 성공: merchantUid={}, amount={}", merchantUid, actualPaidAmount);
    }


    @Override
    public PortOneDTO processPayment(Map<String, Object> paymentData) {
        String impUid = (String) paymentData.get("imp_uid");
        String merchantUid = (String) paymentData.get("merchant_uid");

        if (impUid == null || merchantUid == null) {
            throw new IllegalArgumentException("결제 검증에 필요한 데이터 누락");
        }

        String accessToken = getAccessToken();

        PortOneDTO portOnePaymentInfo = getPaymentInfoFromPortOne(accessToken, impUid);
        if (portOnePaymentInfo == null || portOnePaymentInfo.getResponse() == null) {
            throw new RuntimeException("PortOne에서 결제 응답을 받지 못했습니다.");
        }

        verifyPayment(merchantUid, portOnePaymentInfo);

        Long paidAmount = portOnePaymentInfo.getResponse().getAmount();

        int update = paymentDAO.markSuccessByMerchantUid(
                merchantUid, impUid, paidAmount, PaymentStatus.COMPLETED);

        if (update == 0) {
            throw new RuntimeException("결제 정보 DB 반영 실패");
        }

        PaymentVO payment = paymentDAO.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new RuntimeException("Payment 조회 실패(merchantUid=" + merchantUid + ")"));

        Long paymentId = payment.getId();


        PaymentSocialVO social = new PaymentSocialVO();
        social.setPaymentId(paymentId);
        social.setPaymentSocialName(portOnePaymentInfo.getResponse().getPgProvider());
        social.setPaymentSocialNumber(impUid);
        paymentSocialService.create(social);

        log.info("PaymentSocial 저장 완료: paymentId={}, pg={}, impUid={}",
                paymentId, portOnePaymentInfo.getResponse().getPgProvider(), impUid);

        return portOnePaymentInfo;
    }



    @Override
    public PortOneResponse preparePayment(PaymentPrepareRequest request) {
        Long memberId = request.getMemberId();
        if (memberId == null) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }

        Long orderId = request.getOrderId() != null ? request.getOrderId() : 1L;
        String merchantUid = generateMerchantUid(orderId);

        Long amount = request.getAmount();
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("유효한 결제 금액이 아닙니다.");
        }

        PaymentVO vo = new PaymentVO();
        vo.setPaymentPrice(amount.intValue());
        vo.setPaymentType(request.getPaymentType());
        vo.setPaymentStatus(PaymentStatus.PENDING);
        vo.setOrderId(orderId);
        vo.setMemberId(memberId);
        vo.setMerchantUid(merchantUid);

        paymentDAO.save(vo);

        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("merchant_uid", merchantUid);
        body.put("amount", amount);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        restTemplate.exchange(PREPARE_PAYMENT_URL, HttpMethod.POST, entity, String.class);

        return PortOneResponse.builder()
                .merchantUid(merchantUid)
                .build();
    }

    @Override
    public void verifyPayment(PaymentVerifyRequest request) {
        String impUid = request.getImpUid();
        String merchantUid = request.getMerchantUid();
        Long memberId = request.getMemberId();

        if (impUid == null || merchantUid == null) {
            throw new IllegalArgumentException("결제 검증에 필요한 데이터 누락");
        }

        Map<String, Object> paymentData = new HashMap<>();
        paymentData.put("imp_uid", impUid);
        paymentData.put("merchant_uid", merchantUid);

        // 1) PortOne 검증 + Payment/PG DB 업데이트
        PortOneDTO dto = processPayment(paymentData);

        // 2) 장바구니 정리는 여기서 memberId 기준으로!
        if (memberId != null) {
            orderService.clearCartAfterOrder(memberId);
            log.info(" 장바구니 삭제 완료: memberId={}", memberId);
        } else {
            log.warn("장바구니 삭제 못 함 - memberId가 null입니다. (merchantUid={})", merchantUid);
        }
    }

    @Override
    public void payWithCandy(Long memberId, Long orderId) {
        OrderVO order = orderDAO.selectOrderById(orderId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

//
//        if (!order.getMemberId().equals(memberId)) {
//            throw new IllegalStateException("본인 주문만 결제할 수 있습니다.");
//        }

        int amount = Math.toIntExact(order.getOrderTotalPrice());


        int currentCandy = paymentDAO.selectMemberCandy(memberId);
        if (currentCandy < amount) {
            throw new IllegalStateException("캔디가 부족합니다.");
        }


        int updated = paymentDAO.updateMemberCandy(memberId, amount);
        if (updated == 0) {
            throw new IllegalStateException("캔디 차감에 실패했습니다.");
        }

        PaymentVO payment = new PaymentVO();
        payment.setMemberId(memberId);
        payment.setOrderId(orderId);
        payment.setPaymentPrice(amount);
        payment.setPaymentType("CANDY"); // enum이면 맞게 변경
        payment.setPaymentStatus(PaymentStatus.COMPLETED);

        paymentDAO.save(payment);



    }


}
