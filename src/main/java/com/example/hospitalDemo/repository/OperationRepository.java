package com.example.hospitalDemo.repository;

import com.example.hospitalDemo.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
