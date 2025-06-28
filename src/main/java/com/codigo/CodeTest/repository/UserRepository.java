package com.codigo.CodeTest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codigo.CodeTest.entity.UserData;

public interface UserRepository extends JpaRepository<UserData, Long> {
	Optional<UserData> findByEmail(String email);
    Optional<UserData> findByVerificationToken(String token);
    Optional<UserData> findByResetToken(String token);
}
