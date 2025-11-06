package com.app.bluecotton.domain.dto.post;

import lombok.Data;

@Data
public class PostModifyDTO {
    private Long postId;
    private String postTitle;
    private String postContent;
    private Long memberId;
    private Long somId;
    private String somCategory;
}
