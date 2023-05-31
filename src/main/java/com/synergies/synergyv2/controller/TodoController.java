package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.common.TodoCode;
import com.synergies.synergyv2.common.response.CommonResponse;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.common.response.exception.DefaultException;
import com.synergies.synergyv2.model.dto.TodoDto;
import com.synergies.synergyv2.model.entity.TodoEntity;
import com.synergies.synergyv2.repository.TodoRepository;
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
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;
    private final TodoRepository todoRepository;
    @Operation(summary = "투두 저장")
    @PostMapping
    @Transactional
    public ResponseEntity<CommonResponse> TodoAdd(@RequestBody TodoDto todoDto) throws DefaultException{
        log.info("TodoAdd");
        todoService.createTodo(todoDto);
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.OK, "저장 성공")));
    }

    @Operation(summary = "투두 조회")
    @GetMapping
    public ResponseEntity<CommonResponse> TodoList() {
        log.info("TodoList");

        try {
            List<TodoDto> list = todoService.getAllTodo();
            return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.OK, list)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body((CommonResponse.toErrorResponse(CommonCode.INTERNAL_SERVER_ERROR)));
        }
    }

    @Operation(summary = "id로 투두 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> TodoDelete(@PathVariable("id") int id) throws DefaultException {
        log.info("TodoDelete");

        todoService.deleteById(id);
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.OK)));
    }

    @Operation(summary = "id로 투두 수정")
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> TodoUpdate(@PathVariable("id") int id, @RequestBody TodoDto todoDto) {
        log.info("TodoUpdate");
        TodoDto todo = todoService.getTodoById(id);
        TodoEntity oldEntity = todo.toTodoEntity();

        try {
            oldEntity.updateTodo(todoDto.getContent(), todoDto.getEndDate());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body((CommonResponse.toErrorResponse(CommonCode.INTERNAL_SERVER_ERROR)));
        }

        todoRepository.save(oldEntity);
        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.OK)));
    }

    @Operation(summary = "id로 투두 is_check 수정")
    @PutMapping("/check/{id}")
    public ResponseEntity<CommonResponse> TodoIsCheckUpdate(@PathVariable("id") int id) {
        log.info("TodoIsCheckUpdate");
        TodoDto todo = todoService.getTodoById(id);
        TodoEntity oldEntity = todo.toTodoEntity();

        try {
            if (TodoCode.N.getInteger() == todo.getIsCheck())
                oldEntity.updateCheckTodo(TodoCode.Y.getInteger());
            else
                oldEntity.updateCheckTodo(TodoCode.N.getInteger());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body((CommonResponse.toErrorResponse(CommonCode.INTERNAL_SERVER_ERROR)));
        }

        todoRepository.save(oldEntity);

        return ResponseEntity.ok((CommonResponse.toResponse(CommonCode.OK)));
    }
}
