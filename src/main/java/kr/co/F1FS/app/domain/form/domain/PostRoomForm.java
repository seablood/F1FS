package kr.co.F1FS.app.domain.form.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomFormDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_room_form")
public class PostRoomForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String roomTitle;
    @Lob
    private String description;
    private boolean isPublic;
    private String password;
    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdAt;
    private boolean isConfirmed;
    private String confirmedDescription;

    public void confirm(UpdatePostRoomFormDTO dto){
        this.confirmedDescription = dto.getConfirmedDescription();
        this.isConfirmed = dto.isConfirmed();
    }

    public void modify(ModifyPostRoomFormDTO dto){
        this.roomTitle = dto.getRoomTitle();
        this.description = dto.getDescription();
        this.isPublic = dto.isPublic();
        this.password = dto.getPassword();
    }

    @Builder
    public PostRoomForm(User user, String roomTitle, String description, boolean isPublic, String password){
        this.user = user;
        this.roomTitle = roomTitle;
        this.description = description;
        this.isPublic = isPublic;
        this.password = password;
        this.isConfirmed = false;
        this.confirmedDescription = "아직 승인되지 않았습니다.";
    }
}
