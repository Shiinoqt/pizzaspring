package com.spring.pizzaspring.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Custom authentication token implementation.
 * Extends {@link AbstractAuthenticationToken} to integrate natively into Spring Security.
 */
public class ApiToken extends AbstractAuthenticationToken {
    private final String apiKey;
    private final String apiSecret;

    /**
     * Constructor used by the Filter (ApiKeyFilter) prior to verification.
     * Initializes the token in an UNAUTHENTICATED state.
     * * @param apiKey      The API key sent by the client.
     * @param apiSecret   The API secret sent by the client.
     * @param authorities The collection of roles/permissions (initially empty).
     */
    public ApiToken(String apiKey, String apiSecret, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        setAuthenticated(false); // Explicitly flag this token as unauthenticated
    }

    /**
     * Constructor used by the Service (AuthenticationService) after successful verification.
     * Initializes the token in an AUTHENTICATED state and wipes the secret.
     * * @param apiKey      The validated API key.
     * @param authorities The collection of granted permissions (none in this setup).
     */
    public ApiToken(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        this.apiSecret = null; // Wipe credentials from memory for security reasons
        setAuthenticated(true); // Explicitly flag this token as authenticated
    }

    /**
     * Returns the credentials of the token (the Secret).
     * Required by Spring Security's Authentication interface.
     */
    @Override
    public Object getCredentials() {
        return apiSecret;
    }

    /**
     * Returns the identity of the client (the API Key).
     * Required by Spring Security's Authentication interface.
     */
    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}