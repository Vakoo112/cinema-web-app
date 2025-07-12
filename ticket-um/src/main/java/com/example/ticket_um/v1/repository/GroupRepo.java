package com.example.ticket_um.v1.repository;

import com.example.ticket_um.v1.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group, Long> {

}
