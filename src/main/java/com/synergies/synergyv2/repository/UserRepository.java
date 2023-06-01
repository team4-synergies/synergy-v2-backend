package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.model.entity.UserEntity;
import com.synergies.synergyv2.repository.mapping.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserMapping> findAllProjectedBy();
}
