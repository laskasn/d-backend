package repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entities.Vessel;
import entities.Visits;


@Repository("visitsRepository")
public interface VisitsRepository extends JpaRepository<Visits, Long> {
	
	@Query(value="SELECT CAST(id AS VARCHAR) as id, CAST(vessel AS VARCHAR) as vessel, CAST(port AS VARCHAR) as port, visittime  FROM visits v where v.vessel=:vesselID", nativeQuery = true)
//	@Query(value="SELECT v FROM visits v where v.vessel=:vesselID", nativeQuery = true)
	List<Visits> listByVessel(@Param("vesselID") UUID vesselID);
	
//	List<Visits> findByVessel(Vessel vessel);
	
}

