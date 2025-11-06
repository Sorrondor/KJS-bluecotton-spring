package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.post.PostMainDTO;
import com.app.bluecotton.domain.dto.post.PostModifyDTO;
import com.app.bluecotton.domain.dto.post.SomCategoryDTO;
import com.app.bluecotton.domain.vo.post.PostDraftVO;
import com.app.bluecotton.domain.vo.post.PostVO;
import com.app.bluecotton.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDAO {
    private final PostMapper postMapper;

//    게시글 목록 조회
    public List<PostMainDTO> findPosts(String somCategory, String orderType, Long memberId) {
        return postMapper.select(somCategory, orderType, memberId);
    }

//    게시물 등록
    public void insert(PostVO postVO) {
        postMapper.insert(postVO);
    }

//    게시물 등록 검사
    public boolean existsTodayPostInSom(Long memberId, Long somId) {
        return postMapper.existsTodayPostInSom(memberId, somId) > 0;
    }

//    게시물 이미지 삽입
    public void updatePostIdByUrl(String url, Long postId) {
        postMapper.updatePostIdByUrl(url, postId);
    }
//      게시물 기본 이미지
    public void insertDefaultImage(String postImagePath, String postImageName, Long postId) {
        postMapper.insertDefaultImage(postImagePath, postImageName, postId);
    }

    public void insertThumbnail(String url, Long postId) {
        postMapper.insertThumbnail(url, postId);
    }

//    게시물 삭제
    public void deletePostById(Long id){
        postMapper.deletePostById(id);
    }

    public void deleteLikesByPostId(Long postId) {
        postMapper.deleteLikesByPostId(postId);
    }

    public void deleteCommentsByPostId(Long postId){
        postMapper.deleteCommentsByPostId(postId);
    }

    public void deleteRepliesByPostId(Long postId) {
        postMapper.deleteRepliesByPostId(postId);
    }

    public void deletePostImages(Long postId) {
        postMapper.deletePostImages(postId);
    }

    public void deleteReportsByPostId(Long postId) {
        postMapper.deleteReportsByPostId(postId);
    }

    public void deleteRecectsByPostId(Long postId) {
        postMapper.deleteRecectsByPostId(postId);
    }

//    임시 저장 등록
    public void insertDraft(PostDraftVO  postDraftVO) {
        postMapper.insertDraft(postDraftVO);
    }
//     드롭다운 조회
    public List<SomCategoryDTO> findJoinedCategories(Long memberId) {
        return postMapper.findJoinedCategories(memberId);
    }

    // 수정용 게시글 조회
    public PostModifyDTO findByIdForUpdate(Long id) {
        return postMapper.findByIdForUpdate(id);
    }

    // 게시글 수정
    public void update(PostVO postVO) {
        postMapper.update(postVO);
    }

}
