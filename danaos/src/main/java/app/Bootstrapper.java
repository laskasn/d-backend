package app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import entities.User;
import org.springframework.stereotype.Component;

import services.UserService;

@Component
public class Bootstrapper {
	
	
	@Value("${users.default.adminname}")
	private String DEFAULT_ADMIN_NAME;
	
	@Value("${users.default.adminpass}")
	private String DEFAULT_ADMIN_PASS;
	
    @Autowired 
    private UserService userService;

    
    @PostConstruct
    public void init(){
    	checkDefaultUser();
    }
    
    
    /**
     * Should have a default user by the name/pass described on the configuration file.
     * If user exists (not first initiation), it leaves it intact.
     */
    private void checkDefaultUser() {
    	User defaultUser = userService.find(DEFAULT_ADMIN_NAME.trim());
    	if(defaultUser==null) {
    		defaultUser = new User(DEFAULT_ADMIN_NAME.trim(), new BCryptPasswordEncoder().encode(DEFAULT_ADMIN_PASS.trim()), "default system user", "ROLE_ADMIN");
    		defaultUser = userService.save(defaultUser);
    	}
    }
    
    
    
}