package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.SomImageUpdateDTO;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.mapper.SomImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SomImageDAO {

    private final SomImageMapper somImageMapper;

    public void insertImgTemp(SomImageVO somImageVO){
        somImageMapper.insertImgTemp(somImageVO);
    }

    public void updateImgSomId(SomImageUpdateDTO somImageUpdateDTO){
        somImageMapper.updateImgSomId(somImageUpdateDTO);
    }

    public List<SomImageVO> selectImagesBySomId(Long somId){
        return somImageMapper.selectImagesBySomId(somId);
    }

}
