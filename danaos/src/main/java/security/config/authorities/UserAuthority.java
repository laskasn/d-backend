package security.config.authorities;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 3482826390579909468L;
	
	private static String AUTHORITY_NAME = "ROLE_USER";

	@Override
	public String getAuthority() {
		return AUTHORITY_NAME;
	} 

}
