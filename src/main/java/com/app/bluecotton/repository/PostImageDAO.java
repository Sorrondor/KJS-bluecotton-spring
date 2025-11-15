package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.post.PostImageUpdateDTO;
import com.app.bluecotton.domain.vo.post.PostImageVO;
import com.app.bluecotton.mapper.PostImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostImageDAO {

    private final PostImageMapper postImageMapper;

    // 1) 임시 저장 이미지 등록
    public void insertImgTemp(PostImageVO vo) {
        postImageMapper.insertImgTemp(vo);
    }

    // 2) 여러 이미지 postId 연결
    public void updateImgPostId(PostImageUpdateDTO dto) {
        postImageMapper.updateImgPostId(dto);
    }

    // 3) 단일 이미지 postId 연결
    public void updatePostId(Long imageId, Long postId) {
        postImageMapper.updatePostId(imageId, postId);
    }

    // 4) 게시글 이미지 전체 조회
    public List<PostImageVO> selectImagesByPostId(Long postId) {
        return postImageMapper.selectImagesByPostId(postId);
    }

    // 5) 기본 이미지 삽입
    public void insertDefaultImage(Long postId) {
        postImageMapper.insertDefaultImage(postId);
    }

    // 6) 이미지 1개 삭제 (수정 시 필요)
    public void deleteImageById(Long imageId) {
        postImageMapper.deleteImageById(imageId);
    }
}
