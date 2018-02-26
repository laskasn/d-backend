package repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import beans.Country;


@Repository("countryRepository")
public interface CountryRepository extends JpaRepository<Country, Long> {
	 
	Country findById(UUID id);
	Country findByName(String name);
	
}

