package controllers;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import services.token.TokenService;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600 /*,allowedHeaders={"x-auth-token", "x-requested-with", "x-xsrf-token", "X-Auth-Token"} */ )
//@EnableGlobalMethodSecurity
@RequestMapping("app")
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	
	@Autowired
	private TokenService tokenService;
	
	
	
	@RequestMapping("/login")
	public LoginResponse login() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Gson gson = new Gson();
		
		UsernamePasswordAuthenticationToken auth = 
				new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authentication.getAuthorities());
		
		
		String token = tokenService.generateNewToken();
		tokenService.store(token, auth);
		
		
		System.out.println("TokenService stored token: "+token+ " for user: "+authentication.getPrincipal().toString());

		List<String> roles = authentication.getAuthorities().stream().map(authority-> authority.getAuthority()).collect(Collectors.toList());
		System.out.println("STORING INTO SESSION ROLES: "+roles);
		
		LoginResponse response = new LoginResponse();
		response.setToken(token);
		response.setRoles(roles);
		return response;
	}
	
	@RequestMapping("/logout")
	public void logout() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		tokenService.delete(authentication.getCredentials().toString());
	}
	
	
	@RequestMapping("/loggedinuser")
	public Principal user(Principal principal, @AuthenticationPrincipal final UserDetails user) {
		return principal;
	}
	
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		return "pong: "+UUID.randomUUID().toString();
	}
	
	
	class LoginResponse {
		
		private String token;
		private List<String> roles;
		
		
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public List<String> getRoles() {
			return roles;
		}
		public void setRoles(List<String> roles) {
			this.roles = roles;
		}
	}
	
}
