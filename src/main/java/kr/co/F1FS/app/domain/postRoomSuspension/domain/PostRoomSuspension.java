package kr.co.F1FS.app.domain.postRoomSuspension.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ModifyPostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_room_suspension")
public class PostRoomSuspension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User suspendUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_room_id", nullable = false)
    private PostRoom postRoom;
    private int durationDays;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp suspendUntil;
    private LocalDateTime startSuspension;
    private String description;
    @Column(length = 500)
    private String paraphrase;
    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdAt;

    public void modify(ModifyPostRoomSuspensionDTO dto){
        this.suspendUntil = Timestamp.valueOf(this.startSuspension.plusDays(durationDays + dto.getExtensionDurationDays()));
        this.durationDays = this.durationDays + dto.getExtensionDurationDays();
        this.description = dto.getDescription();
        this.paraphrase = dto.getParaphrase();
    }

    @Builder
    public PostRoomSuspension(User suspendUser, PostRoom postRoom, int durationDays, Timestamp suspendUntil,
                              LocalDateTime startSuspension, String description, String paraphrase){
        this.suspendUser = suspendUser;
        this.postRoom = postRoom;
        this.durationDays = durationDays;
        this.suspendUntil = suspendUntil;
        this.startSuspension = startSuspension;
        this.description = description;
        this.paraphrase = paraphrase;
    }
}
