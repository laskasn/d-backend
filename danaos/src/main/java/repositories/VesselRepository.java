package repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entities.Vessel;

@Repository("vesselRepository")
public interface VesselRepository extends JpaRepository<Vessel, Long> {

	Vessel findByName(String name);
	
	@Query(value="SELECT CAST(id AS VARCHAR),name FROM vessel", nativeQuery = true)
	List<Object[]> findAllVesselNameIds();
	
}

