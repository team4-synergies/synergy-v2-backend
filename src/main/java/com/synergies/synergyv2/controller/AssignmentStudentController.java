package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.model.dto.AssignmentResponseDto;
import com.synergies.synergyv2.service.AssignmentStudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/assignments")
public class AssignmentStudentController {

    private final AssignmentStudentService assignmentService;

    @Operation(summary = "학생 과제 제출")
    @PostMapping("/{id}/students")
    public ResponseEntity<CommonResponse> createSubmit(@PathVariable("id") int id, @RequestPart MultipartFile file) {
        if(!file.isEmpty()) {
            assignmentService.createSubmit(id, file);
        }
        // 파일이 없을 때 파일이 없다는 에러 띄우면 좋음

        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.CREATED));
    }

    @Operation(summary = "학생 과제 재제출")
    @PutMapping("/{id}/students")
    public ResponseEntity<CommonResponse> createResubmit(@PathVariable("id") int submitId, @RequestPart MultipartFile file) {
        if(!file.isEmpty()) {
            assignmentService.updateSubmit(submitId, file);
        }
        // 파일이 없을 때 파일이 없다는 에러 띄우면 좋음

        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.CREATED));
    }

    @Operation(summary = "학생 과제 상세 데이터")
    @GetMapping("/{id}/students")
    public ResponseEntity<CommonResponse> getSubmit(@PathVariable("id") int submitId) {
        AssignmentResponseDto.SubmitDetail submitDetail = assignmentService.getSubmit(submitId);
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, submitDetail));
    }


}
