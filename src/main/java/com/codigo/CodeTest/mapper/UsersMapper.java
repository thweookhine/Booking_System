package com.codigo.CodeTest.mapper;

import org.springframework.stereotype.Component;

import com.codigo.CodeTest.dto.UserDto;
import com.codigo.CodeTest.entity.UserData;

@Component
public class UsersMapper {

    public UserData toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        
        // Map to UserData
        UserData user = new UserData();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCountry(dto.getCountry());
        
        return user;
    }

    public UserDto toDto(UserData entity) {
        if (entity == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setCountry(entity.getCountry());
        return dto;
    }
}