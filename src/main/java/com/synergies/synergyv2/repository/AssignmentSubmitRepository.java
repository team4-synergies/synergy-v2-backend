package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.model.entity.AssignmentSubmitEntity;
import com.synergies.synergyv2.repository.mapping.SubmitMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentSubmitRepository extends JpaRepository<AssignmentSubmitEntity, Integer> {
    @Query("SELECT s.id as id, s.updateDate as updateDate, s.user.userNickname as nickname, s.user.id as userId "+
            "FROM AssignmentSubmitEntity s LEFT OUTER JOIN s.user "+
            "WHERE s.assignment.id = :id "+
            "ORDER BY s.updateDate DESC")
    List<SubmitMapping> findSubmitStudents(@Param("id") int id);
}
