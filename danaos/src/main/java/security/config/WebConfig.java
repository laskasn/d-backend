package security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import services.AppUserDetailsService;


//@EnableOAuth2Sso
//@Configurable
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AppUserDetailsService appUserDetailsService;

	@Autowired
	private ExternalAuthenticationProvider externalAuthProvider;
	
	@Autowired
	private SessionTokenAuthenticationProvider sessionTokenAuthProvider;
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http
		.cors().and()
	    .csrf().disable()
		
			.authorizeRequests()

			.antMatchers("/app/login","/app/logout").permitAll()
			

			.antMatchers("/users/list",
					"/port/delete","/port/add",
		    		"/vessel/delete","/vessel/add","/vessel/update", "/vessel/list",
		    		"/country/delete","/country/add","/country/update","/country/get","/country/list").access("hasRole('ROLE_ADMIN')")//.hasAnyAuthority("ROLE_ADMIN")
			
		    .antMatchers("/visit/delete","/visit/add","/visit/list").access("hasRole('ROLE_USER')")
			
		    .antMatchers("/vessel/listidname", "/port/list").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
		    
		    
		    .and()
				.httpBasic();
		
//		http.addFilterBefore(new MyFilter(), BasicAuthenticationFilter.class);
		
	}
	
	
//	@Override
//	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		DaoAuthenticationProvider internalAuthProvider = new DaoAuthenticationProvider();
		internalAuthProvider.setUserDetailsService(appUserDetailsService);
		internalAuthProvider.setPasswordEncoder(new BCryptPasswordEncoder());

		auth.authenticationProvider(sessionTokenAuthProvider);

		auth.authenticationProvider(externalAuthProvider);

		auth.authenticationProvider(internalAuthProvider);
		
	}
	
//	@Bean
//	HeaderHttpSessionStrategy sessionStrategy() {
//		return new HeaderHttpSessionStrategy();
//	}
	
	@Bean
	public CommonsMultipartResolver filterMultipartResolver(){
	    return new CommonsMultipartResolver();
	}
	
	

	// this configuration allow the client app to access the this api 
	// all the domain that consume this api must be included in the allowed o'rings 
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//	    return new WebMvcConfigurerAdapter() {
//	        @Override
//	        public void addCorsMappings(CorsRegistry registry) {
//	            registry.addMapping("/**").allowedOrigins("http://localhost:4200","http://localhost", "*");
//	          
//	        }
//	    };
//	}
	
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		super.configure(web);
//	}

	
	public static void main(String []args) {
		System.out.println(new BCryptPasswordEncoder().encode("auserpass"));
	}
	
	
}
