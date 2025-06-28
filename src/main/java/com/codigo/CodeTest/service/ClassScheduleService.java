package com.codigo.CodeTest.service;

import org.springframework.stereotype.Service;

import com.codigo.CodeTest.dto.ClassScheduleDto;

@Service
public interface ClassScheduleService {

	void createSchedule(ClassScheduleDto scheduleDto);

}
