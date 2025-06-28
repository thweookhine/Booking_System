package com.codigo.CodeTest.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codigo.CodeTest.entity.UserPackage;

public interface UserPackageRepo extends JpaRepository<UserPackage, Long> {

	List<UserPackage>  findByUserDataId(Long userId);
	
    @Query("""
        SELECT p FROM UserPackage p
        WHERE p.user.id = :userId
          AND p.country = :country
          AND p.expiredAt >= :currentDate
          AND p.remainingCredits > 0
        ORDER BY p.expiredAt ASC
        LIMIT 1
        """)
    Optional<UserPackage> findAvailablePackageForUser(Long userId, String country, LocalDate currentDate);
}
