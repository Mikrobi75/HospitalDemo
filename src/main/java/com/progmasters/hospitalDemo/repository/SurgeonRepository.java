package com.progmasters.hospitalDemo.repository;

import com.progmasters.hospitalDemo.domain.Surgeon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SurgeonRepository extends JpaRepository<Surgeon, Long> {
}
