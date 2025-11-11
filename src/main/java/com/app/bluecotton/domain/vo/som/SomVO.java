package com.app.bluecotton.domain.vo.som;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SomVO {
    private Long id;
    private String somTitle;
    private String somCategory;
    private String somAddress;
    private String somType;
    private Date somStartDate;
    private Date somEndDate;
    private Integer somLike;
    private String somContent;
    private Long memberId;

    private List<SomImageVO> somImageList;
}
