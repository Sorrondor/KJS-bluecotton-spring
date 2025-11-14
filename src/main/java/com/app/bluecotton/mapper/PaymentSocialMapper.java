package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.vo.shop.PaymentSocialVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PaymentSocialMapper {

    public void insert(PaymentSocialVO paymentSocialVO);

    public  Optional<PaymentSocialVO> findById(Long id);

    public Optional<PaymentSocialVO> findByIdWithPgProvider(Long id);

    public List<PaymentSocialVO> findByPaymentIdWithPgProvider(Long paymentId);

    public void update(PaymentSocialVO paymentSocialVO);

    public void delete(Long id);
}
