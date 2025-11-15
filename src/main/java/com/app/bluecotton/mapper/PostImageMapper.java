package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.post.PostImageUpdateDTO;
import com.app.bluecotton.domain.vo.post.PostImageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostImageMapper {

    /** 1) 임시 이미지 업로드 */
    void insertImgTemp(PostImageVO vo);

    /** 2) 여러 개 이미지 postId 연결 */
    void updateImgPostId(PostImageUpdateDTO dto);

    /** 3) 단일 이미지 postId 연결 */
    void updatePostId(@Param("imageId") Long imageId,
                      @Param("postId") Long postId);

    /** 4) 게시글의 이미지 전체 조회 */
    List<PostImageVO> selectImagesByPostId(Long postId);

    /** 5) 기본 이미지 삽입 */
    void insertDefaultImage(@Param("postId") Long postId);

    /** 6) 이미지 1개 삭제 */
    void deleteImageById(@Param("imageId") Long imageId);

    /** 7) 수정 시 기존 이미지 전부 삭제 (⭐ 반드시 필요) */
    void deleteImagesByPostId(@Param("postId") Long postId);
}
