package com.app.bluecotton.service;


import com.app.bluecotton.domain.dto.SomImageUpdateDTO;
import com.app.bluecotton.domain.vo.som.SomImageVO;

import java.util.List;

public interface SomImageService {

    public void createSomImageTemp(SomImageVO somImageVO);

    public void updateInsertSomImage(SomImageUpdateDTO somImageUpdateDTO);

    public List<SomImageVO> selectImagesBySomId(Long somId);

}
