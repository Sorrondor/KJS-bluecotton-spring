package com.app.bluecotton.service;

import com.app.bluecotton.domain.dto.MemberResponseDTO;
import com.app.bluecotton.domain.vo.chat.ChatMessageVO;
import com.app.bluecotton.repository.ChatMessageDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageDAO chatMessageDAO;
    private final MemberService memberService;

    @Override
    public List<ChatMessageVO> selectAll(ChatMessageVO chatMessageVO) {
        List<ChatMessageVO> chatMessageVOList = chatMessageDAO.selectAll(chatMessageVO);
        return chatMessageVOList;
    }

    @Override
    public void insert(ChatMessageVO chatMessageVO) {
        chatMessageDAO.insert(chatMessageVO);
    }

    @Override
    public void updateReadStatus(ChatMessageVO chatMessageVO) {
        chatMessageDAO.updateReadStatus(chatMessageVO);
    }

    @Override
    public List<ChatMessageVO> getMessages(Long chatId, int offset, int limit) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("chatId", chatId);
        paramMap.put("offset", offset);
        paramMap.put("limit", limit);
        List<ChatMessageVO> messages = chatMessageDAO.selectMessagesByChatId(paramMap).stream().map((content) -> {
            MemberResponseDTO memberResponseDTO = memberService.getMemberById(content.getChatMessageSenderId());
            content.setMemberName(memberResponseDTO.getMemberName());

            return content;
        }).toList();
        return messages;
    }

}
