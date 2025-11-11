package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.SomImageUpdateDTO;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SomImageMapper {
    public void insertImgTemp(SomImageVO somImageVO);

    public void updateImgSomId(SomImageUpdateDTO somImageUpdateDTO);

    public List<SomImageVO> selectImagesBySomId(Long somId);
}
