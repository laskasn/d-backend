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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import security.config.authorities.UserAuthority;
import services.token.TokenService;



@Component
public class SessionTokenAuthenticationProvider implements AuthenticationProvider {

    private TokenService tokenService;

	@Autowired
    public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
    }
    

@Override
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	
	String type = authentication.getPrincipal().toString();
	String token = authentication.getCredentials().toString();
	
	if("%%%custom-token%%%".equals(type)) {
		Authentication storedAuthentication = tokenService.retrieve(token);
		
		if(storedAuthentication != null) {
			System.out.println("Authenticated by an existing token "+token);
			
			List<String> roles = storedAuthentication.getAuthorities().stream().map(authority-> authority.getAuthority()).collect(Collectors.toList());
			System.out.println("FROM SESSION ROLES: "+roles);
			
			return storedAuthentication;
//			return new UsernamePasswordAuthenticationToken( authentication.getPrincipal(), authentication.getCredentials(), authentication.getAuthorities());
		}
	}
	return null;
}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
