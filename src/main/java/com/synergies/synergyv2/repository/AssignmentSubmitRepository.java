package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.model.entity.AssignmentSubmitEntity;
import com.synergies.synergyv2.repository.mapping.SubmitMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentSubmitRepository extends JpaRepository<AssignmentSubmitEntity, Integer> {
    @Query("SELECT s.id as id, s.updateDate as updateDate, s.user.name as nickname, s.user.id as userId "+
            "FROM AssignmentSubmitEntity s LEFT OUTER JOIN s.user "+
            "WHERE s.assignment.id = :id AND s.user.role='STUDENT' "+
            "ORDER BY s.updateDate DESC")
    List<SubmitMapping> findSubmitStudents(@Param("id") int id);
    AssignmentSubmitEntity findByAssignmentIdAndUserId(int assignmentId, UUID userId);
}
