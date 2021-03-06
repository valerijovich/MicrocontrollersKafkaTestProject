package org.valerijovich.receiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.valerijovich.receiver.entity.UserEntity;

import java.util.Optional;

// JPA репозиторий для взаимодействия с БД
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Integer userId);
}
