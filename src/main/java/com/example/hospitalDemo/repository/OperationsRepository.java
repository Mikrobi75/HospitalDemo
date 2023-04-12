package com.example.hospitalDemo.repository;

import com.example.hospitalDemo.domain.Operations;
import com.example.hospitalDemo.domain.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationsRepository extends JpaRepository<Operations, Long> {
    @Query("select o from Operations o where o.operation = :operation")
    List<Operations> findOperationsByType(@Param("operation") String inputParam);

    @Modifying
    @Query("delete from Operations o where (o.doctorId = :id) or (o.patientId = :id)")
    void deleteOperationById(@Param("id") Long inputId);

    List<Operations> deleteByDoctorId(Long doctorId);
    List<Operations> deleteByPatientId(Long patientId);

}
