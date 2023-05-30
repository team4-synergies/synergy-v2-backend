package com.synergies.synergyv2.model.dto;

import com.synergies.synergyv2.model.entity.TodoEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TodoDto {
    private int id;
    private String refUserId;
    private String content;
    private String isCheck;
    private LocalDate regDate;
    private String endDate;
    public TodoEntity toTodoEntity(){
        return TodoEntity.builder()
                .id(id)
                .refUserId(refUserId)
                .isCheck(isCheck)
                .content(content)
                .regDate(regDate)
                .endDate(endDate)
                .build();
    }
}
