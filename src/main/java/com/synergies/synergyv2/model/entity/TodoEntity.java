package com.synergies.synergyv2.model.entity;

import com.synergies.synergyv2.model.dto.TodoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TODO")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private UUID refUserId;
    private String content;
    private int isCheck;
    @CreatedDate
    @Column(updatable = false)
    private LocalDate regDate;
    private String endDate;

    public TodoDto toTodoDto(){
        return TodoDto.builder()
                .id(id)
                .refUserId(refUserId)
                .isCheck(isCheck)
                .content(content)
                .regDate(regDate)
                .endDate(endDate)
                .build();
    }

    public void updateTodo(String content, String endDate){
        this.content = content;
        this.endDate = endDate;
    }

    public void updateCheckTodo(int isCheck){
        this.isCheck = isCheck;
    }

}
