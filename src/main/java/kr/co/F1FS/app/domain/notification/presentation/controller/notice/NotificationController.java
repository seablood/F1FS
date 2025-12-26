package kr.co.F1FS.app.domain.notification.presentation.controller.notice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.NotificationUseCase;
import kr.co.F1FS.app.global.presentation.dto.notification.ResponseNotificationDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.SimpleResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
@Tag(name = "Notification 시스템", description = "Notification 관련 서비스")
public class NotificationController {
    private final NotificationUseCase notificationUseCase;

    @GetMapping("/notify-list/public")
    @Operation(summary = "공지/알림(public) 리스트", description = "공지/알림(public) 전체 리스트 반환")
    public ResponseEntity<List<SimpleResponseNotificationDTO>> getNotificationList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                                   @RequestParam(value = "size", defaultValue = "10") int size){
        Page<SimpleResponseNotificationDTO> newPage = notificationUseCase.getNotificationList(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(newPage.getContent());
    }

    @GetMapping("/find/public-notify/{redisId}")
    @Operation(summary = "공지 상세 페이지", description = "특정 공지의 상세 내용을 반환")
    public ResponseEntity<ResponseNotificationDTO> getNotificationByRedisId(@PathVariable Long redisId){
        return ResponseEntity.status(HttpStatus.OK).body(notificationUseCase.getNotificationByRedisId(redisId));
    }

    @GetMapping("/find/public-notify/id/{id}")
    @Operation(summary = "공지 상세 페이지(ID)", description = "특정 공지의 상세 내용을 반환(ID)")
    public ResponseEntity<ResponseNotificationDTO> getNotificationById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(notificationUseCase.getNotificationById(id));
    }
}
