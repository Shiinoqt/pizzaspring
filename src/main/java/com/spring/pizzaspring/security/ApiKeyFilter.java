package com.spring.pizzaspring.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * Custom security filter that intercepts every HTTP request to verify the
 * presence and validity of API credentials (Key and Secret) in the headers.
 */
@Component
public class ApiKeyFilter extends GenericFilterBean {

    // Custom header names expected from the HTTP request
    private static final String API_KEY_HEADER = "pizzaspring-api-key";
    private static final String API_SECRET_HEADER = "pizzaspring-api-secret";

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Core filter method that executes processing logic for every request.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Cast generic Servlet objects to HTTP-specific request/response objects
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Extract credentials from the HTTP request headers
        String apiKey = httpRequest.getHeader(API_KEY_HEADER);
        String apiSecret = httpRequest.getHeader(API_SECRET_HEADER);

        // If either header is missing, skip authentication processing
        // and pass control down to the next filter in the security chain
        if (apiKey == null || apiSecret == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // 1. Create a temporary, UNAUTHENTICATED token with the received credentials
            ApiToken token = new ApiToken(apiKey, apiSecret, AuthorityUtils.NO_AUTHORITIES);

            // 2. Delegate to the service to validate the token
            ApiToken authenticated = authenticationService.authenticate(token);

            // 3. If no exception is thrown, store the AUTHENTICATED token in Spring Security Context
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            // 4. Continue execution down the filter chain to the requested endpoint
            chain.doFilter(request, response);
        } catch (BadCredentialsException e) {
            // If authentication fails:
            // 1. Clear the security context to ensure no stale authentication remains
            SecurityContextHolder.clearContext();

            // 2. Configure the HTTP response with a 401 Unauthorized status and JSON content type
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

            // 3. Write the exception's error message directly into the response body
            httpResponse.getWriter().write(e.getMessage());
            return; // Break the chain; block the request from proceeding further
        }
    }
}