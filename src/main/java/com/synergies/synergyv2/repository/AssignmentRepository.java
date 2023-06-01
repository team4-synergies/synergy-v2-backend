package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.model.entity.AssignmentEntity;
import com.synergies.synergyv2.repository.mapping.AssignmentMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Integer> {
    List<AssignmentMapping> findAllProjectedBy();
    int countByUpdateDateAfter(LocalDateTime date);
}
