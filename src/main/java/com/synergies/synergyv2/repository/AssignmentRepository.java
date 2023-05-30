package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.model.entity.AssignmentEntity;
import com.synergies.synergyv2.repository.mapping.AssignmentMapping;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@EntityScan
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Integer> {

    List<AssignmentMapping> findAllProjectedBy();
}
