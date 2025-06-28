package com.codigo.CodeTest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codigo.CodeTest.entity.PackageData;

public interface PackageRepository extends JpaRepository<PackageData, Long>{

	@Query(value = "select * from package_data where is_available = true", nativeQuery = true)
	List<PackageData> findAllAvailablePackages();
	
	@Query(value = """
		    SELECT * FROM package_data where is_available = true
		    AND country = :country)
		  """, nativeQuery = true)
	List<PackageData> findAllAvailablePackagesByCountry(@Param("country") String country);


}
