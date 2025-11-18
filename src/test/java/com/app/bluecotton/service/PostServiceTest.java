package com.app.bluecotton.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class PostServiceTest {

    @Autowired
    private PostService postService;

    Random random = new Random();

    @Test
    void toggleLikeRandomByRange() {
        Random random = new Random();

        for (long postId = 1L; postId <= 100; postId++) {

            // 1) 구간별 최대 좋아요 개수 설정
            int maxLikes;
            if (postId >= 90 && postId <= 100) {          // 100 ~ 90
                maxLikes = 5;
            } else if (postId >= 70 && postId < 90) {     // 90 ~ 70
                maxLikes = 15;
            } else if (postId >= 40 && postId < 70) {     // 70 ~ 40
                maxLikes = 30;
            } else {                                      // 40 ~ 1
                maxLikes = 50;
            }

            // 실제 좋아요 개수 = 0 ~ maxLikes 랜덤
            int likeCount = random.nextInt(maxLikes + 1);

            // 2) likeCount 만큼 멤버를 랜덤으로 골라 toggle
            for (int k = 0; k < likeCount; k++) {
                long memberId = 1 + random.nextInt(50);   // 멤버 1~50
                postService.toggleLike(postId, memberId);
            }
        }
    }


    @Test
    void toggleCommentLike() {
        Random random = new Random();

        // 댓글 ID 1 ~ 639
        for (long commentId = 1L; commentId <= 639L; commentId++) {

            // 1) 구간별 최대 좋아요 개수 설정
            int maxLikes;
            if (commentId >= 500 && commentId <= 600) {     // 639 ~ 500
                maxLikes = 4;
            } else {                                        // 499 ~ 1
                maxLikes = 15;
            }

            // 실제 좋아요 개수 = 0 ~ maxLikes 랜덤
            int likeCount = random.nextInt(maxLikes + 1);

            // 2) 좋아요 count 만큼 멤버 랜덤으로 toggle
            for (int k = 0; k < likeCount; k++) {
                long memberId = 1 + random.nextInt(50);     // member 1~50 랜덤
                postService.toggleCommentLike(commentId, memberId);
            }
        }
    }

    @Test
    void toggleReplyLikeRandom() {
        Random random = new Random();

        // replyId: 1 ~ 299
        for (long replyId = 1L; replyId <= 299L; replyId++) {

            // 0 ~ 3 랜덤
            int likeCount = random.nextInt(4);

            for (int k = 0; k < likeCount; k++) {
                long memberId = 1 + random.nextInt(50);  // member 1~50
                postService.toggleReplyLike(replyId, memberId);
            }
        }
    }

}