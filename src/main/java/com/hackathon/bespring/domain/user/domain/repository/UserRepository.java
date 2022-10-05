package com.hackathon.bespring.domain.user.domain.repository;

import com.hackathon.bespring.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountId(String accountId);

    Boolean existsByAccountId(String accountId);

}
