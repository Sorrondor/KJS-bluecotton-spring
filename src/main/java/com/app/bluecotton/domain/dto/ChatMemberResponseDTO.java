package com.app.bluecotton.domain.dto;

import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMemberResponseDTO {
    private Long id;
    private String memberName;
    private String chatMemberRole;
}
