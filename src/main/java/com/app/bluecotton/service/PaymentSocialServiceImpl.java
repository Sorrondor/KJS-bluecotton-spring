package com.app.bluecotton.service;

import com.app.bluecotton.domain.vo.shop.PaymentSocialVO;
import com.app.bluecotton.mapper.PaymentSocialMapper;
import com.app.bluecotton.repository.PaymentSocialDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class PaymentSocialServiceImpl implements PaymentSocialService {

    private final PaymentSocialDAO paymentSocialDAO;

    @Override
    public void create(PaymentSocialVO paymentSocialVO) {
        if(paymentSocialVO.getPaymentId() == null){
            throw new IllegalArgumentException("paymentId는 필수입니다.");
        }
        paymentSocialDAO.save(paymentSocialVO);
    }

    @Override
    public Optional<PaymentSocialVO> findById(Long id) {
        return paymentSocialDAO.findById(id);
    }

    @Override
    public List<PaymentSocialVO> findByPaymentId(Long paymentId) {
        return paymentSocialDAO.findByPaymentIdWithPgProvider(paymentId);
    }

    @Override
    public void update(PaymentSocialVO paymentSocialVO) {
        if(paymentSocialVO.getId() == null){
            throw new IllegalArgumentException("Id는 필수입니다");
        }
        paymentSocialDAO.update(paymentSocialVO);
    }

    @Override
    public void deleteById(Long id) {
        paymentSocialDAO.delete(id);
    }

}
