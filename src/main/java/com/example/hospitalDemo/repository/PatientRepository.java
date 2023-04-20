package com.example.hospitalDemo.repository;

import com.example.hospitalDemo.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
