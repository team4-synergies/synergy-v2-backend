package com.synergies.synergyv2.service;


import com.synergies.synergyv2.model.dto.NotificationDto;
import com.synergies.synergyv2.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void createNotification(NotificationDto notificationDto) {
        notificationRepository.save(notificationDto.toNotificationEntity());
    }

    ;

    public List<NotificationDto> getAllNotification() {
        List<NotificationDto> list = notificationRepository.findAll().stream().map(i -> i.toNotificationDto()).collect(Collectors.toList());
        return list;
    }

    ;

    public List<NotificationDto> getAllNotificationByCategory(String category) {
        List<NotificationDto> list = notificationRepository.findByCategory(category).stream().map(i -> i.toNotificationDto()).collect(Collectors.toList());
        return list;
    }

    ;

    public void deleteById(int id) {
        notificationRepository.deleteById(id);
    }

    public NotificationDto getNotificationById(int id) { return notificationRepository.findById(id).get().toNotificationDto(); };
}
