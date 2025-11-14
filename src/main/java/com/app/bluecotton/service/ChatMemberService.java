package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.ChatMemberResponseDTO;
import com.app.bluecotton.domain.vo.chat.ChatMemberVO;
import com.app.bluecotton.domain.vo.chat.ChatMessageVO;

import java.util.List;

public interface ChatMemberService {
    public void createChatMember(ChatMemberVO chatMemberVO);
    public void delete(ChatMemberVO chatMemberVO);
    public Integer exists(ChatMemberVO chatMemberVO);
    public List<ChatMemberResponseDTO> selectIdByMemberListChatId(Long chatId);
}
