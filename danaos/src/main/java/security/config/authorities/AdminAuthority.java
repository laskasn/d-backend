package security.config.authorities;

import org.springframework.security.core.GrantedAuthority;

public class AdminAuthority implements GrantedAuthority {
	
	private static final long serialVersionUID = 7620046829757980424L;
	
	private static String AUTHORITY_NAME = "ROLE_ADMIN";

	@Override
	public String getAuthority() {
		return AUTHORITY_NAME;
	} 

}
