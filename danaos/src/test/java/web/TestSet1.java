package web;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.Rule;

import controllers.MainController;
import repositories.CountryRepository;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import beans.Country;




@ComponentScan({"app","beans", "repositories"})
@AutoConfigureTestDatabase(replace=Replace.NONE)
@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT, classes= {TestSet1.class})
public class TestSet1 {


	@Autowired
	private CountryRepository countryRepository;

	
	@Test
	public void checkCountries() {
		List<Country> countries = countryRepository.findAll();
		countries.stream().forEach(
			country->{
				System.out.println(new Gson().toJson(country));
			}
		);
		assertNotNull(countryRepository);
		assertNotNull(countryRepository.count()>0);
	}
	
	
//	@Test
	public void testImportAllImages() {
		
		File flagsFolder = new File("/home/satyr/Desktop/all_flags"); 
		List<File> flags = Arrays.asList(flagsFolder.listFiles());
		
		
		flags.parallelStream().forEach(flag -> {
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
		
		assertNotNull(countryRepository);
		assertEquals(countryRepository.count(), 254);
	}
	
	
    
    
    
    
    
}
