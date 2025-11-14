package com.app.bluecotton.repository;

import com.app.bluecotton.domain.dto.ChatMemberResponseDTO;
import com.app.bluecotton.domain.vo.chat.ChatMemberVO;
import com.app.bluecotton.mapper.ChatMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class ChatMemberDAO {

    private final ChatMemberMapper chatMemberMapper;

    public void insertChatMember(ChatMemberVO chatMemberVO) {
        chatMemberMapper.insertChatMember(chatMemberVO);
    }

    public Integer exists(ChatMemberVO chatMemberVO) {
        return chatMemberMapper.exists(chatMemberVO);
    }

    public void delete(ChatMemberVO chatMemberVO) {
        chatMemberMapper.delete(chatMemberVO);
    }

    public List<ChatMemberResponseDTO> selectIdByMemberListChatId(Long chatId){
        return chatMemberMapper.selectMemberListByChatId(chatId);
    }

}
