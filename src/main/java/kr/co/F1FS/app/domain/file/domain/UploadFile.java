package kr.co.F1FS.app.domain.file.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "upload_file")
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalName;
    private String storedName;
    private String url;
    private Long size;

    @Builder
    public UploadFile(String originalName, String storedName, String url, Long size){
        this.originalName = originalName;
        this.storedName = storedName;
        this.url = url;
        this.size = size;
    }
}
