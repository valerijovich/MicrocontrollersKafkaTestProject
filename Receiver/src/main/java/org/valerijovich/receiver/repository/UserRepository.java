package org.valerijovich.receiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.valerijovich.receiver.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {}
