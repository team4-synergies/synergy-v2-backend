package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.config.S3.FileService;
import com.synergies.synergyv2.model.dto.AssignmentRequestDto;
import com.synergies.synergyv2.model.dto.AssignmentResponseDto;
import com.synergies.synergyv2.repository.mapping.AssignmentMapping;
import com.synergies.synergyv2.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    private final FileService fileService;

    @Operation(summary = "과제 등록")
    @PostMapping("/admin")
    public ResponseEntity<CommonResponse> createAssignment(@ModelAttribute AssignmentRequestDto.AssignmentRegister assignment,
                                                           @RequestPart(value = "file", required = false) MultipartFile file) {

        assignmentService.createAssignment(assignment, file);
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.CREATED));
    }

    @Operation(summary = "과제 수정")
    @PutMapping("/{id}/admin")
    public ResponseEntity<CommonResponse> updateAssignment(@PathVariable("id") int id,
                                                           @ModelAttribute AssignmentRequestDto.AssignmentRegister assignment,
                                                           @RequestPart(value = "file", required = false) MultipartFile file) {

        assignmentService.updateAssignment(id, assignment, file);
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.CREATED));
    }

    @Operation(summary = "과제 삭제")
    @DeleteMapping("/{id}/admin")
    public ResponseEntity<CommonResponse> deleteAssignment(@PathVariable("id") int id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.NO_CONTENT));
    }

    @Operation(summary = "전체 과제 리스트 조회")
    @GetMapping
    public ResponseEntity<CommonResponse> getAssignmentList() {
        List<AssignmentResponseDto.AssignmentList> assignmentList = assignmentService.getAssignmentList();
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, assignmentList));
    }

}
