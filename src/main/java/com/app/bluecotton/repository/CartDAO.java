package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.CartResponseDTO;
import com.app.bluecotton.domain.vo.shop.CartVO;
import com.app.bluecotton.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartDAO {

    private final CartMapper cartMapper;

    public void add(CartVO cartVO) {
        cartMapper.insert(cartVO);
    }

    public List<CartResponseDTO> selectAllCart(Long memberId) {
        return cartMapper.selectByMemberId(memberId);
    }

    public Optional<Integer> selectQuantity(Long memberId, Long productId) {
        return cartMapper.selectQuantity(memberId, productId);
    }

    public void increaseProduct(CartVO cartVO) {
        cartMapper.updatePlus(cartVO);
    }

    public void decreaseProduct(CartVO cartVO) {
        cartMapper.updateMinus(cartVO);
    }

    public void deleteProduct(Long memberId, Long productId) {
        cartMapper.delete(memberId, productId);
    }

    public int deleteAllByMember(Long memberId) { return cartMapper.deleteCartByMember(memberId);}
}
