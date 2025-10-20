package kr.co.F1FS.app.domain.chat.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.chat.presentation.dto.ModifyChatRoomDTO;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String masterUser;
    private int memberCount;
    private LocalDateTime createTime;
    private LocalDateTime lastChatMessage;

    public void modify(ModifyChatRoomDTO dto){
        this.name = dto.getName();
        this.description = dto.getDescription();
    }

    public void increaseMember(){
        this.memberCount++;
    }

    public void decreaseMember(){
        this.memberCount--;
    }

    public void updateLastChatMessage(LocalDateTime sendTime){
        this.lastChatMessage = sendTime;
    }

    @Builder
    public ChatRoom(String name, String description, String masterUser){
        this.name = name;
        this.description = description;
        this.masterUser = masterUser;
        this.memberCount = 0;
        this.createTime = LocalDateTime.now();
        this.lastChatMessage = LocalDateTime.now();
    }
}
