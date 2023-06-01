package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.model.dto.AssignmentRequestDto;
import com.synergies.synergyv2.model.dto.AssignmentResponseDto;
import com.synergies.synergyv2.service.AssignmentService;
import com.synergies.synergyv2.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/assignments")
public class AssignmentAdminController {

    private final AssignmentService assignmentService;
    private final CommentService commentService;

    @Operation(summary = "과제 등록")
    @PostMapping("/admin")
    public ResponseEntity<CommonResponse> createAssignment(@ModelAttribute AssignmentRequestDto.AssignmentRegister assignment,
                                                           @RequestPart(value = "file", required = false) MultipartFile file) {

        if(!assignment.getTitle().isEmpty()) {
            assignmentService.createAssignment(assignment, file);
        }
        // title이 빈값일 때 예외처리 추가
        
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
    @GetMapping("/admin")
    public ResponseEntity<CommonResponse> getAssignmentList() {
        List<AssignmentResponseDto.AssignmentList> assignmentList = assignmentService.getAssignmentList();
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, assignmentList));
    }

    @Operation(summary = "과제 상세 데이터 조회")
    @GetMapping("/{id}/admin")
    public ResponseEntity<CommonResponse> getAssignment(@PathVariable("id") int id) {
        AssignmentResponseDto.AssignmentDetail assignment = assignmentService.getAssignment(id);
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, assignment));
    }

    @Operation(summary = "과제 제출 현황 조회")
    @GetMapping("/submit/{id}/admin")
    public ResponseEntity<CommonResponse> getSubmitList(@PathVariable("id") int id) {
        AssignmentResponseDto.AssignmentSubmitList submitLists = assignmentService.getSubmitList(id);
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, submitLists));
    }

//    @Operation(summary = "오늘 등록한 과제 개수 조회")
//    @GetMapping("/cnt")
//    public ResponseEntity<CommonResponse> getCount() {
//        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, assignmentService.getTodayCount()));
//    }

    @Operation(summary = "학생이 제출한 과제에 대한 코멘트 등록")
    @PostMapping("/comment/{id}/admin")
    public ResponseEntity<CommonResponse> createComment(@PathVariable("id") int id,
                                                        @RequestPart String comment) {
        commentService.createComment(id, comment);
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.CREATED));
    }

    @Operation(summary = "학생이 제출한 과제에 대한 코멘트 삭제")
    @DeleteMapping("/comment/{id}/admin")
    public ResponseEntity<CommonResponse> deleteComment(@PathVariable("id") int id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.NO_CONTENT));
    }

    @Operation(summary = "학생이 제출한 과제에 대한 코멘트 조회")
    @GetMapping("/comment/{id}/admin")
    public ResponseEntity<CommonResponse> getComment(@PathVariable("id") int id) {
        List<AssignmentResponseDto.CommentList> commentList = commentService.getComment(id);
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, commentList));
    }
}
