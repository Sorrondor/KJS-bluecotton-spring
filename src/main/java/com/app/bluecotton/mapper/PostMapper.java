package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

    // 게시물 목록 조회
    public List<PostMainDTO> select(
            @Param("somCategory") String somCategory,
            @Param("orderType") String orderType,
            @Param("memberId") Long memberId,
            @Param("q") String q
    );

    // 게시물 등록
    public void insert(PostVO postVO);

    // 오늘 해당 솜에 이미 등록했는지 검사
    public int existsTodayPostInSom(@Param("memberId") Long memberId, @Param("somId") Long somId);

    // 이미지 URL에 PostID 매핑
    public void updatePostIdByUrl(@Param("url") String url, @Param("postId") Long postId);

    // 기본 이미지 등록
    public void insertDefaultImage(
            @Param("postImagePath") String postImagePath,
            @Param("postImageName") String postImageName,
            @Param("postId") Long postId
    );

    // 썸네일 이미지 등록
    public void insertThumbnail(@Param("url") String url, @Param("postId") Long postId);

    // 게시글 삭제 관련
    public void deletePostById(Long postId);
    public void deleteLikesByPostId(Long postId);
    public void deletePostImages(Long postId);
    public void deleteReportsByPostId(Long postId);
    public void deleteRecentsByPostId(Long postId);

    public void insertDraft(PostDraftVO postDraftVO);
    public PostDraftVO selectDraftById(Long id);
    public void deleteDraftById(Long id);

    public List<SomCategoryDTO> findJoinedSomsByMemberId(Long memberId);

    public PostModifyDTO findByIdForUpdate(@Param("id") Long id);
    public void update(PostVO postVO);

    // 게시물 좋아요
    public int existsLike(@Param("postId") Long postId, @Param("memberId") Long memberId);
    public void insertLike(@Param("postId") Long postId, @Param("memberId") Long memberId);
    public void deleteLike(@Param("postId") Long postId, @Param("memberId") Long memberId);

    // 댓글 좋아요
    public int existsCommentLike(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
    public void insertCommentLike(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
    public void deleteCommentLike(@Param("commentId") Long commentId, @Param("memberId") Long memberId);

    // 답글 좋아요
    public int existsReplyLike(@Param("replyId") Long replyId, @Param("memberId") Long memberId);
    public void insertReplyLike(@Param("replyId") Long replyId, @Param("memberId") Long memberId);
    public void deleteReplyLike(@Param("replyId") Long replyId, @Param("memberId") Long memberId);

    // 조회수 _ 최근 본 게시글
    public void updateReadCount(Long postId);
    public void insertOrUpdateRecentView(@Param("memberId") Long memberId, @Param("postId") Long postId);

    // 댓글 / 답글
    public void insertComment(PostCommentVO postCommentVO);
    public void insertReply(PostReplyVO postReplyVO);
    public void deleteComment(Long commentId);
    public void deleteReply(Long replyId);

    //  게시물 상세
    public PostDetailDTO selectPost(Long id);

    public List<PostCommentDTO> selectComment(Long id);

    public List<PostReplyDTO> selectReply(Long id);

    // 신고
    public void insertPostReport(PostReportVO postReportVO);

    public void insertPostCommentReport(PostCommentReportVO postCommentReportVO);

    public void insertPostReplyReport(PostReplyReportVO postReplyReportVO);

}
