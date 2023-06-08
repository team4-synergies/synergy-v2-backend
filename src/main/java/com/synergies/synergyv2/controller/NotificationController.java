package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.auth.CustomUserDetails;
import com.synergies.synergyv2.common.PageRequestDto;
import com.synergies.synergyv2.common.PageResponseDto;
import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.model.dto.NotificationDto;
import com.synergies.synergyv2.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v2/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "전체 공지사항 불러오기")
    @GetMapping
    public ResponseEntity<CommonResponse> getAllNotification(){
        List<NotificationDto> list = notificationService.getAllNotification();
        log.info("Notification getAll");
        log.info(list.toString());
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.OK, list)));
    }

    @Operation(summary = "공지사항 저장")
    @PostMapping
    public ResponseEntity<CommonResponse> createNotification(@RequestBody NotificationDto notificationDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        log.info("NotificationAdd Success");
        notificationDto.setRefUserId(customUserDetails.getUserId());
        notificationService.createNotification(notificationDto);
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.CREATED, "저장 성공")));
    }

    @Operation(summary = "id로 공지사항 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteNotification(@PathVariable("id") int id) {
        log.info("NotificationDelete Success");
        notificationService.deleteNotification(id);
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.OK)));
    }

    @Operation(summary = "id로 공지사항 수정")
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> updateNotification(@PathVariable("id") int id, @RequestBody NotificationDto notificationDto) {
        log.info("NotificationUpdate Success");
        notificationService.updateNotification(id, notificationDto);
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.CREATED)));
    }

    @GetMapping("/page")
    public ResponseEntity<CommonResponse> getNotificationPaging(PageRequestDto pageRequestDto) {
        PageResponseDto notificationEntityPage = notificationService.getNotificationPaging(pageRequestDto);
        log.info("Read Paging All");
        log.info(notificationEntityPage.toString());
        return  ResponseEntity.ok((CommonResponse.toResponse(CommonCode.OK, notificationEntityPage)));
    }
}
