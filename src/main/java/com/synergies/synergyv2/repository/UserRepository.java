package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.model.entity.UserEntity;
import com.synergies.synergyv2.repository.mapping.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserMapping> findAllProjectedBy();
}
