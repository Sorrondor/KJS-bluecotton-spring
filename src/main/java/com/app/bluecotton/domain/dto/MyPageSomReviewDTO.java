package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.som.SomCheckImageVO;
import com.app.bluecotton.domain.vo.som.SomCheckVO;
import com.app.bluecotton.domain.vo.som.SomReviewVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageSomReviewDTO {
    //    TBSR.MEMBER_ID, TBSR.SOM_ID, TBSR.ID, TBSR.SOM_REVIEW_IS_CHECKED, TBSR.SOM_REVIEW_CONTENT, TBS.SOM_CATEGORY
    private Long somReviewId;
    private Boolean somReviewIsChecked;
    public String getSomReviewIsCheckedYn() {
        return Boolean.TRUE.equals(somReviewIsChecked) ? "Y" : "N";
    }
    private String somCategory;
    private String somReviewContent;
    private Long memberId;
    private Long somId;

    public MyPageSomReviewDTO(SomReviewVO somReviewVO, SomVO somVO) {
        this.somReviewId = somReviewVO.getId();
        this.somReviewIsChecked = somReviewVO.isSomReviewIsChecked();
        this.somReviewContent = somReviewVO.getSomReviewContent();
        this.somCategory = somVO.getSomCategory();
        this.memberId = somReviewVO.getMemberId();
        this.somId = somReviewVO.getSomId();
    }
}
