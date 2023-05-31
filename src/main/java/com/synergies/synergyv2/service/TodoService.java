package com.synergies.synergyv2.service;

import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.common.response.exception.DefaultException;
import com.synergies.synergyv2.model.dto.TodoDto;
import com.synergies.synergyv2.model.entity.TodoEntity;
import com.synergies.synergyv2.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    @Transactional
    public void createTodo(TodoDto todoDto) {
        todoRepository.save(todoDto.toTodoEntity());
    }

    public List<TodoDto> getAllTodo() {
        List<TodoDto> list = todoRepository.findAll().stream().map(i -> i.toTodoDto()).collect(Collectors.toList());
        return list;
    }

    @Transactional
    public void deleteById(int id) {
        todoRepository.deleteById(id);
    }


    public TodoDto getTodoById(int id) {
        TodoEntity todo = todoRepository.findById(id)
                .orElseThrow(() -> new DefaultException(CommonCode.NOT_FOUND));
        return todo.toTodoDto();
    }

}
