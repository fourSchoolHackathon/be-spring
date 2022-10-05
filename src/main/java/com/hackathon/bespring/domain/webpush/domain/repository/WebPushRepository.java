package com.hackathon.bespring.domain.webpush.domain.repository;

import com.hackathon.bespring.domain.webpush.domain.WebPush;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebPushRepository extends JpaRepository<WebPush, String> {

    Optional<WebPush> findByUserId(long userId);

}
