package com.app.bluecotton.api.publicapi;


import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.post.PostMainDTO;
import com.app.bluecotton.domain.dto.post.PostModifyDTO;
import com.app.bluecotton.domain.dto.post.PostWriteDTO;
import com.app.bluecotton.domain.dto.post.SomCategoryDTO;
import com.app.bluecotton.domain.vo.post.PostDraftVO;
import com.app.bluecotton.domain.vo.post.PostVO;
import com.app.bluecotton.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/main/post")
public class PostApi {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDTO<List<PostMainDTO>>> getAllPosts(
            @RequestParam(required = false) String somCategory,
            @RequestParam(required = false) String orderType
    ) {
        Long memberId = 1L; // 로그인 가정
        List<PostMainDTO> posts = postService.getPosts(somCategory, orderType, memberId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("게시글 목록 조회 완료", posts));
    }

    /**
     *    게시글 등록 API
     * - 카테고리(SOM_ID)별 하루 1개 제한
     * - 이미지 미첨부 시 기본 이미지 자동 등록
     */
    @PostMapping("/write")
    public ResponseEntity<ApiResponseDTO> writePost(@RequestBody PostWriteDTO dto) {
        postService.write(dto.postVO(), dto.getImageUrls());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("게시글이 등록 되었습니다."));
    }

    // ✅ 회원이 참여 중인 솜 카테고리 목록 조회 (수정/등록용 드롭다운)
    @GetMapping("/categories/{memberId}")
    public ResponseEntity<List<SomCategoryDTO>> getJoinedCategories(@PathVariable Long memberId) {
        return ResponseEntity.ok(postService.getJoinedCategories(memberId));
    }

//    게시판 삭제 api
    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponseDTO> withdrawPost(@RequestParam Long id) {
        postService.withdraw(id);
        return ResponseEntity.ok(ApiResponseDTO.of("게시글이 성공적으로 삭제 되었습니다"));
    }

//    임시저장 등록 api
    @PostMapping("/draft")
    public ResponseEntity<ApiResponseDTO> draftPost(@RequestBody PostDraftVO postdraftVO) {
        postService.registerDraft(postdraftVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.of("게시글이 임시 저장되었습니다."));
    }

    // 게시글 수정 조회 (수정페이지 진입 시)
    @GetMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO> getPostDetail(@PathVariable Long id) {
        PostModifyDTO dto = postService.getPostForUpdate(id);

        if (dto == null) {
            // 존재하지 않는 게시글이면 404 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.of("존재하지 않는 게시글입니다.", null));
        }

        // 정상적으로 불러왔을 때는 OK + 데이터 반환
        return ResponseEntity.ok(ApiResponseDTO.of("게시글 조회 성공", dto));
    }

    // 게시글 수정
    @PutMapping("/modify")
    public ResponseEntity<ApiResponseDTO> modifyPost(@RequestBody PostVO postVO) {
        postService.modifyPost(postVO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDTO.of("게시글이 성공적으로 수정되었습니다."));
    }

}