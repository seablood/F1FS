package kr.co.F1FS.app.domain.postRoom.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ModifyPostRoomInfoDTO;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ModifyPostRoomPublicDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_room")
@SQLDelete(sql = "UPDATE post_room SET deleted = true WHERE id = ?")
public class PostRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User masterUser;
    private String roomTitle;
    @Lob
    private String description;
    private boolean isPublic;
    private String password;
    private int postCount;
    @CreationTimestamp
    @Column(name = "created_time")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_time")
    private Timestamp updatedAt;
    @Column(nullable = false)
    private boolean deleted = false;

    public void increasePostCount(){
        this.postCount++;
    }

    public void decreasePostCount(){
        this.postCount--;
    }

    public void modifyInfo(ModifyPostRoomInfoDTO dto){
        this.roomTitle = dto.getRoomTitle();
        this.description = dto.getDescription();
    }

    public void modifyIsPublic(ModifyPostRoomPublicDTO dto){
        this.isPublic = dto.isPublic();
        this.password = dto.getPassword();
    }

    @Builder
    public PostRoom(User user, String roomTitle, String description, boolean isPublic, String password){
        this.masterUser = user;
        this.roomTitle = roomTitle;
        this.description = description;
        this.isPublic = isPublic;
        this.password = password;
        this.postCount = 0;
    }
}
