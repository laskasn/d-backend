package security.config;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

public class MyFilter extends GenericFilterBean {

	
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		
	    HttpServletRequest httpRequest = (HttpServletRequest)request;
	    HttpServletResponse httpResponse = (HttpServletResponse)response;

	    String token = httpRequest.getHeader("X-Auth-Token");

	    String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);

//	    try {
//
//
//	        if (token!=null && !token.isEmpty()) {
//	            System.out.println("Trying to authenticate user by X-Auth-Token method. Token: "+ token);
//	            processTokenAuthentication(token);
//	        }
//
//	        System.out.println("AuthenticationFilter is passing request down the filter chain");
//	        addSessionContextToLogging();
//	        chain.doFilter(request, response);
//	        
//	    } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
//	        SecurityContextHolder.clearContext();
//	        logger.error("Internal authentication service exception", internalAuthenticationServiceException);
//	        httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	    } catch (AuthenticationException authenticationException) {
//	        SecurityContextHolder.clearContext();
//	        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
//	    } finally {
//	        MDC.remove(TOKEN_SESSION_KEY);
//	        MDC.remove(USER_SESSION_KEY);
//	    }
	    
	    
	    chain.doFilter(request, response);
	    
	}

}
