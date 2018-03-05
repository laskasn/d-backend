package security.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import security.config.authorities.UserAuthority;
import security.validators.GoogleValidator;
import services.token.TokenService;

@Component
public class ExternalAuthenticationProvider implements AuthenticationProvider {	
	
	
	GoogleValidator googleValidator;
	
	
	@Autowired
    public void setGoogleValidator(GoogleValidator googleValidator) {
		this.googleValidator = googleValidator;
    }
	
	
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
  
        String nameOrType = authentication.getName();
        String passwordOrToken = authentication.getCredentials().toString();
        
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new UserAuthority()); // let's say for now (the demo) that external users have "USER" permissions

        
        UsernamePasswordAuthenticationToken userPassAuthToken = 
        		new UsernamePasswordAuthenticationToken( nameOrType, passwordOrToken, authorities);
        
        
        if("%%%external-google-provider%%%".equals(nameOrType) && googleValidator.isValid(passwordOrToken)) {
        	
        	List<String> roles = userPassAuthToken.getAuthorities().stream().map(authority-> authority.getAuthority()).collect(Collectors.toList());
    		System.out.println("FROM EXTERNAL ROLES: "+roles);
        	
        	return userPassAuthToken;
        }
        else{
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    
    
    
}
