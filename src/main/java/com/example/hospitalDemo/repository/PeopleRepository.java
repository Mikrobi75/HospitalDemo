package com.example.hospitalDemo.repository;

import com.example.hospitalDemo.domain.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Long> {

    @Query("select p from People p where p.doctor = :doctor")
    List<People> findPeopleByParam(@Param("doctor") Boolean inputParam);

}
