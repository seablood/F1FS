package kr.co.F1FS.app.domain.form.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_room_delete_form")
public class PostRoomDeleteForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_room_id", nullable = false)
    private PostRoom postRoom;
    private String title;
    private String description;
    private boolean isPostDelete;
    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdAt;
    private boolean isConfirmed;
    private String confirmedDescription;

    public void confirm(UpdatePostRoomDeleteFormDTO dto){
        this.isConfirmed = dto.isConfirmed();
        this.confirmedDescription = dto.getConfirmedDescription();
    }

    public void modify(ModifyPostRoomDeleteFormDTO dto){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.isPostDelete = dto.isPostDelete();
    }

    @Builder
    public PostRoomDeleteForm(User user, PostRoom postRoom, String title, String description, boolean isPostDelete){
        this.user = user;
        this.postRoom = postRoom;
        this.title = title;
        this.description = description;
        this.isPostDelete = isPostDelete;
        this.isConfirmed = false;
        this.confirmedDescription = "";
    }
}
