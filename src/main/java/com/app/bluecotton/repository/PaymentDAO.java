package com.app.bluecotton.repository;

import com.app.bluecotton.domain.vo.shop.PaymentStatus;
import com.app.bluecotton.domain.vo.shop.PaymentVO;
import com.app.bluecotton.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentDAO {

    private final PaymentMapper paymentMapper;

    public void save(PaymentVO paymentVO) {
        paymentMapper.insert(paymentVO);
    }

    public Optional<PaymentVO> findById(Long id) {
        return paymentMapper.selectById(id);
    }

    public List<PaymentVO> findByOrderId(Long orderId) {
        return paymentMapper.selectByOrderId(orderId);
    }

    public void updateStatus(PaymentVO paymentVO) {
        paymentMapper.updateStatus(paymentVO);
    }

    public void delete(Long id) {
        paymentMapper.delete(id);
    }

    public Optional<PaymentVO> findByMerchantUid(String merchantUid) {
        return paymentMapper.selectByMerchantUid(merchantUid);
    }

    public Long findExpectedAmountByMerchantUid(String merchantUid) {
        return paymentMapper.selectExpectedAmountByMerchantUid(merchantUid);
    }

    public int updateStatusByMerchantUid(String merchantUid, PaymentStatus status) {
        return paymentMapper.updateStatusByMerchantUid(merchantUid, status);
    }

    public int markSuccessByMerchantUid(String merchantUid, String impUid, long paidAmount, PaymentStatus status) {
        return paymentMapper.markSuccessByMerchantUid(merchantUid, impUid, paidAmount, status);
    }

    public int selectMemberCandy(Long memberId) {
        return paymentMapper.selectMemberCandy(memberId);
    }

    public List<Map<String, Object>> selectByMemberIdCandy(Long memberId) {
        return paymentMapper.selectByMemberIdCandy(memberId);
    }

    public int selectTotalCandySpentByMemberId(Long memberId) {
        return paymentMapper.selectTotalCandySpentByMemberId(memberId);
    }

    public int updateMemberCandy(Long memberId, @Param("amount") int amount) {
        return paymentMapper.updateMemberCandy(memberId, amount);
    }
}
