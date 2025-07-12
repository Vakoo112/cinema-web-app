package com.example.cinme_service.v1.repository;

import com.example.cinme_service.v1.domain.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HallRepo extends JpaRepository<Hall, Long>, JpaSpecificationExecutor<Hall> {
}
