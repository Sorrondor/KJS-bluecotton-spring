package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.SomImageUpdateDTO;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.repository.SomImageDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SomImageServiceImpl implements SomImageService {

    private final SomImageDAO somImageDAO;

    @Override
    public void createSomImageTemp(SomImageVO somImageVO) {
        somImageDAO.insertImgTemp(somImageVO);
    }

    @Override
    public void updateInsertSomImage(SomImageUpdateDTO somImageUpdateDTO) {
        somImageDAO.updateImgSomId(somImageUpdateDTO);
    }

    @Override
    public List<SomImageVO> selectImagesBySomId(Long somId) {
        return somImageDAO.selectImagesBySomId(somId);
    }
}
