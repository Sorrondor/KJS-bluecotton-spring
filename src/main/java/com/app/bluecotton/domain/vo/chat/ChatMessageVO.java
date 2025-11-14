package com.app.bluecotton.domain.vo.chat;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ChatMessageVO {
    private Long id;
    private String chatMessageType;
    private String chatMessageContent;
    private Integer chatMessageReadStatus;
    private LocalDateTime chatMessageCreateAt;

    private Long chatMessageSenderId;
    private Long chatMessageReceiverId;
    private Long chatId;

    {
        this.chatMessageReadStatus = 0;
        this.chatMessageType = "MESSAGE";
    }
}