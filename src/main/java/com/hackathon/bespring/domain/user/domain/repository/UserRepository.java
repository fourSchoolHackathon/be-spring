package com.hackathon.bespring.domain.user.domain.repository;

import com.hackathon.bespring.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountId(String accountId);

    Boolean existsByAccountId(String accountId);

    @Query(value = "SELECT *, (6371 * acos(cos(radians(:x)) * cos(radians(u.latitude)) * cos(radians(u.longitude) - radians(:y)) + sin(radians(:x)) * sin(radians(u.latitude)))) AS distance FROM tbl_user AS u HAVING distance <= 10 ORDER BY distance;", nativeQuery = true)
    List<User> findAllUser(@Param(value = "x") BigDecimal x, @Param(value = "y") BigDecimal y);

}
