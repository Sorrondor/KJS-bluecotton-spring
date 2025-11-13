package com.app.bluecotton.api.privateapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.dto.post.*;
import com.app.bluecotton.domain.vo.post.*;
import com.app.bluecotton.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/private/post")
@RequiredArgsConstructor
public class PostPrivateApi {

    private final PostService postService;

    // 게시글 등록
    @PostMapping("/write")
    public ResponseEntity<ApiResponseDTO> writePost(
            @RequestBody PostWriteDTO dto,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        Long memberId = currentUser.getId();
        PostVO postVO = dto.postVO();
        postVO.setMemberId(memberId);

        postService.write(postVO, dto.getImageUrls(), dto.getDraftId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("게시글이 등록되었습니다.", Map.of("postId", postVO.getId())));
    }


    // 참여 중 솜 카테고리 목록 조회
    @GetMapping("/categories")
    public ResponseEntity<List<SomCategoryDTO>> getJoinedCategories(
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        return ResponseEntity.ok(postService.getJoinedCategories(currentUser.getId()));
    }

    // 게시글 삭제
    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponseDTO> withdrawPost(@RequestParam Long id) {
        postService.withdraw(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("게시글이 성공적으로 삭제되었습니다.", null));
    }

    // 임시저장 등록
    @PostMapping("/draft")
    public ResponseEntity<ApiResponseDTO> draftPost(
            @RequestBody PostDraftVO postDraftVO,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        postDraftVO.setMemberId(currentUser.getId());
        postService.registerDraft(postDraftVO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("게시글이 임시 저장되었습니다."));
    }

    // 임시저장 조회
    @GetMapping("/draft/{id}")
    public ResponseEntity<ApiResponseDTO> getDraft(@PathVariable("id") Long id) {
        PostDraftVO draft = postService.getDraft(id);
        if (draft == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.of("임시저장된 글을 찾을 수 없습니다.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("임시저장 글 불러오기 성공", draft));
    }

    // 임시저장 삭제
    @DeleteMapping("/draft/delete")
    public ResponseEntity<ApiResponseDTO> deleteDraft(@RequestParam("id") Long id) {
        postService.deleteDraft(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("임시저장 글이 삭제되었습니다."));
    }

    // 게시글 수정 조회
    @GetMapping("/modify/{id}")
    public ResponseEntity<ApiResponseDTO> getPostUpdate(@PathVariable Long id) {
        PostModifyDTO dto = postService.getPostForUpdate(id);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.of("존재하지 않는 게시글입니다.", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("게시글 조회 성공", dto));
    }

    // 게시글 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<ApiResponseDTO> modifyPost(
            @PathVariable Long id,
            @RequestBody PostVO postVO
    ) {
        postVO.setId(id);
        postService.modifyPost(postVO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("게시글이 성공적으로 수정되었습니다."));
    }

    // 댓글 등록
    @PostMapping("/comment")
    public ResponseEntity<ApiResponseDTO> insertComment(
            @RequestBody PostCommentVO postCommentVO,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        postCommentVO.setMemberId(currentUser.getId());
        postService.insertComment(postCommentVO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("댓글이 등록되었습니다."));
    }

    // 답글 등록
    @PostMapping("/reply")
    public ResponseEntity<ApiResponseDTO> insertReply(
            @RequestBody PostReplyVO postReplyVO,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        postReplyVO.setMemberId(currentUser.getId());
        postService.insertReply(postReplyVO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("답글이 등록되었습니다."));
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDTO> deleteComment(@PathVariable Long commentId) {
        postService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("댓글 및 관련 답글이 모두 삭제되었습니다."));
    }

    // 답글 삭제
    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<ApiResponseDTO> deleteReply(@PathVariable Long replyId) {
        postService.deleteReply(replyId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("답글이 삭제되었습니다."));
    }

    // 게시글 좋아요 토글
    @PostMapping("/like/toggle")
    public ResponseEntity<ApiResponseDTO> toggleLike(
            @RequestBody Map<String, Long> payload,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        Long postId = payload.get("postId");
        postService.toggleLike(postId, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("좋아요 토글 완료"));
    }

    // 댓글 좋아요 토글
    @PostMapping("/comment/like/toggle")
    public ResponseEntity<ApiResponseDTO> toggleCommentLike(
            @RequestBody Map<String, Long> payload,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        Long commentId = payload.get("commentId");
        postService.toggleCommentLike(commentId, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("댓글 좋아요 토글 완료"));
    }

    // 답글 좋아요 토글
    @PostMapping("/reply/like/toggle")
    public ResponseEntity<ApiResponseDTO> toggleReplyLike(
            @RequestBody Map<String, Long> payload,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        Long replyId = payload.get("replyId");
        postService.toggleReplyLike(replyId, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("대댓글 좋아요 토글 완료"));
    }

    // 최근 본 게시물
    @PostMapping("/recent/{postId}")
    public ResponseEntity<ApiResponseDTO> recentPost(Authentication authentication, @PathVariable Long postId) {
        MemberResponseDTO currentMember = (MemberResponseDTO) authentication.getPrincipal();
        Long memberId = currentMember.getId();
        postService.registerRecent(memberId, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("최근 본 글 추가"));
    }

    // 신고
    @PostMapping("/report/post")
    public ResponseEntity<ApiResponseDTO> reportPost(
            Authentication authentication,
            @RequestBody PostReportVO postReportVO) {

        MemberResponseDTO currentMember = (MemberResponseDTO) authentication.getPrincipal();
        postReportVO.setMemberId(currentMember.getId());

        postService.reportPost(postReportVO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("게시글 신고 성공"));
    }

    @PostMapping("/report/comment")
    public ResponseEntity<ApiResponseDTO> reportComment(
            Authentication authentication,
            @RequestBody PostCommentReportVO postCommentReportVO) {

        MemberResponseDTO currentMember = (MemberResponseDTO) authentication.getPrincipal();
        postCommentReportVO.setMemberId(currentMember.getId());

        postService.reportComment(postCommentReportVO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("댓글 신고 성공"));
    }

    @PostMapping("/report/reply")
    public ResponseEntity<ApiResponseDTO> reportReply(
            Authentication authentication,
            @RequestBody PostReplyReportVO postReplyReportVO) {

        MemberResponseDTO currentMember = (MemberResponseDTO) authentication.getPrincipal();
        postReplyReportVO.setMemberId(currentMember.getId());

        postService.reportReply(postReplyReportVO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of("답글 신고 성공"));
    }


}
