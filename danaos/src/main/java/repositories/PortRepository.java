package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entities.Port;

@Repository("portRepository")
public interface PortRepository extends JpaRepository<Port, Long> {

	Port findByName(String name);
	
}

