package com.synergies.synergyv2.entity;

import com.synergies.synergyv2.model.entity.TodoEntity;
import com.synergies.synergyv2.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class TodoTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    @Transactional
    public void 투두_데이터_저장_테스트() {

        TodoEntity todo =
                TodoEntity.builder()
                        .refUserId("#123")
                        .content("content")
                        .endDate("2021-12-30")
                        .build();
        try {
            todoRepository.save(todo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void 투두_데이터_호출_테스트() {
        try {
            List<TodoEntity> list = todoRepository.findAll();
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void 투두_데이터_삭제_테스트() {
        try {
            todoRepository.deleteById(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void 투두_데이터_수정_테스트() {
        try {
            TodoEntity oldEntity = todoRepository.findById(1).get();
            oldEntity.updateTodo("aaa", "2022-04-13");
            todoRepository.save(oldEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
