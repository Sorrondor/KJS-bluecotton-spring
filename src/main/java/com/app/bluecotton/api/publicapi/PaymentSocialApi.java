package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.vo.shop.PaymentSocialVO;
import com.app.bluecotton.domain.vo.shop.PaymentVO;
import com.app.bluecotton.service.PaymentSocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/social/*")
public class PaymentSocialApi {

    private final PaymentSocialService paymentSocialService;

    @PostMapping("create")
    public ResponseEntity<ApiResponseDTO> create(@RequestBody PaymentSocialVO paymentSocialVO) {
        paymentSocialService.create(paymentSocialVO);
        log.info("✅ PaymentSocial created: {}", paymentSocialVO.toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("소셜 결제 정보 등록 성공"));
    }

    @GetMapping("option")
    public ResponseEntity<ApiResponseDTO<Optional<PaymentSocialVO>>> getById(@RequestParam Long id) {
        Optional<PaymentSocialVO> paySocial = paymentSocialService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("소셜 결제 확인 완료", paySocial));

    }

    @GetMapping("list")
    public ResponseEntity<ApiResponseDTO<List<PaymentSocialVO>>> getByPaymentId(@RequestParam Long paymentId) {
        List<PaymentSocialVO> list = paymentSocialService.findByPaymentId(paymentId);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ApiResponseDTO.of("조회된 소셜 결제 정보가 없습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("소셜 결제 리스트 확인 완료", list));
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponseDTO<Void>>  update(@RequestBody PaymentSocialVO paymentSocialVO) {
        paymentSocialService.update(paymentSocialVO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("소셜 결제 업데이트 완료"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<ApiResponseDTO<Void>> delete(@RequestParam Long id) {
        paymentSocialService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("소셜 결제 삭제 완료"));
    }
}
