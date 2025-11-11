package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.SomResponseDTO;
import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.app.bluecotton.domain.vo.som.SomVO;
import com.app.bluecotton.exception.SomException;
import com.app.bluecotton.mapper.SomImageMapper;
import com.app.bluecotton.mapper.SomMapper;
import com.app.bluecotton.repository.SomDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SomServiceImpl implements SomService {

    private final SomDAO somDAO;
    private final SomImageMapper somImageMapper;

    //  솜 등록
    @Override
    public void registerSom(SomVO somVO) {
        somDAO.save(somVO);
    }

    //  솜 상세 조회
    @Override
    public SomResponseDTO findById(Long somId) {
        SomResponseDTO somResponseDTO = somDAO.findById(somId).map(SomResponseDTO::new).orElseThrow(() -> new SomException("솜을 불러오지 못했습니다"));
        List<SomImageVO> somImages = somImageMapper.selectImagesBySomId(somId);
        if(somImages.size() == 0){
            SomImageVO somImageVO = new SomImageVO();
            somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
            somImageVO.setSomId(somId);
            somImageVO.setSomImageName("1762700261.jpg");
            somImages.add(somImageVO);
        }
        somResponseDTO.setSomImageList(somImages);

        return somResponseDTO;
    }

    //  솜 카테고리별 조회
    @Override
    public List<SomResponseDTO> findByCategory(String somCategory) {
        List<SomResponseDTO> somList = somDAO.findByCategory(somCategory).stream().map((som) -> {
            SomResponseDTO somResponseDTO1 = new SomResponseDTO(som);
            List<SomImageVO> somImages = somImageMapper.selectImagesBySomId(som.getId());
            if(somImages.size() == 0){
                SomImageVO somImageVO = new SomImageVO();
                somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
                somImageVO.setSomId(som.getId());
                somImageVO.setSomImageName("1762700261.jpg");
                somImages.add(somImageVO);
            }
            somResponseDTO1.setSomImageList(somImages);
            return somResponseDTO1;
        }).toList();

        return somList;
    }

    //  솜 카테고리별 조회
    @Override
    public List<SomResponseDTO> findByType(String somType) {
        List<SomResponseDTO> somList = somDAO.findByType(somType).stream().map((som) -> {
            SomResponseDTO somResponseDTO1 = new SomResponseDTO(som);
            List<SomImageVO> somImages = somImageMapper.selectImagesBySomId(som.getId());
            if(somImages.size() == 0){
                SomImageVO somImageVO = new SomImageVO();
                somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
                somImageVO.setSomId(som.getId());
                somImageVO.setSomImageName("1762700261.jpg");
                somImages.add(somImageVO);
            }
            somResponseDTO1.setSomImageList(somImages);
            return somResponseDTO1;
        }).toList();


        return somList;
    }

    //  솜 전체 조회
    @Override
    public List<SomResponseDTO> findAllSom() {
        List<SomResponseDTO> somList = somDAO.findAllSom().stream().map((som) -> {
            SomResponseDTO somResponseDTO1 = new SomResponseDTO(som);
            List<SomImageVO> somImages = somImageMapper.selectImagesBySomId(som.getId());
            if(somImages.size() == 0){
                SomImageVO somImageVO = new SomImageVO();
                somImageVO.setSomImagePath("https://image-server.ideaflow.co.kr/uploads/1762700261.jpg");
                somImageVO.setSomId(som.getId());
                somImageVO.setSomImageName("1762700261.jpg");
                somImages.add(somImageVO);
            }
            somResponseDTO1.setSomImageList(somImages);
            return somResponseDTO1;
        }).toList();

        return somList;
    }

    @Override
    public List<String> findAllAddress() {
        return somDAO.findAllSomAddress();
    }

    //  솜 좋아요
    @Override
    public void addLike(Long somId) {
        somDAO.addLike(somId);
    }

    //  솜 삭제
    @Override
    public void withdraw(Long somId) {
        somDAO.withdraw(somId);
    }
}
