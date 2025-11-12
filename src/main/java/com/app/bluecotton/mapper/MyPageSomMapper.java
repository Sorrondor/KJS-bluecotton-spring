package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageSomMapper {

    //    마이페이지 솜 인증 추가
    public void insertSomCheckWithImages(MyPageSomCheckDTO myPageSomCheckDTO);
    //    마이페이지 솜 리뷰 추가
    public void insertSomReview(SomReviewVO somReviewVO);
    //    마이페이지 솜 인증 호출
    public List<MyPageSomCheckDTO> readSomCheck(Long id);
    //    마이페이지 솜 리뷰 호출
    public List<MyPageSomReviewDTO> readSomReview(Long id);

}
