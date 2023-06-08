package com.synergies.synergyv2.entity;


import com.synergies.synergyv2.model.entity.NotificationEntity;
import com.synergies.synergyv2.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class NotificationTest {
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    @Transactional
    public void 공지_데이터_저장_테스트() {
        NotificationEntity notification =
                NotificationEntity.builder()
                        .refUserId("#123")
                        .title("title")
                        .content("content")
                        .category("실습공지")
                        .build();
        try {
            notificationRepository.save(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void 공지_데이터_호출_테스트() {
        try {
            List<NotificationEntity> list = notificationRepository.findAll();
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
