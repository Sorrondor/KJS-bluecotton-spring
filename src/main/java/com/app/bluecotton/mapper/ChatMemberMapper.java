package com.app.bluecotton.mapper;

import com.app.bluecotton.domain.dto.ChatMemberResponseDTO;
import com.app.bluecotton.domain.vo.chat.ChatMemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMemberMapper {
    public void insertChatMember(ChatMemberVO chatMemberVO);

    public Integer exists(ChatMemberVO chatMemberVO);

    public void delete(ChatMemberVO chatMemberVO);

    public List<ChatMemberResponseDTO> selectMemberListByChatId(Long chatId);
}
