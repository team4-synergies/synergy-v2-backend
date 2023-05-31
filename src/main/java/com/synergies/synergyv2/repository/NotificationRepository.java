package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.model.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
    public Page<NotificationEntity> findByCategory(Pageable pageable , String category);
}
