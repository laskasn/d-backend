package services.token;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Component
@Scope("singleton")
public class TokenService {
	
	private static Integer EXPIRE_AFTER_ACCESS_SEC = 120000;  
	
	Cache<Object, Object> cache;
	
	
	public TokenService() {
		
		cache = CacheBuilder.newBuilder()
		      .expireAfterAccess(EXPIRE_AFTER_ACCESS_SEC, TimeUnit.SECONDS)
		      .build();
		
	}
	

	public String generateNewToken() {
		return UUID.randomUUID().toString();
	}

	public void store(String token, Authentication authentication) {
		cache.put(token, authentication);
	}

	public boolean contains(String token) {
		return cache.asMap().keySet().contains(token);
	}

	public Authentication retrieve(String token) {
		return (Authentication) cache.getIfPresent(token);
	}
  
	public void delete(String token) {
		cache.invalidate(token);
	}
	
    
    
    
    
    
}
