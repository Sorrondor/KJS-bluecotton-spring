package com.app.bluecotton.service;

import com.app.bluecotton.domain.vo.shop.PaymentSocialVO;

import java.util.List;
import java.util.Optional;

public interface PaymentSocialService {

    public void create(PaymentSocialVO paymentSocialVO);

    public Optional<PaymentSocialVO> findById(Long id);

    public List<PaymentSocialVO> findByPaymentId(Long paymentId);

    public void update(PaymentSocialVO paymentSocialVO);

    public void deleteById(Long id);

}
