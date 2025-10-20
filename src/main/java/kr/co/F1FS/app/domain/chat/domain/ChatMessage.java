package kr.co.F1FS.app.domain.chat.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.global.util.ChatStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long roomId;
    private String sender;
    private String content;
    private String imageUrl;
    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdAt;
    private LocalDateTime sendTime;
    @Enumerated(value = EnumType.STRING)
    private ChatStatus chatStatus;

    @Builder
    public ChatMessage(Long roomId, String sender, String content, String imageUrl, ChatStatus chatStatus){
        this.roomId = roomId;
        this.sender = sender;
        this.content = content;
        this.imageUrl = imageUrl;
        this.sendTime = LocalDateTime.now();
        this.chatStatus = chatStatus;
    }
}
