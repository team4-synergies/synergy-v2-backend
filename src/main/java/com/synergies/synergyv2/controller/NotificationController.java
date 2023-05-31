package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.common.PageRequestDto;
import com.synergies.synergyv2.common.PageResponseDto;
import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.exception.DefaultException;
import com.synergies.synergyv2.model.dto.NotificationDto;
import com.synergies.synergyv2.model.entity.NotificationEntity;
import com.synergies.synergyv2.repository.NotificationRepository;
import com.synergies.synergyv2.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

import static com.synergies.synergyv2.common.response.code.CommonCode.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v2/notification")
public class NotificationController {
    private final NotificationService notificationService;
    @Operation(summary = "공지사항 저장")
    @PostMapping
    @Transactional
    public ResponseEntity<CommonResponse> NotificationAdd(@RequestBody NotificationDto notificationDto){
        log.info("NotificationAdd");
        notificationService.createNotification(notificationDto);
        return ResponseEntity.ok((CommonResponse.toResponse(OK, "저장 성공")));
    }

    @Operation(summary = "id로 공지사항 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> NotificationDelete(@PathVariable("id") int id) {
        log.info("NotificationDelete");
        notificationService.deleteById(id);
        return ResponseEntity.ok((CommonResponse.toResponse(OK)));
    }

    @Operation(summary = "id로 공지사항 수정")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CommonResponse> NotificationUpdate(@PathVariable("id") int id, @RequestBody NotificationDto notificationDto) {
        log.info("NotificationUpdate");
        notificationService.updateNotificationById(id, notificationDto);
        return ResponseEntity.ok((CommonResponse.toResponse(OK)));
    }

    @GetMapping("/page")
    public ResponseEntity<CommonResponse> readAllPaging(PageRequestDto pageRequestDto) {
        log.info("Read Paging All");
        return notificationService.searchAllPaging(pageRequestDto);
    }
}
