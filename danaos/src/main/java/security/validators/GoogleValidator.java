package security.validators;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import exceptions.NonValidTokenException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;


@Component
public class GoogleValidator implements ExternalValidatorI {


	private static final List<String> clientIDs = Arrays.asList(
			"536713408045-clb2dpnvl55t0qvtshman8ssku07eh7v.apps.googleusercontent.com"
			);
		
	private static final JacksonFactory jacksonFactory = new JacksonFactory();
	private static final HttpTransport transport = new NetHttpTransport();
	
	private GoogleIdTokenVerifier verifier;
	
	public GoogleValidator() {
		
		verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
			    .setAudience(clientIDs)
			    .build();
		
	}

	@Override
	public boolean isValid(String token) {
		
		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(token);
		}
		catch(GeneralSecurityException ex) {
			return false;
		}
		catch(IOException ex) {
			return false;
		}
		catch(IllegalArgumentException ex) {
			return false;
		}
		
		if(idToken == null) {
			return false;
		}

		Payload payload = idToken.getPayload();
		
		
		String name = (String)payload.get("name");
		String email = payload.getEmail();
		
		System.out.println("Google external user: "+name +" with email: "+email +" was just authenticated!");
		
		return true;
	}
	
	
	
	
	
	
	
}
