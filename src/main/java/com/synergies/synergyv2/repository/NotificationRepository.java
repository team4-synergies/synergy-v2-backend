package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.model.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
    public List<NotificationEntity> findByCategory(String category);
}
