package com.synergies.synergyv2.model.dto;

import com.synergies.synergyv2.model.entity.NotificationEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class NotificationDto {
    private int id;
    private UUID refUserId;
    private String title;
    private String content;
    private String category;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    public NotificationEntity toNotificationEntity(){
        return NotificationEntity.builder()
                .id(id)
                .refUserId(refUserId)
                .title(title)
                .content(content)
                .category(category)
                .build();
    }
}
