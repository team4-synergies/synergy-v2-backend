package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.model.dto.AssignmentRequestDto;
import com.synergies.synergyv2.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

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

}
