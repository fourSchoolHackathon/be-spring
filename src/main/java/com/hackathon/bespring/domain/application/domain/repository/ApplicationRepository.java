package com.hackathon.bespring.domain.application.domain.repository;

import com.hackathon.bespring.domain.application.domain.Application;
import com.hackathon.bespring.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByUserOrderByCreatedAtDesc(User user);

}
