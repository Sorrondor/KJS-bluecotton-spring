package com.app.bluecotton.domain.dto;

import com.app.bluecotton.domain.vo.member.MemberVO;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SomResponseDTO {
    private Long id;
    private String somTitle;
    private String somCategory;
    private String somAddress;
    private String somType;
    private Date somStartDate;
    private Date somEndDate;
    private Integer somLikeCount;
    private String somContent;
    private Integer somCount;
    private Long memberId;
    private Boolean isSomLike;
    private String somTitleImagePath;
    private String somTitleImageName;

    private List<SomImageVO> somImageList;
    private List<SomJoinResponseDTO> somJoinList;
    private MemberSomLeaderResponseDTO memberSomLeader;

    {
        this.setSomTitleImagePath("https://image-server.ideaflow.co.kr/uploads/som/2025/11/10/default_post_25987fce-7bfb-43bb-8984-f4bae4daacb5.jpg");
        this.setSomTitleImageName("som.jpg");
    }

    public SomResponseDTO(SomVO somVO) {
        this.id = somVO.getId();
        this.somTitle = somVO.getSomTitle();
        this.somCategory = somVO.getSomCategory();
        this.somAddress = somVO.getSomAddress();
        this.somType = somVO.getSomType();
        this.somStartDate = somVO.getSomStartDate();
        this.somEndDate = somVO.getSomEndDate();
        this.somContent = somVO.getSomContent();
        this.memberId = somVO.getMemberId();
    }
}
