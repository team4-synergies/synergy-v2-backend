package com.synergies.synergyv2.model.entity;

import com.synergies.synergyv2.common.BaseTime;
import com.synergies.synergyv2.model.dto.NotificationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "NOTIFICATION")
public class NotificationEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "BINARY(16)")
    private UUID refUserId;
    private String title;
    private String content;
    private String category;

    public NotificationDto toNotificationDto(){
        return NotificationDto.builder()
                .id(id)
                .refUserId(refUserId)
                .title(title)
                .content(content)
                .category(category)
                .regDate(getRegDate())
                .updateDate(getUpdateDate())
                .build();
    }

    public void updateNotification(String title, String content, String category){
        this.title = title;
        this.content = content;
        this.category = category;
    }
}