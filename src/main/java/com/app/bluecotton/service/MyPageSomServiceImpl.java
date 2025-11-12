package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.*;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import com.app.bluecotton.repository.MyPagePostDAO;
import com.app.bluecotton.repository.MyPageSomDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageSomServiceImpl implements MyPageSomService {
    private final MyPageSomDAO myPageSomDAO;

    //    마이페이지 솜 인증 추가
    @Override
    public void insertSomCheckWithImages(MyPageSomCheckDTO myPageSomCheckDTO){
        myPageSomDAO.insertSomCheckWithImages(myPageSomCheckDTO);
    }

    //    마이페이지 솜 리뷰 추가
    @Override
    public void insertSomReview(SomReviewVO somReviewVO){
        myPageSomDAO.insertSomReview(somReviewVO);
    }

    //    마이페이지 솜 인증 호출
    @Override
    public List<MyPageSomCheckDTO> readSomCheck(Long id){
        return myPageSomDAO.readSomCheck(id);
    }

    //    마이페이지 솜 리뷰 호출
    @Override
    public List<MyPageSomReviewDTO> readSomReview(Long id){
        return myPageSomDAO.readSomReview(id);
    }

}
