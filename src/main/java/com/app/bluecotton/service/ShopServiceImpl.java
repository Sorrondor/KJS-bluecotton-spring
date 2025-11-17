package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.shop.ProductReviewReportVO;
import com.app.bluecotton.repository.ShopDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {


    private final ShopDAO shopDAO;

    @Override
    public List<ProductListResponseDTO> getProductByFilter(Map<String, Object> filterParams) {
        return shopDAO.findProductsByFilter(filterParams);
    }

    @Override
    public ProductDetailResponseDTO getProductDetailHeader(Long id, Long memberId) {
        return shopDAO.findProductDetailHeader(id, memberId);
    }

    @Override
    public ProductDetailResponseDTO getProductDetailLike(Long productId, Long memberId) {
        toggleLike(memberId, productId);
        return shopDAO.findProductDetailHeaderLike(productId, memberId);
    }

    @Override
    public ProductInfoDetailResponseDTO getProductDetailInfo(Long id) {
        return shopDAO.findProductDetailInfo(id);
    }

    @Override
    public List<ProductReviewDetailResponseDTO> getProductReviewDetail(Map<String, Object> reviewParams) {
        return shopDAO.findProductReviewDetail(reviewParams);
    }

    @Override
    public ProductReviewStatsResponseDTO getProductReviewStats(Long id) {
        return shopDAO.findProductReviewStats(id);
    }

    // 찜하기 토글
    @Override
    @Transactional
    public void toggleLike(Long memberId, Long productId) {
        Integer count = shopDAO.findLikeCount(memberId, productId);

        // 찜한 상품이 있을 때
        if (count > 0) {
            // 찜 삭제
            shopDAO.deleteLikedProduct(memberId, productId);
        }
        // 찜한 상품 없을 때
        else {
            // 찜 추가
            shopDAO.insertMyLikedProduct(memberId, productId);
        }
    }

    @Override
    public List<ProductListResponseDTO> getLikedProducts(Long memberId) {
        return shopDAO.findLikedProducts(memberId);
    }

    @Override
    public List<MyReviewListDTO> getMyReviews(Long id) {
        return shopDAO.findMyReviews(id);
    }

    @Override
    public void modifyMyReview(Map<String, Object> modifyReview) {
        shopDAO.updateMyReview(modifyReview);
    }

    @Override
    public void deleteMyReview(Long id) {
        shopDAO.deleteMyReview(id);
    }

    @Override
    public void deleteMyReviewImage(Long id) {
        shopDAO.deleteMyReviewImage(id);
    }

    @Override
    public void insertMyReviewImageOnUpdate(Map<String, Object> updateReview) {
        shopDAO.insertMyReviewImageOnUpdate(updateReview);
    }

    @Override
    public List<MyPageOrderListDTO> getMyOrders(Long memberId) {
        return shopDAO.findMyOrders(memberId);
    }

    @Override
    public Map<String, Object> getReviewModal(Long productId) {
        return shopDAO.getReviewModal(productId);
    }

    @Override
    public void insertMyReview(MyPageReviewWriteDTO myPageReviewWriteDTO) {
        shopDAO.insertMyReview(myPageReviewWriteDTO);
    }

    @Override
    public void insertMyReviewImage(MyPageReviewWriteDTO myPageReviewWriteDTO) {
        shopDAO.insertMyReviewImage(myPageReviewWriteDTO);
    }

    @Override
    public List<MyPageDeliveryListDTO> getMyDeliveryList(Long memberId) {
        return shopDAO.findMyDeliveryList(memberId);
    }

    @Override
    public void deleteMyDeliveryProduct(Long id) {
        shopDAO.deleteMyDeliveryProduct(id);
    }

    // 리뷰 유효성 검사
    @Override
    public int existProductReview(Long productId, Long memberId) {
        return shopDAO.existProductReview(productId, memberId);
    }

    // 리뷰 작성
    @Override
    public void writeReview(MyPageReviewWriteDTO dto) {

        int exist = shopDAO.existProductReview(dto.getProductId(), dto.getMemberId());
        if (exist == 1) {
            throw new IllegalStateException("이미 리뷰를 작성했습니다.");
        }

        shopDAO.insertMyReview(dto);

        if (dto.getImagePath() != null && !dto.getImagePath().isBlank()
                && dto.getImageName() != null && !dto.getImageName().isBlank()) {

            shopDAO.insertMyReviewImage(dto);
        }

    }

    @Override
    public void reportProductReview(ProductReviewReportVO productReviewReportVO) {
        shopDAO.reportProductReview(productReviewReportVO);
    }

    @Override
    public ProductReviewRecommendDTO ProductReviewRecommendDTO(Map<String, Object> recommend) {
        return null;
    }


}




