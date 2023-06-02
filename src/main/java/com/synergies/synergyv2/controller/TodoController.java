package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.model.dto.TodoDto;
import com.synergies.synergyv2.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v2/todo")
public class TodoController {
    private final TodoService todoService;
    @Operation(summary = "투두 저장")
    @PostMapping
    public ResponseEntity<CommonResponse> createTodo(@RequestBody TodoDto todoDto) {
        log.info("TodoAdd");
        todoService.createTodo(todoDto);
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.CREATED)));
    }

    @Operation(summary = "투두 조회")
    @GetMapping
    public ResponseEntity<CommonResponse> getAllTodo() {
        log.info("TodoList");
        List<TodoDto> list = todoService.getAllTodo();
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.OK, list)));

    }

    @Operation(summary = "id로 투두 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteTodo(@PathVariable("id") int id){
        log.info("TodoDelete");
        todoService.deleteTodo(id);
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.OK)));
    }

    @Operation(summary = "id로 투두 수정")
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> updateTodo(@PathVariable("id") int id, @RequestBody TodoDto todoDto) {
        log.info("TodoUpdate");
        todoService.updateTodo(id, todoDto);
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.CREATED)));
    }

    @Operation(summary = "id로 투두 is_check 수정")
    @PutMapping("/check/{id}")
    public ResponseEntity<CommonResponse> updateTodoIsCheck(@PathVariable("id") int id) {
        log.info("TodoIsCheckUpdate");
        todoService.updateTodoIsCheck(id);
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.CREATED)));
    }
}
