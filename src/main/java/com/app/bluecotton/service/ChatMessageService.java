package com.app.bluecotton.service;

import com.app.bluecotton.domain.vo.chat.ChatMessageVO;

import java.util.List;

public interface ChatMessageService {
    public List<ChatMessageVO> selectAll(ChatMessageVO chatMessageVO);
    public void insert(ChatMessageVO chatMessageVO);
    public void updateReadStatus(ChatMessageVO chatMessageVO);
    public List<ChatMessageVO> getMessages(Long chatId, int offset, int limit);
}
