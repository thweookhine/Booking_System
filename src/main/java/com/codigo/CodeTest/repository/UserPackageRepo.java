package com.codigo.CodeTest.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codigo.CodeTest.entity.UserPackage;

@Repository
public interface UserPackageRepo extends JpaRepository<UserPackage, Long> {

	List<UserPackage>  findByUserDataId(Long userId);
	
    @Query("""
        SELECT p FROM UserPackage p
        WHERE p.userData.id = :userId
          AND p.packageData.country = :country
          AND p.expiryDate >= :currentDate
          AND p.remainingCredits > 0
        ORDER BY p.expiryDate ASC
        LIMIT 1
        """)
    Optional<UserPackage> findAvailablePackageForUser(Long userId, String country, LocalDateTime currentDateTime);
    
    @Query("""
            SELECT up FROM UserPackage up
            WHERE up.userData.id = :userId
              AND up.packageData.country = :country
              AND up.expiryDate > :now
            ORDER BY up.expiryDate DESC
        """)
        Optional<UserPackage> getMostRecentPackageForUser(
            @Param("userId") Long userId,
            @Param("country") String country,
            @Param("now") LocalDateTime now
        );
}
