package com.codigo.CodeTest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codigo.CodeTest.dto.ClassScheduleDto;
import com.codigo.CodeTest.entity.ClassSchedule;

@Service
public interface ClassScheduleService {

	void createSchedule(ClassScheduleDto scheduleDto);

	List<ClassSchedule> getSchedules(String country);

}
