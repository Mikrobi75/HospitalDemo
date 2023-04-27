package com.example.hospitalDemo.repository;

import com.example.hospitalDemo.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Modifying
    @Query("delete from Operation o where (o.surgeon.id = :id)")
    void deleteOperationBySurgeonId(@Param("id") Long inputId);

    @Modifying
    @Query("delete from Operation o where (o.patient.id = :id)")
    void deleteOperationByPatientId(@Param("id") Long inputId);

}
