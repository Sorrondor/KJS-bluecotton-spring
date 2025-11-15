package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.post.PostImageUpdateDTO;
import com.app.bluecotton.domain.vo.post.PostImageVO;

import java.util.List;

public interface PostImageService {

    // 1) 임시 이미지 저장
    void createPostImageTemp(PostImageVO postImageVO);

    // 2) 여러 개 postId 업데이트
    void updateInsertPostImage(PostImageUpdateDTO dto);

    // 3) 게시글 이미지 목록 조회
    List<PostImageVO> selectImagesByPostId(Long postId);

    // 4) 단일 이미지 postId 연결
    void updatePostId(Long imageId, Long postId);

    // 5) 기본 이미지 넣기
    void insertDefaultImage(Long postId);

    // 6) 이미지 삭제 (수정 시 중요)
    void deleteImageById(Long imageId);
}
