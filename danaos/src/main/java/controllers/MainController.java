package controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import entities.Country;
import entities.Port;
import entities.Vessel;
import entities.Visits;
import repositories.CountryRepository;
import repositories.PortRepository;
import repositories.VesselRepository;
import repositories.VisitsRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@CrossOrigin//(origins = "http://localhost:4200")
public class MainController {

	private Gson gson = new Gson();
	
	private CountryRepository countryRepository;
	private VesselRepository vesselRepository;
	private PortRepository portRepository;
	private VisitsRepository visitsRepository;
	

	@Autowired
	public void setCountryRepository(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}
	
	@Autowired
	public void setVesselRepository(VesselRepository vesselRepository) {
		this.vesselRepository = vesselRepository;
	}
	
	@Autowired
	public void setPortRepository(PortRepository portRepository) {
		this.portRepository = portRepository;
	}
	
	@Autowired
	public void setVisitsRepository(VisitsRepository visitsRepository) {
		this.visitsRepository = visitsRepository;
	}
	
	
	@RequestMapping("/country/list")
	@ResponseBody
    public List<Country> listCountries() {
		List<Country> countries = countryRepository.findAll();
		//remove some objects to avoid cyclic json serialization issues
		countries.parallelStream().forEach(country -> {
			country.setVessels(new HashSet<Vessel>());
		});
		return countries;
    }
	
	@RequestMapping(method = RequestMethod.GET, value = { "/country/get" }, produces="application/json")
	public @ResponseBody ResponseEntity<Object> getCountry(@RequestParam("id") String id) {
		try{
			Country country = countryRepository.findById(UUID.fromString(id));
			if(country!=null)
				return ResponseEntity.status(HttpStatus.OK).body(country);
		}
		catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete the country!");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Did not find any country with id: "+id);
	}
	
	
	@PostMapping("/country/update")
	public ResponseEntity<Object> saveCountryFlag(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("icon") MultipartFile file) {
		try{
			Country country = new Country();
			country.setId(UUID.fromString(id));
			country.setName(name);
			country.setIcon(file.getBytes());
			country = countryRepository.save(country);
		}
		catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not save the country flag!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Updated country flag of country id: "+id);
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = { "/country/add" }, consumes = "application/json", produces="application/json")
	public @ResponseBody ResponseEntity<Object> createCountry(@RequestBody Country country) {
		try{
			country = countryRepository.save(country);
		}
		catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create the country!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Created country with id: "+country.getId());
	}
	

	@RequestMapping(method = RequestMethod.POST, value = { "/country/delete" }, consumes = "application/json", produces="application/json")
	public @ResponseBody ResponseEntity<Object> deleteCountry(@RequestBody Country country) {
		try{
			countryRepository.delete(country);
		}
		catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete the country!");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Deleted country with id: "+country.getId().toString());
	}
	
	
	
	
	@RequestMapping("/vessel/list")
	@ResponseBody
    public List<Vessel> listVessels() {
		List<Vessel> vessels = vesselRepository.findAll();
		//remove some objects to avoid cyclic json serialization issues
		vessels.parallelStream().forEach(vessel -> {
			if(vessel!=null && vessel.getCountry()!=null)
				vessel.getCountry().setVessels(new HashSet<Vessel>());
		});
		return vessels;
    }
	
	@RequestMapping("/vessel/listidname")
	@ResponseBody
    public List<Object[]> listVesselNameIDs() {
		return (List<Object[]>)vesselRepository.findAllVesselNameIds();
    }	
	
	

    @PostMapping("/vessel/update")
	public ResponseEntity<Object> saveVesselImage(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("image") MultipartFile image, @RequestParam("countryid") String countryid) {
		try{
			Vessel vessel = new Vessel();
			vessel.setId(UUID.fromString(id));
			vessel.setName(name);
			vessel.setImage(image.getBytes());
			
			Country country = new Country();
			country.setId(UUID.fromString(countryid));
			
			vessel.setCountry(country);
			
			vessel = vesselRepository.save(vessel);
			
		}
		catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not save vessel image!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Updated vessel image of vessel id: "+id);
	}
	
	

    @RequestMapping(method = RequestMethod.POST, value = { "/vessel/add" }, consumes = "application/json", produces="application/json")
	public @ResponseBody ResponseEntity<Object> createVessel(@RequestBody Vessel vessel) {
		try{
			vessel = vesselRepository.save(vessel);
		}
		catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create the vessel on db!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Created vessel with id: "+vessel.getId());
	}
	

    @RequestMapping(method = RequestMethod.POST, value = { "/vessel/delete" }, consumes = "application/json", produces="application/json")
	public @ResponseBody ResponseEntity<Object> deleteVessel(@RequestBody Vessel vessel) {
		try{
			vesselRepository.delete(vessel);
		}
		catch(Exception ex){
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete the vessel!");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Deleted vessel with id: "+vessel.getId().toString());
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping("/port/list")
	@ResponseBody
    public List<Port> listPorts() {
		return portRepository.findAll();
    }
	
	

	@RequestMapping(method = RequestMethod.POST, value = { "/port/add" }, consumes = "application/json", produces="application/json")
	public @ResponseBody ResponseEntity<Object> createPort(@RequestBody Port port) {
		try{
			port = portRepository.save(port);
		}
		catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not create the port on db!");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Created port with id: "+port.getId());
	}
	

	@RequestMapping(method = RequestMethod.POST, value = { "/port/delete" }, consumes = "application/json", produces="application/json")
	public @ResponseBody ResponseEntity<Object> deletePort(@RequestBody Port port) {
		try{
			portRepository.delete(port);
		}
		catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete the port!");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Deleted port with id: "+port.getId().toString());
	}
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = { "/visit/list" }, produces="application/json")
	@ResponseBody
    public List<Visits> listVisits(@RequestParam("vesselID") String vesselID) {
		List<Visits> visits = visitsRepository.listByVessel(UUID.fromString(vesselID));
		//remove some objects to avoid cyclic json serialization issues
		visits.parallelStream().forEach(visit -> {
			if(visit.getVessel()!=null) 
				visit.getVessel().setCountry(null);
		});
		return visits;
    }
	

	
	
	@RequestMapping(method = RequestMethod.POST, value = { "/visit/add" },consumes = "application/json", produces="plain/text")
    public @ResponseBody ResponseEntity<Object> addVisit(
    		//@RequestParam("vesselID") String vesselID, @RequestParam("portID") String portID, @RequestParam("visittime") Long visittime
    		@RequestBody Visits visit
    		) {
		
		try{
			visitsRepository.save(visit);
		}
		catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not add the visit!");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Added visit with id: "+visit.getId().toString());
    }
	
	
	@RequestMapping(method = RequestMethod.POST, value = { "/visit/delete" }, consumes = "application/json", produces="application/json")
	public @ResponseBody ResponseEntity<Object> deleteVisit(@RequestBody Visits visit) {
		try{
			visitsRepository.delete(visit);
		}
		catch(Exception ex){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete the port!");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Deleted visit with id: "+visit.getId().toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	@RequestMapping("/ping")
    public String ping() {
    	return "pong: "+UUID.randomUUID().toString();
    }
	
	
	@RequestMapping("/now")
    public Date now() {
    	return new Date();
    }
	
	
    
    
}
