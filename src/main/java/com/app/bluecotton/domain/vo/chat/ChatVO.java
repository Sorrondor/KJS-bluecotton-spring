package com.app.bluecotton.domain.vo.chat;

import com.app.bluecotton.domain.dto.ChatCreateDTO;
import com.app.bluecotton.domain.dto.ChatMemberResponseDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ChatVO {
    private Long id;
    private String chatTitle;
    private String chatType;
    private Long chatOwnerId;
    private Date chatCreatedAt;
    private String chatStatus;
    private Integer chatMemberCount;

    private List<ChatMemberResponseDTO> members;

    public ChatVO(ChatCreateDTO chatCreateDTO) {
        this.chatTitle = chatCreateDTO.getChatTitle();
        this.chatType = chatCreateDTO.getChatType();
        this.chatOwnerId = chatCreateDTO.getMemberId();
    }
}
