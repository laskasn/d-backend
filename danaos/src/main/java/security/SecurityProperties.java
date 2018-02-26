//package security;
//
//import javax.validation.constraints.NotNull;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.validation.annotation.Validated;
//
//@ConfigurationProperties(prefix = "security")
////@Validated
//public class SecurityProperties {
//
//	
//	@NotNull
//	private String usersQuery;
//	
//	private String rolesQuery;
//	
//	private String userPassword;
//
//	
//
//	public void setUsersQuery(String usersQuery) {
//		this.usersQuery = usersQuery;
//	}
//
//	public void setRolesQuery(String rolesQuery) {
//		this.rolesQuery = rolesQuery;
//	}
//
//	public void setUserPassword(String userPassword) {
//		this.userPassword = userPassword;
//	}
//
//	public String getUsersQuery() {
//		return usersQuery;
//	}
//
//	public String getRolesQuery() {
//		return rolesQuery;
//	}
//
//	public String getUserPassword() {
//		return userPassword;
//	}
//	
//	
//	
//	
//}
//
