package com.codigo.CodeTest.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codigo.CodeTest.dto.ClassScheduleDto;
import com.codigo.CodeTest.entity.ClassSchedule;
import com.codigo.CodeTest.mapper.ScheduleMapper;
import com.codigo.CodeTest.repository.ClassScheduleRepository;
import com.codigo.CodeTest.service.ClassScheduleService;

@Service
public class ClassScheduleServiceImpl implements ClassScheduleService{

	@Autowired
	private ClassScheduleRepository scheduleRepo;
	
	private final ScheduleMapper scheduleMapper;
	
	public ClassScheduleServiceImpl(ScheduleMapper mapper) {
		this.scheduleMapper = mapper;
	}
	
	@Override
	public void createSchedule(ClassScheduleDto scheduleDto) {
		ClassSchedule schedule = scheduleMapper.toEntity(scheduleDto);
		scheduleRepo.save(schedule);
	}

}
