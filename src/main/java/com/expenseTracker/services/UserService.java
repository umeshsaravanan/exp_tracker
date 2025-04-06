package com.expenseTracker.services;

import java.util.Map;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.expenseTracker.models.User;
import com.expenseTracker.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    private static final String GOOGLE_TOKEN_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    private String secretKey = "#exp_25";
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Map<String, Object> getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        try {
        	ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    GOOGLE_TOKEN_INFO_URL, 
                    HttpMethod.GET, 
                    new org.springframework.http.HttpEntity<>(headers), 
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            return response.getBody();  
        } catch (Exception e) {
            System.err.println("Error fetching user info: " + e.getMessage());
            return null;  
        }
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user == null)
        	return null;
        
        if (user.getPassword().equals(password))
            return user;

        return new User(); 
    }
    
    public User checkUserExists(String email) {
    	User user = userRepository.findByEmail(email);
    	
    	return user;
    }
}
