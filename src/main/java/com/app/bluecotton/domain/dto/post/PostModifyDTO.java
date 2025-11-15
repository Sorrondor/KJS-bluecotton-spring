package com.app.bluecotton.domain.dto.post;

import com.app.bluecotton.domain.vo.post.PostVO;
import lombok.Data;

import java.util.List;

@Data
public class PostModifyDTO {

    private Long id;
    private String postTitle;
    private String postContent;
    private Long memberId;
    private Long somId;
    private String somCategory;

    // 이미지 관련
    private List<Long> postImageIds;
    private List<Long> newImageIds;
    private List<Long> deleteImageIds;

    // PostVO 변환 메서드
    public PostVO toPostVO() {
        PostVO vo = new PostVO();
        vo.setId(this.id);
        vo.setPostTitle(this.postTitle);
        vo.setPostContent(this.postContent);
        vo.setMemberId(this.memberId);
        vo.setSomId(this.somId);
        return vo;
    }
}
