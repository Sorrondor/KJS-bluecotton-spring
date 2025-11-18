package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.CartResponseDTO;
import com.app.bluecotton.domain.vo.shop.CartVO;
import com.app.bluecotton.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart/*")
public class CartApi {

    private final CartService cartService;

    @PostMapping("add")
    public ResponseEntity<ApiResponseDTO> addCart(@RequestBody CartVO cartVO) {
        cartService.addCart(cartVO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("장바구니에 담았습니다."));
    }

    @GetMapping("list")
    public ResponseEntity<ApiResponseDTO<List<CartResponseDTO>>> getCart(@RequestParam Long memberId) {
        List<CartResponseDTO> cartList = cartService.getCartList(memberId);
        log.info("cartList={}", cartList);
        ApiResponseDTO<List<CartResponseDTO>> apiResponseDTO = new ApiResponseDTO<>("장바구니 요청 성공", cartList);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponseDTO);
    }

    @PutMapping("plus")
    public ResponseEntity<ApiResponseDTO> updatePlusCart(@RequestBody CartVO cartVO) {
        cartService.updatePlusCart(cartVO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("상품 수량 추가 성공"));
    }

    @PutMapping("minus")
    public ResponseEntity<ApiResponseDTO<CartVO>> updateMinusCart(@RequestBody CartVO cartVO) {
        cartService.updateMinusCart(cartVO);
        return ResponseEntity.ok(ApiResponseDTO.of("상품 수량 감소 성공"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<ApiResponseDTO> deleteCart(@RequestParam Long memberId,
                                                     @RequestParam Long productId) {
        cartService.deleteCart(memberId, productId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("상품 삭제 성공"));
    }
}
