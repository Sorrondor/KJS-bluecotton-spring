package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.domain.dto.ApiResponseDTO;
import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.dto.post.PostDetailDTO;
import com.app.bluecotton.domain.dto.post.PostMainDTO;
import com.app.bluecotton.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/main/post")
public class PostApi {

    private final PostService postService;

    // 게시글 전체 목록 조회
    // (비로그인/로그인 모두 가능)
    @GetMapping("/all")
    public ResponseEntity<ApiResponseDTO<List<PostMainDTO>>> getAllPosts(
            @RequestParam(required = false) String somCategory,
            @RequestParam(defaultValue = "latest") String orderType,
            @RequestParam(required = false) String q,
            @AuthenticationPrincipal MemberResponseDTO currentUser
    ) {
        // 로그인 상태면 memberId 전달, 아니면 null
        Long memberId = (currentUser != null) ? currentUser.getId() : null;

        List<PostMainDTO> posts = postService.getPosts(somCategory, orderType, memberId, q);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseDTO.of("게시글 목록 조회 완료", posts));
    }


}
