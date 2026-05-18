package com.spring.pizzaspring.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

/**
 * Service dedicated to handling authentication business logic.
 * Compares incoming token credentials against the server-side configuration.
 */
@Service
public class AuthenticationService {

    // Inject the valid API key defined in application configuration files
    @Value("${api.key}")
    private String validApiKey;

    // Inject the valid API secret defined in application configuration files
    @Value("${api.secret}")
    private String validApiSecret;

    /**
     * Accepts an unauthenticated token, extracts its data, and validates it against system properties.
     * * @param token The unauthenticated ApiToken containing credentials sent by the client.
     * @return A new authenticated ApiToken instance if validation succeeds.
     * @throws BadCredentialsException If credentials are null, incorrect, or mismatched.
     */
    public ApiToken authenticate(ApiToken token) {
        // Extract credentials provided by the client through the token
        String apiKey = (String) token.getPrincipal();
        String apiSecret = (String) token.getCredentials();

        // Cross-verification check:
        // 1. Ensure values are not null.
        // 2. Perform case-sensitive checks against the expected configuration values.
        if (apiKey == null || !apiKey.equals(validApiKey) || apiSecret == null || !apiSecret.equals(validApiSecret)) {
            throw new BadCredentialsException("Invalid api key or secret");
        }

        // On successful check, return an AUTHENTICATED token instance (wiping out the secret)
        return new ApiToken(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}