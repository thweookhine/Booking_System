package com.codigo.CodeTest.mapper;

import org.springframework.stereotype.Component;

import com.codigo.CodeTest.dto.ClassScheduleDto;
import com.codigo.CodeTest.entity.ClassSchedule;

@Component
public class ScheduleMapper {

	 public ClassSchedule toEntity(ClassScheduleDto dto) {
	        if (dto == null) {
	            return null;
	        }
	        
	        // Map to UserData
	        ClassSchedule classSchedule = new ClassSchedule();
	        classSchedule.setTitle(dto.getTitle());
	        classSchedule.setCountry(dto.getCountry());
	        classSchedule.setRequiredCredits(dto.getRequiredCredits());
	        classSchedule.setMaxSlots(dto.getMaxSlots());
	        classSchedule.setStartTime(dto.getStartTime());
	        classSchedule.setEndTime(dto.getEndTime());
	        classSchedule.setEnded(false);
	        
	        return classSchedule;
	    }
}
