package com.spring.pizzaspring.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Main security configuration class for the PizzaSpring application.
 * It sets up authorization rules, filter order, exception entry points, and session behaviors.
 */
@Configuration
@EnableWebSecurity // Enables Spring Security's web security support and integrates it with Spring MVC
public class SecurityConfig {

    // Injects the custom API Key verification filter
    @Autowired
    private ApiKeyFilter apiKeyFilter;

    /**
     * Defines the SecurityFilterChain bean which intercepts and manages all incoming HTTP requests.
     * * @param http The HttpSecurity object used to build security configurations.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF (Cross-Site Request Forgery) protection.
                // This is safe and standard practice for stateless REST APIs that use tokens/headers instead of cookies.
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Define endpoint authorization rules
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                // These specific patterns require a valid API Key and Secret authentication
                                .requestMatchers("/ordini/**", "/clienti/**", "/riders/**").authenticated()
                                // Any other request outside of the ones listed above is publicly accessible
                                .anyRequest().permitAll()
                )

                // 3. Configure Exception Handling & Authentication Entry Point
                // This custom entry point catches unauthorized requests that try to access protected paths
                // without passing through the filter successfully (e.g., missing credentials entirely).
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Enforce a strict 401 Unauthorized HTTP status
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            // Set the response content type to application/json
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            // Write a clear, developer-friendly message into the body
                            response.getWriter().write("Missing or invalid API credentials");
                        })
                )

                // 4. Configure Session Management to be completely Stateless.
                // Spring Security will never create an HttpSession and will never use it to obtain the SecurityContext.
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 5. Inject the custom filter into the Spring Security internal filter lifecycle.
                // Your 'apiKeyFilter' will execute *before* the standard UsernamePasswordAuthenticationFilter.
                // This ensures credentials are checked right at the beginning of the security assessment.
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class);

        // Build and return the completed security chain structure
        return http.build();
    }

    /**
     * Explicitly prevents duplicate filter execution.
     * Since ApiKeyFilter extends GenericFilterBean and is likely marked as a Spring Component,
     * Spring Boot would automatically register it as a global servlet filter.
     * This bean instructs Spring Boot to skip global auto-registration, leaving it to run exclusively
     * inside the Spring Security filter chain.
     */
    @Bean
    public FilterRegistrationBean<ApiKeyFilter> disableAutoRegistration(ApiKeyFilter filter) {
        FilterRegistrationBean<ApiKeyFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false); // Disables servlet container-level auto-registration
        return registration;
    }
}