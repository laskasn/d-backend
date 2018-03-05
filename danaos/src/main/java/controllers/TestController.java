package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import entities.Country;
import entities.Vessel;
import repositories.CountryRepository;
import repositories.PortRepository;
import repositories.VesselRepository;
import repositories.VisitsRepository;

@RestController
@CrossOrigin // (origins = "http://localhost:4200")
public class TestController {

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


	@RequestMapping(value = "/test/populate/countries", method = RequestMethod.GET)
	public void populateCountries(HttpServletResponse response) throws Exception {

		OutputStream os = response.getOutputStream();

		String folderName = UUID.randomUUID().toString();
		
		os.write("Creating folder to process files... \n".getBytes());
		response.flushBuffer();
		
		try {
			File folder = new File("/tmp/"+folderName);
			folder.mkdir();
		}
		catch(Exception ex) {
			os.write("Failed to create folder! \n".getBytes());
			response.flushBuffer();
		}
		
		String zippedFilePath = "/tmp/"+folderName+"/flags.zip";
		
		File zipped = new File(zippedFilePath);
		
		os.write("Downloading file from internet... \n".getBytes());
		response.flushBuffer();
	
		
		URL website = new URL("http://www.freeflagicons.com/download/?all=1");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(zipped);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.flush();
		fos.close();
		
		os.write("Finished downloading! \n".getBytes());
		response.flushBuffer();
		
		os.write("Unzipping: \n".getBytes());
		response.flushBuffer();
		
		String flagsFolderPath = "/tmp/"+folderName+"/flags";
		
		Process p = Runtime.getRuntime().exec("unzip "+zippedFilePath+" -d "+flagsFolderPath);
		
		System.out.println("Unzipped into: "+flagsFolderPath);
		
		os.write("Finished unzipping! \n".getBytes());
		response.flushBuffer();
		
		
		os.write("Removing all existing countries from DB... \n".getBytes());
		response.flushBuffer();
		countryRepository.deleteAll();
		os.write("Removed! \n".getBytes());
		response.flushBuffer();
		
		os.write("Inserting into db... \n".getBytes());
		response.flushBuffer();
		insertIntoDB(flagsFolderPath);
		
		os.write("COMPLETED SUCCESSFULLY !!!".getBytes());
		response.flushBuffer();
		
	}
	
	
	
	
	public void insertIntoDB(String flagsFolderPath) {
		
		File flagsFolder = new File(flagsFolderPath); 
		List<File> flags = Arrays.asList(flagsFolder.listFiles());
		
		
		flags.stream().forEach(flag -> {
			String splits[] = flag.getName().replace(".gif", "").split("_");
			//capitalize first letter 
			String flagname = "";
			for(int i=0;i<splits.length;i++)
				flagname += (splits[i].substring(0, 1).toUpperCase() + splits[i].substring(1) + " ");
			flagname = flagname.trim();
			//now flagname has a human readable name for the flag.
			
			byte [] fileBytes = null;
			try {
				fileBytes = Files.readAllBytes(flag.toPath());
			}catch (IOException e) {/* just leave the icon blank */}
			
			Country country = new Country();
			country.setName(flagname);
			country.setIcon(fileBytes);
			
			countryRepository.save(country);
			
		});
		
	}
	

}



