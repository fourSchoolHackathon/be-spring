package com.hackathon.bespring.domain.application.domain.repository;

import com.hackathon.bespring.domain.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {}
