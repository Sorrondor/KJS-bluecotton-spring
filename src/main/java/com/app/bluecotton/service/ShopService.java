package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.shop.ProductReviewReportVO;

import java.util.List;
import java.util.Map;

public interface ShopService {

    // 메인 페이지 상품 조건 조회
    public List<ProductListResponseDTO> getProductByFilter(Map<String,Object> filterParams);


    // 상세 페이지 상단 조회
    public ProductDetailResponseDTO getProductDetailHeader(Long id, Long memberId);

    // 상세 페이지 상품 상단 찜하기 로직
    public ProductDetailResponseDTO getProductDetailLike(Long productId, Long memberId);

    // 상세 페이지 상품 정보 조회
    public ProductInfoDetailResponseDTO getProductDetailInfo(Long id);

    // 상세 페이지 "상품 리뷰" 조회
    public List<ProductReviewDetailResponseDTO> getProductReviewDetail(Map<String,Object> reviewParams);

    // 상세 페이지 "리뷰 평점" 조회
    public ProductReviewStatsResponseDTO getProductReviewStats(Long id);

    // 찜 하기 토글
    public void toggleLike(Long memberId, Long productId);

    // 마이페이지(샵) 찜한 상품 조회
    public List<ProductListResponseDTO> getLikedProducts(Long memberId);


    // 마이페이지(샵) 마이리뷰 조회
    public List<MyReviewListDTO> getMyReviews(Long id);

    // 마이페이지(샵) 마이리뷰 수정
    public void modifyMyReview(Map<String,Object> modifyReview);

    // 마이페이지(샵) 마이리뷰 삭제
    public void deleteMyReview(Long id);


    // 마이리뷰 : 기존 리뷰 이미지 전부 삭제
    public void deleteMyReviewImage(Long id);

    // 마이리뷰 : 수정 시 새 이미지 1장 등록
    public void insertMyReviewImageOnUpdate(Map<String,Object> updateReview);


    //  ------  구매내역 --------
    // 마이페이지(샵) 구매내역 전체조회
    public List<MyPageOrderListDTO> getMyOrders(Long memberId);

    // 마이페이지(샵) 구매내역 리뷰 모달 조회
    public Map<String, Object> getReviewModal(Long productId);

    // 마이페이지(샵) 구매내역 리뷰 작성
    public void insertMyReview(MyPageReviewWriteDTO myPageReviewWriteDTO);

    // 마이페이지(샵) 구매내역 리뷰 이미지 작성
    public void insertMyReviewImage(MyPageReviewWriteDTO myPageReviewWriteDTO);

    // 마이페이지(샵) 배송현황 전체 조회
    public List<MyPageDeliveryListDTO> getMyDeliveryList(Long memberId);

    // 마이페이지(샵) 배송현황 전체 조회
    public void deleteMyDeliveryProduct(Long id);


    // 리뷰 작성 유효성 검사
    public int existProductReview(Long productId, Long memberId);

    public void writeReview(MyPageReviewWriteDTO dto);

    // 상품 리뷰 댓글 신고하기
    public void reportProductReview(ProductReviewReportVO productReviewReportVO);


    // 리뷰 댓글 도움돼요 상태 여부 조회
    public ProductReviewRecommendDTO ProductReviewRecommendDTO(Map<String,Object> recommend);

}
