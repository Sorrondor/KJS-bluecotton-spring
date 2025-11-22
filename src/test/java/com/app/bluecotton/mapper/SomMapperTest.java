package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.SomJoinResponseDTO;
import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomJoinVO;
import com.app.bluecotton.domain.vo.som.SomLikeVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@Slf4j
public class SomMapperTest {

    @Autowired
    private SomMapper somMapper;

    private final Random random = new Random();

    @Test
    public void insert() {
        SomVO somVO = new SomVO();
        somVO.setSomTitle("테스트용 솜");
        somVO.setSomCategory("study");
        somVO.setSomAddress("서울");
        somVO.setSomStartDate(new Date());
        somVO.setSomEndDate(new Date());
        somVO.setSomContent("내용");
        somVO.setSomType("solo");
        somMapper.insert(somVO);
        log.info("{}", somVO.getId());
    }

    @Test
    public void selectListTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("somCategory", "study");
        params.put("somType", "solo");
        params.put("page", 1);

        log.info("list: {}", somMapper.selectSomListByCategoryAndType(params));
    }

    @Test
    public void somLikeDummy() {
        somJoinDeleteAll();
        somLikeDeleteAll();

        generateRandomLikes();
        generateRandomJoins();
    }

    private void somLikeDeleteAll(){
        List<SomResponseDTO> somList = somMapper.selectAll();

        for(SomResponseDTO som : somList){
            List<SomLikeVO> somLikes = somMapper.selectSomLikeList(som.getId());
            for(SomLikeVO somLike : somLikes){
                somMapper.deleteSomLikeById(somLike.getId());
            }
        }
    }

    private void somJoinDeleteAll(){
        List<SomResponseDTO> somList = somMapper.selectAll();

        for(SomResponseDTO som : somList){
            List<SomJoinResponseDTO> somJoins = somMapper.selectAllSomJoinList(som.getId());
            for(SomJoinResponseDTO somJoin : somJoins){
                somMapper.deleteSomJoinById(somJoin.getId());
            }
        }
    }

    private void generateRandomJoins() {
        List<SomResponseDTO> somList = somMapper.selectAll();

        for (SomResponseDTO som : somList) {

            Long leaderId = som.getMemberId();
            String type = som.getSomType();

            // 1) 리더는 무조건 JOIN
            SomJoinVO leaderJoin = new SomJoinVO();
            leaderJoin.setSomId(som.getId());
            leaderJoin.setMemberId(leaderId);
            somMapper.insertSomJoin(leaderJoin);

            // solo면 리더만 끝
            if ("solo".equalsIgnoreCase(type)) continue;

            int joinCount = random.nextInt(8) + 3;  // 3~10명 랜덤

            Set<Long> usedMembers = new HashSet<>();
            usedMembers.add(leaderId);

            for (int i = 0; i < joinCount; i++) {
                Long memberId = random.nextLong(50) + 1;

                if (usedMembers.contains(memberId)) continue;

                SomJoinVO join = new SomJoinVO();
                join.setSomId(som.getId());
                join.setMemberId(memberId);

                somMapper.insertSomJoin(join);
                usedMembers.add(memberId);
            }
        }
    }

    private void generateRandomLikes() {
        List<SomResponseDTO> somList = somMapper.selectAll();

        for (SomResponseDTO som : somList) {
            int likeCount = random.nextInt(10) + 10; // 10~20 좋아요

            Set<Long> usedMembers = new HashSet<>();

            for (int i = 0; i < likeCount; i++) {
                Long memberId = random.nextLong(50) + 1; // 1~50

                if (usedMembers.contains(memberId)) continue;

                SomLikeVO like = new SomLikeVO();
                like.setSomId(som.getId());
                like.setMemberId(memberId);

                somMapper.insertSomLike(like);
                usedMembers.add(memberId);
            }
        }
    }
}
