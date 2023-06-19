package com.progmasters.hospitalDemo.repository;

import com.progmasters.hospitalDemo.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
