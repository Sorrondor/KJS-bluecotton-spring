package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.CartResponseDTO;
import com.app.bluecotton.domain.vo.shop.CartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CartMapper {

    public void insert(CartVO cartVO);

    List<CartResponseDTO> selectByMemberId(Long memberId);


    Optional<Integer> selectQuantity(@Param("memberId") Long memberId, @Param("productId") Long productId);


    public void updatePlus(CartVO cartVO);

    public void updateMinus(CartVO cartVO);

    public void delete(Long memberId,  Long productId);

    public int deleteCartByMember(Long memberId);

}

