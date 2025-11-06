package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.post.PostMainDTO;
import com.app.bluecotton.domain.vo.post.PostVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    void selectTest() {
        List<PostMainDTO> posts = postMapper.select(null, null, 1L);
        System.out.println("조회된 개수 = " + posts.size());
        posts.forEach(System.out::println);
    }

    // ✅ 게시글 등록 테스트
    @Test
    void insertTest() {
        PostVO postVO = new PostVO();
        postVO.setPostTitle("테스트용 게시글 제목");
        postVO.setPostContent("테스트 게시글입니다.");
        postVO.setMemberId(1L);
        postVO.setSomId(10L);

        postMapper.insert(postVO);
        log.info("삽입 완료! 새 게시글 ID: {}", postVO.getId());
    }

    // ✅ 오늘 해당 솜에 게시글 등록 여부 검사
    @Test
    void existsTodayPostInSomTest() {
        Long memberId = 1L;
        Long somId = 21L;
        int count = postMapper.existsTodayPostInSom(memberId, somId);
        log.info("오늘 등록된 게시글 개수: {}", count);
    }

    // ✅ 특정 이미지 URL에 게시글 ID 매핑
    @Test
    void updatePostIdByUrlTest() {
        String imageUrl = "/upload/test_post_image.jpg"; // DB에 임시 저장된 이미지 경로
        Long postId = 37L; // 이미 존재하는 게시글 ID로 테스트
        postMapper.updatePostIdByUrl(imageUrl, postId);
        log.info("이미지 매핑 완료 (postId = {})", postId);
    }

}