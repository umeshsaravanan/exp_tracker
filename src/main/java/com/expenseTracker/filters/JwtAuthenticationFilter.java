package com.expenseTracker.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

//import com.expenseTracker.services.JwtUtils;

@Component
@WebFilter("/*")  
public class JwtAuthenticationFilter implements Filter {
	private static final String[] ALLOWED_PATHS = {"/api/login", "/api/register", "/api/google", "/api/validate"};
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        
//        if (isAllowedPath(httpRequest.getRequestURI())) {
//            chain.doFilter(request, response);  // Skip token validation and move to next filter
//            return;
//        }
//        
//        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
//            chain.doFilter(request, response);
//            return;
//        }
//        
//        String token = getJwtTokenFromCookie(httpRequest);
//        
//        if (token != null && JwtUtils.validateToken(token)) {
//            // If the token is valid, set the authentication in the security context (if you're using Spring Security)
//            // You can set the authentication based on the token data, like the username
//            String username = JwtUtils.getUsernameFromToken(token);
//            // Optionally: Set authentication in Spring Security context
//            // Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
//            // SecurityContextHolder.getContext().setAuthentication(auth);
//            System.out.println("username" + username);
//        } else {
//            // If token is invalid, send a 401 Unauthorized response
//            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
//            return;
//        }

        chain.doFilter(request, response);  // Continue with the request-response chain
    }

    private String getJwtTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    private boolean isAllowedPath(String uri) {
        // Check if the URI matches any of the allowed paths
        for (String path : ALLOWED_PATHS) {
            if (uri.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void destroy() {}
}
