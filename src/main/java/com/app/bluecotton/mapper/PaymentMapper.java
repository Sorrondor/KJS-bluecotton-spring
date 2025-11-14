package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.vo.shop.PaymentStatus;
import com.app.bluecotton.domain.vo.shop.PaymentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface PaymentMapper {

    public void insert(PaymentVO paymentVO);


    public Optional<PaymentVO> selectById(long id);

    public List<PaymentVO> selectByOrderId(Long orderId);

    public void updateStatus(PaymentVO paymentVO);

    public void delete(Long id);

    public Optional<PaymentVO> selectByMerchantUid(String merchantUid);

    public Long selectExpectedAmountByMerchantUid(String merchantUid);

    public int updateStatusByMerchantUid(String merchantUid, PaymentStatus status);

    public int markSuccessByMerchantUid(String merchantUid, String impUid, long paidAmount, PaymentStatus status);

    public int selectMemberCandy(Long memberId);

    public List<Map<String, Object>> selectByMemberIdCandy(Long memberId);

    public int selectTotalCandySpentByMemberId(Long memberId);

    public int updateMemberCandy(Long memberId, @Param("amount") int amount);

}

