package com.synergies.synergyv2.service;

import com.synergies.synergyv2.common.TodoCode;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.common.response.exception.DefaultException;
import com.synergies.synergyv2.model.dto.TodoDto;
import com.synergies.synergyv2.model.entity.TodoEntity;
import com.synergies.synergyv2.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    @Transactional
    public void createTodo(TodoDto todoDto) throws DefaultException{
        todoRepository.save(todoDto.toTodoEntity());
    }

    public List<TodoDto> getAllTodoFindByUuid(UUID uuid) throws DefaultException{
        List<TodoDto> list = todoRepository.findAllByRefUserId(uuid).stream().map(i -> i.toTodoDto()).collect(Collectors.toList());
        List<TodoEntity> list2 = todoRepository.findAllByRefUserId(uuid);
        System.out.println(list2);
        return list;
    }

    public TodoDto getTodoById(int id) throws DefaultException{
        return todoRepository.findById(id).get().toTodoDto();
    }
    @Transactional
    public void deleteTodo(int id){
        try {
            todoRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new DefaultException(CommonCode.NOT_FOUND);
        }
    }

    @Transactional
    public void updateTodo(int id,TodoDto todoDto){
        try {
            TodoEntity oldEntity = todoRepository.getById(id);
            oldEntity.updateTodo(todoDto.getContent(), todoDto.getEndDate());
        } catch (RuntimeException e) {
            throw new DefaultException(CommonCode.NOT_FOUND);
        }
    }

    @Transactional
    public void updateTodoIsCheck(int id){
        TodoEntity oldEntity = todoRepository.getById(id);
        TodoDto todoDto = oldEntity.toTodoDto();
        try {
            if (TodoCode.N.getInteger() == todoDto.getIsCheck())
                oldEntity.updateCheckTodo(TodoCode.Y.getInteger());
            else
                oldEntity.updateCheckTodo(TodoCode.N.getInteger());
        } catch (RuntimeException e) {
            throw new DefaultException(CommonCode.NOT_FOUND);
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteCheckTodo() {
        log.info("Todo Check Delete Scheduling");
        todoRepository.findAll().stream().map(i -> i.toTodoDto()).forEach((dto) -> {
            if (todoRepository.findById(dto.getId()).get().toTodoDto().getIsCheck() == 1)
                todoRepository.deleteById(dto.getId());
        });
    }
}
