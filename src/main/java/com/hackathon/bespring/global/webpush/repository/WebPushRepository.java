package com.hackathon.bespring.global.webpush.repository;

import com.hackathon.bespring.global.webpush.WebPush;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebPushRepository extends JpaRepository<WebPush, String> {

    Optional<WebPush> findByUserId(long userId);

}
