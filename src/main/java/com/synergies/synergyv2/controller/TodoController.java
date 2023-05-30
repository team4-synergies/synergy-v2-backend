package com.synergies.synergyv2.controller;

import com.synergies.synergyv2.common.response.CommonResponse;
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

import static com.synergies.synergyv2.common.response.code.CommonCode.NOT_FOUND;
import static com.synergies.synergyv2.common.response.code.CommonCode.OK;

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
    public ResponseEntity<CommonResponse> TodoAdd(@RequestBody TodoDto todoDto) {
        log.info("TodoAdd");
        todoService.createTodo(todoDto);
        return ResponseEntity.ok((CommonResponse.toResponse(OK, "저장 성공")));
    }

    @Operation(summary = "투두 조회")
    @GetMapping
    public ResponseEntity<CommonResponse> TodoList() {
        log.info("TodoList");
        List<TodoDto> list = todoService.getAllTodo();
        return ResponseEntity.ok((CommonResponse.toResponse(OK, list)));
    }

    @Operation(summary = "id로 투두 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> TodoDelete(@PathVariable("id") int id) {
        log.info("TodoDelete");
        try {
            todoService.deleteById(id);
        } catch (Exception e) {
            throw new DefaultException(NOT_FOUND);
        }

        return ResponseEntity.ok((CommonResponse.toResponse(OK)));
    }

    @Operation(summary = "id로 투두 수정")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CommonResponse> TodoUpdate(@PathVariable("id") int id, @RequestBody TodoDto todoDto) {
        log.info("TodoUpdate");
        try {
            TodoDto todo = todoService.getTodoById(id);
            TodoEntity oldEntity = todo.toTodoEntity();
            oldEntity.updateTodo(todoDto.getContent(), todoDto.getEndDate());
            todoRepository.save(oldEntity);
        } catch (Exception e) {
            throw new DefaultException(NOT_FOUND);
        }
        return ResponseEntity.ok((CommonResponse.toResponse(OK)));
    }

    @Operation(summary = "id로 투두 is_check 수정")
    @PutMapping("/check/{id}")
    @Transactional
    public ResponseEntity<CommonResponse> TodoIsCheckUpdate(@PathVariable("id") int id) {
        log.info("TodoIsCheckUpdate");
        try {
            TodoDto todo = todoService.getTodoById(id);
            TodoEntity oldEntity = todo.toTodoEntity();

            if(todo.getIsCheck().equals("false"))
                oldEntity.updateCheckTodo("true");
            else
                oldEntity.updateCheckTodo("false");

            todoRepository.save(oldEntity);
        } catch (Exception e) {
            throw new DefaultException(NOT_FOUND);
        }
        return ResponseEntity.ok((CommonResponse.toResponse(OK)));
    }
}
