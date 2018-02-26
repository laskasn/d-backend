package app;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;






@EnableTransactionManagement
@EntityScan(value = "beans")
@EnableJpaRepositories(basePackages = {"dao", "app", "beans", "repositories"} )
@SpringBootApplication(scanBasePackages={"beans", "app", "dao", "services", "security", "controllers", "services"})
//@EnableConfigurationProperties(SecurityProperties.class)
public class Application extends SpringBootServletInitializer{
	
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

	
	
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
    
    }
    
    

    
    
    /*
    @Bean
    CommandLineRunner init(final UserService userService, final UserRepository userRepository, final RoleRepository roleRepository, final AddressRepository addressRepository) {
      
      return new CommandLineRunner() {

        @Override
        @Transactional
        public void run(String... arg0) throws Exception {
        	
//        	//add some
//        	List<Address> addresses = new ArrayList<Address>();
//        	IntStream.range(0, 1000).forEach(i -> addresses.add(new Address(UUID.randomUUID().toString(),UUID.randomUUID().toString(),UUID.randomUUID().toString(), "11111")));
//        	addressRepository.save(addresses);
        	
        	
//        	Sort sort = new Sort(new Sort.Order(Direction.ASC, "street"));
//        	Pageable pageable = new PageRequest(0, 5, sort);
        	
//        	Pageable pageable = new PageRequest(0, 100);
        	
        	//read some
//        	Page<Address> page = addressRepository.findAll(pageable);
//        	System.out.println("read: "+page.getContent().size());
//        	page.getNumberOfElements()
//        	page.getTotalElements()
        	
        	
        	
//        	final int PAGE_SIZE = 1000;
//        	int pageNum = 0;
//        	int totalPages;
//        	do {
//        		System.out.println("Trying page: "+pageNum);
//        		Pageable pageable = new PageRequest(pageNum, PAGE_SIZE);
////        		Page<Address> addressPage = addressRepository.findAll(pageable);
//        		Page<Address> addressPage = addressRepository.findByZipcode("11111", pageable);
//        		
//        		System.out.println("Read: "+addressPage.getNumberOfElements());
//        		
//        		totalPages = addressPage.getTotalPages();
//        		pageNum++;
//        	}while(pageNum<totalPages);
        	
        	
        	
        }
        
      };

    }
     */
    
    
}