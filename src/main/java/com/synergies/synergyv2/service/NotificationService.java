package com.synergies.synergyv2.service;


import com.synergies.synergyv2.common.PageRequestDto;
import com.synergies.synergyv2.common.PageResponseDto;
import com.synergies.synergyv2.common.response.code.CommonCode;
import com.synergies.synergyv2.common.response.exception.DefaultException;
import com.synergies.synergyv2.model.dto.NotificationDto;
import com.synergies.synergyv2.model.entity.NotificationEntity;
import com.synergies.synergyv2.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    @Transactional
    public void createNotification(NotificationDto notificationDto) throws DefaultException {
        notificationRepository.save(notificationDto.toNotificationEntity());
    }

    @Transactional
    public void deleteNotification(int id) {
        try {
            notificationRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new DefaultException(CommonCode.NOT_FOUND);
        }
    }

    @Transactional
    public void updateNotification(int id, NotificationDto notificationDto){
        try {
            NotificationEntity oldEntity = notificationRepository.getById(id);
            oldEntity.updateNotification(notificationDto.getTitle(), notificationDto.getContent(), notificationDto.getCategory());
        } catch (RuntimeException e) {
            throw new DefaultException(CommonCode.NOT_FOUND);
        }
    }


    public PageResponseDto getNotificationPaging(PageRequestDto pageRequestDto) throws DefaultException{
        Pageable pageable = pageRequestDto.getPageable(Sort.by("id").descending());
        Page<NotificationDto> notificationEntityPage;
        if (pageRequestDto.getKeyword() != null) {
            notificationEntityPage = notificationRepository.findByCategory(pageable, pageRequestDto.getKeyword()).map(notificationEntity -> notificationEntity.toNotificationDto());
        } else {
            notificationEntityPage = notificationRepository.findAll(pageable).map(notificationEntity -> notificationEntity.toNotificationDto());
        }
        PageResponseDto pageResponseDtoPage = new PageResponseDto(pageable, notificationEntityPage);
        return pageResponseDtoPage;
    }
}
