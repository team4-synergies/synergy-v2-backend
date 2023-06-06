package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.model.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {
   List<TodoEntity> findAllByRefUserId(UUID uuid);
}
