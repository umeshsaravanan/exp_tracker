package com.expenseTracker.controllers;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenseTracker.models.User;
import com.expenseTracker.services.JwtUtils;
import com.expenseTracker.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            boolean validToken = JwtUtils.validateToken(jwt);
            return new ResponseEntity<>(validToken ? "Token Valid" : "Invalid Token", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletResponse response) {
        User isValidUser =  userService.authenticateUser(user.getEmail(), user.getPassword());
        
        if(isValidUser != null) {
        	if(isValidUser.getId() <= 0) {
            	return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }
        	generateTokenAndAddToCookie(isValidUser,response);
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response){
    	removeToken(response);
    	return new ResponseEntity<>("logout success", HttpStatus.OK);
    }
    
    @PostMapping("/google")
    public ResponseEntity<Map<String, String>> loginUserByGoogle(@RequestBody Map<String, String> requestBody, HttpServletResponse response) {
        try {
        	String accessToken = requestBody.get("token");

            Map<String, Object> userDetails = userService.getUserInfo(accessToken);
            String email = (String) userDetails.get("email");
            String name = (String) userDetails.get("name");

            User existingUser = userService.checkUserExists(email);

            if (existingUser == null) {
                User newUser = new User();
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setPassword("");
                existingUser = userService.saveUser(newUser);
                if (existingUser == null || existingUser.getId() <= 0) {
                    return new ResponseEntity<>(Map.of("error","Error creating new user"), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            if (existingUser != null && existingUser.getId() > 0) {
            	Map<String, String> responseBody = Map.of(
                        "jwtToken", JwtUtils.generateToken(existingUser.getName(), existingUser.getId()),
                        "userId", String.valueOf(existingUser.getId())
                    );

                    return new ResponseEntity<>(responseBody, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of("error", "User creation or retrieval failed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    private void generateTokenAndAddToCookie(User user, HttpServletResponse response) {
    	String jwt = JwtUtils.generateToken(user.getName(), user.getId());

        Cookie jwtTokenCookie = new Cookie("jwtToken", jwt);
        jwtTokenCookie.setSecure(true); 
        jwtTokenCookie.setPath("/"); 
        jwtTokenCookie.setMaxAge(60 * 60); 

        response.addCookie(jwtTokenCookie);
        
        Cookie userIdCookie = new Cookie("userId", user.getId() + "");
        userIdCookie.setSecure(true); 
        userIdCookie.setPath("/"); 
        userIdCookie.setMaxAge(60 * 60); 

        response.addCookie(userIdCookie);
    }
    
    private void removeToken(HttpServletResponse response) {

        Cookie jwtTokenCookie = new Cookie("jwtToken", "");
        jwtTokenCookie.setSecure(true); 
        jwtTokenCookie.setPath("/"); 
        jwtTokenCookie.setMaxAge(0); 

        response.addCookie(jwtTokenCookie);
        
        Cookie userIdCookie = new Cookie("userId", "");
        userIdCookie.setSecure(true); 
        userIdCookie.setPath("/"); 
        userIdCookie.setMaxAge(0); 

        response.addCookie(userIdCookie);
    }

}
