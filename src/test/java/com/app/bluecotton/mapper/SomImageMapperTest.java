package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.vo.som.SomImageVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
public class SomImageMapperTest {

    @Autowired
    private SomImageMapper somImageMapper;

    @Test
    public void insert() {
        SomImageVO somImageVO = new SomImageVO();

        somImageVO.setSomImagePath("test_path");
        somImageVO.setSomImageName("test_name");
        somImageMapper.insertImgTemp(somImageVO);
        log.info("testId : {}", somImageVO.getId());
        log.info("솜 임시 이미지 등록 완료, 한번 더");
    }


    @Test
    public void somSelectTest() {
        log.info("list: {}", somImageMapper.selectImagesBySomId(23L));

    }

}
