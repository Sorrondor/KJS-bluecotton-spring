package com.app.bluecotton.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostMainDTO {
    private Long postId;
    private String somCategory;
    private String somTitle;
    private Integer postSomDay;
    private String postTitle;
    private String postContent;
    private String memberNickname;
    private String memberProfileUrl;
    private Date postCreateAt;
    private Integer postReadCount;
    private String postImageUrl;
    private Integer postLikeCount;
    private Integer postCommentCount;
    private Integer postIsLike;
}
