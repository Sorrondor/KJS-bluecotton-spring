package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.post.PostMainDTO;
import com.app.bluecotton.domain.dto.post.PostModifyDTO;
import com.app.bluecotton.domain.dto.post.SomCategoryDTO;
import com.app.bluecotton.domain.vo.post.PostDraftVO;
import com.app.bluecotton.domain.vo.post.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
@Mapper

public interface PostMapper {
//    게시물 목록 조회
    List<PostMainDTO> select(
            @Param("somCategory") String somCategory,
            @Param("orderType") String orderType,
            @Param("memberId") Long memberId
    );

//    게시물 추가
    void insert(PostVO postVO);

//    게시물 등록 검사
    int existsTodayPostInSom(@Param("memberId") Long memberId, @Param("somId") Long somId);

//    이미지 url 등록
    void updatePostIdByUrl(@Param("url") String url, @Param("postId") Long postId);

//    기본 이미지 등록
    void insertDefaultImage(
        @Param("postImagePath") String postImagePath,
        @Param("postImageName") String postImageName,
        @Param("postId") Long postId);

//    썸네일 이미지 등록
    void insertThumbnail(@Param("url") String url, @Param("postId") Long postId);

//    게시글 삭제
    void deletePostById(Long postId);
//    게시글 좋아요 삭제
    void deleteLikesByPostId(Long postId);
//    댓글 삭제
    void deleteCommentsByPostId(Long postId);
//    답글 삭제
    void deleteRepliesByPostId(Long postId);
//    이미지 삭제
    void deletePostImages(Long postId);
//    신고 삭제
    void deleteReportsByPostId(Long postId);
//    최근본 삭제
    void deleteRecectsByPostId(Long postId);

//    임시저장 등록
    void insertDraft(PostDraftVO postDraftVO);

//    회원이 참여 중인 솜 카테고리 조회 (드롭다운용)
    List<SomCategoryDTO> findJoinedCategories(Long memberId);

    // 게시글 수정 조회
    PostModifyDTO findByIdForUpdate(@Param("id") Long id);

    // 게시글 수정
    void update(PostVO postVO);

}
