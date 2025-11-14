package com.app.bluecotton.domain.vo.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMemberVO {
    private Long id;
    private Long memberId;
    private Long chatId;
    private String chatMemberRole;
    private String chatMemberStatus;

    public ChatMemberVO(ChatMessageVO message) {
        this.chatId = message.getChatId();
        this.memberId = message.getChatMessageSenderId(); // ★ 여기 매우 중요
        this.chatMemberRole = "MEMBER";
        this.chatMemberStatus = "ACTIVE";
    }
}