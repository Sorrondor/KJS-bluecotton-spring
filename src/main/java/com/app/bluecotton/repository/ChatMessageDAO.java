package com.app.bluecotton.repository;

import com.app.bluecotton.domain.vo.chat.ChatMessageVO;
import com.app.bluecotton.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ChatMessageDAO {
    private final ChatMessageMapper chatMessageMapper;

    public void insert(ChatMessageVO chatMessageVO){
        chatMessageMapper.insert(chatMessageVO);
    }

    public void updateReadStatus(ChatMessageVO chatMessageVO){
        chatMessageMapper.updateReadStatus(chatMessageVO);
    }

    public List<ChatMessageVO> selectAll(ChatMessageVO chatMessageVO){
        return chatMessageMapper.selectAll(chatMessageVO);
    }

    public List<ChatMessageVO> selectMessagesByChatId(Map<String, Object> paramMap){
        return chatMessageMapper.selectMessagesByChatId(paramMap);
    }
}
