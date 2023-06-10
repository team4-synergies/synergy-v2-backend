package com.synergies.synergyv2.repository;

import com.synergies.synergyv2.auth.Role;
import com.synergies.synergyv2.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findByRole(Role role);
    Optional<UserEntity> findByKakaoId(String kakaoId);
}
