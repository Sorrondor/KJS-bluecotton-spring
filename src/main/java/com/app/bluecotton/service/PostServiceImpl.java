package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.post.PostMainDTO;
import com.app.bluecotton.domain.dto.post.PostModifyDTO;
import com.app.bluecotton.domain.dto.post.SomCategoryDTO;
import com.app.bluecotton.domain.vo.post.PostDraftVO;
import com.app.bluecotton.domain.vo.post.PostVO;
import com.app.bluecotton.repository.PostDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostDAO postDAO;

//    게시판 목록 조회 서비스
    @Override
    public List<PostMainDTO> getPosts(String somCategory, String orderType, Long memberId) {
        return postDAO.findPosts(somCategory, orderType, memberId);
    }

//    게시판 등록 서비스
    @Override
    public void write(PostVO postVO, List<String> imageUrls) {
//        if (postDAO.existsTodayPostInSom(postVO.getMemberId(), postVO.getSomId())) {
//            throw new IllegalStateException("오늘은 이미 해당 솜 카테고리에 게시글을 등록했습니다.");
//        }

        postDAO.insert(postVO);

        if (imageUrls != null && !imageUrls.isEmpty()) {
            boolean isFirst = true;

            for (String url : imageUrls) {
                postDAO.updatePostIdByUrl(url, postVO.getId());

                // ✅ 첫 번째 이미지일 때만 대표 썸네일로 설정
                if (isFirst) {
                    postDAO.insertThumbnail(url, postVO.getId());
                    isFirst = false;
                }
            }
        } else {
            // ✅ 이미지가 없으면 기본 이미지 등록
            postDAO.insertDefaultImage("/upload/default/", "default_post.jpg", postVO.getId());
        }
    }

    @Override
    public List<SomCategoryDTO> getJoinedCategories(Long memberId) {
        return postDAO.findJoinedCategories(memberId);
    }

//    게시판 삭제 서비스
    @Override
    public void withdraw(Long postId) {
        postDAO.deleteLikesByPostId(postId);
        postDAO.deleteRepliesByPostId(postId);
        postDAO.deleteCommentsByPostId(postId);
        postDAO.deletePostImages(postId);
        postDAO.deleteRecectsByPostId(postId);
        postDAO.deleteReportsByPostId(postId);

        postDAO.deletePostById(postId);
    }

//    임시저장 등록 서비스
    @Override
    public void registerDraft(PostDraftVO postDraftVO) {
        postDAO.insertDraft(postDraftVO);
    }

//    수정 게시판 조회 서비스
    @Override
    public PostModifyDTO getPostForUpdate(Long id) {
        return postDAO.findByIdForUpdate(id);
    }

//    게시판 조회 서비스
    @Override
    public void modifyPost(PostVO postVO) {
        postDAO.update(postVO);
    }
}
