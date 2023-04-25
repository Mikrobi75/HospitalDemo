package com.example.hospitalDemo.repository;

import com.example.hospitalDemo.domain.Surgeon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurgeonRepository extends JpaRepository<Surgeon, Long> {
}
