package com.app.bluecotton.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ChatCreateDTO {
    private String chatTitle;
    private String chatType; // PUBLIC, PRIVATE
    private String chatMemberRole; // OWNER, USER
    private Long memberId;
}
