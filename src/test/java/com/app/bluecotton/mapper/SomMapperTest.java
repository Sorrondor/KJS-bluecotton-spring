package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.vo.som.SomVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j
public class SomMapperTest {

    @Autowired
    private SomMapper somMapper;

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

}
