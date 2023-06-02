package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.model.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
    public Page<NotificationEntity> findByCategory(Pageable pageable , String category);
}
