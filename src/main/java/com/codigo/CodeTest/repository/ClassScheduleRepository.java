package com.codigo.CodeTest.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codigo.CodeTest.entity.ClassSchedule;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {

	List<ClassSchedule> findByCountry(String country);

    @Query("SELECT cs FROM ClassSchedule cs WHERE cs.endTime < :now")
    List<ClassSchedule> findAllEndedClasses(@Param("now") LocalDateTime now);
}
