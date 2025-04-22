# Chapter 9: Spring Security Configuration

Welcome to the final chapter! In [Chapter 8: Repository Layer](08_repository_layer_.md), we saw how our services interact with the database using Spring Data JPA repositories. We've covered how requests come in ([Chapter 2: REST API Controllers](02_rest_api_controllers_.md)), how users are authenticated ([Chapter 3: User Authentication & Authorization](03_user_authentication___authorization_.md)), how data is structured ([Chapter 4: FQW Data Model & Management](04_fqw_data_model___management_.md)), packaged ([Chapter 5: Data Transfer Objects (DTOs) & Mappers](05_data_transfer_objects__dtos____mappers_.md)), and processed ([Chapter 6: Service Layer](06_service_layer_.md), [Chapter 7: Protocol Generation & Processing](07_protocol_generation___processing_.md)).

Now, how do we tie all the security aspects together? How does the application know *which* web addresses (URLs) require a user to be logged in? How does it know only administrators can perform certain actions? How does it allow our frontend website (running on one address) to talk to our backend services (running on different addresses)? This is all managed by the **Spring Security Configuration**.

## What Problem Does This Solve? The Security Rulebook

Imagine our microservice application is a large office building. We have security guards at the main entrance ([Chapter 3: User Authentication & Authorization](03_user_authentication___authorization_.md) using JWTs) who check IDs. But how do those guards, and other guards inside the building, know the specific rules?
*   Which doors are open to everyone (e.g., the main lobby)?
*   Which doors require *any* valid ID card (e.g., general office floors)?
*   Which doors require a special *high-level clearance* ID card (e.g., the server room)?
*   How should guards handle visitors arriving from a different, specific building (like our frontend website)?

The **Spring Security Configuration** file is like the official **rulebook** for the security guards. It lays out all these access rules and security procedures in one place.

**Use Case:**

1.  We want the login page (`/api/auth/login`) to be accessible by anyone, even if they aren't logged in.
2.  We want general API endpoints like viewing student data (`/api/fqw/students`) to require a user to be logged in (authenticated), but any valid user is okay.
3.  We want administrative actions, like deleting a user (`DELETE /api/users/someUserId`), to require the user to be logged in *and* have the specific "ADMIN" role.
4.  We need to allow our separate frontend application (e.g., running at `http://localhost:5173`) to make requests to our backend microservices (e.g., `http://localhost:8081/api/...`).

The `SecurityConfig` class within each relevant service (`auth`, `fqw`, `email`, `protocol`, `support`) defines these rules.

## Key Concepts: Inside the Rulebook

1.  **The Configuration Class (`SecurityConfig.java`):** This is the main Java class where we define all our security rules. It's marked with annotations to tell Spring what it is.
    *   `@Configuration`: A standard Spring annotation indicating this class provides bean definitions and configuration settings.
    *   `@EnableWebSecurity`: This specifically enables Spring's web security features, activating the security mechanisms.
    *   **Analogy:** The physical binder labeled "Security Rulebook".

2.  **The Security Filter Chain (`SecurityFilterChain` Bean):** This is the most important part. It's a method within the `SecurityConfig` class (usually annotated with `@Bean`) that defines a sequence of security checks (filters) that every incoming web request must pass through. We configure the rules within this chain.
    *   **Analogy:** The main chapter in the rulebook detailing the step-by-step procedures guards follow for checking people entering different areas.

3.  **Authorization Rules (`authorizeHttpRequests`):** Inside the filter chain configuration, this is where we specify *who* can access *what*. We use methods like:
    *   `requestMatchers("/path/to/allow")`: Specifies which URL patterns these rules apply to.
    *   `permitAll()`: Allows access to the matched path(s) for *everyone*, no login required.
    *   `authenticated()`: Requires the user to be logged in (valid JWT presented, as checked by our `JwtRequestFilter` from Chapter 3).
    *   `hasRole("ROLENAME")`: Requires the user to be logged in *and* have the specified role (e.g., "ADMIN").
    *   **Analogy:** Specific rules in the rulebook, like "Rule 5.1: Access to /api/auth/login - Permit All" or "Rule 7.3: Access to /api/admin/** - Requires ADMIN Role".

4.  **CORS Configuration (`.cors(...)`):** Stands for Cross-Origin Resource Sharing. Browsers have a security feature that prevents a webpage from `http://localhost:5173` (the frontend "origin") from making requests to `http://localhost:8081` (the backend "origin") unless the backend explicitly allows it. CORS configuration tells the backend service "It's okay to accept requests from `http://localhost:5173`".
    *   **Analogy:** A rule stating that guards should allow entry to authorized visitors arriving specifically from the "Frontend Building" located at address `localhost:5173`.

5.  **CSRF Disabling (`.csrf(disable())`):** Stands for Cross-Site Request Forgery. This is a type of web attack. Protection against it often relies on server-side sessions. Since our APIs use JWTs and are **stateless** (don't rely on server sessions, as discussed in [Chapter 3: User Authentication & Authorization](03_user_authentication___authorization_.md)), we typically disable this built-in protection. JWTs themselves provide protection against this kind of attack when used correctly.
    *   **Analogy:** Removing an older, session-based security checkpoint because our new ID card system (JWT) makes it redundant.

6.  **Stateless Sessions (`.sessionManagement(STATELESS)`):** This explicitly tells Spring Security *not* to create or manage user sessions on the server side. We rely entirely on the JWT sent with each request.
    *   **Analogy:** Instructing guards not to keep a logbook of who is currently inside the building, because everyone must show their valid ID card (JWT) every time they pass a checkpoint.

7.  **JWT Filter Integration (`.addFilterBefore(jwtFilter, ...)`):** This plugs our custom `JwtRequestFilter` (which we learned about in [Chapter 3: User Authentication & Authorization](03_user_authentication___authorization_.md)) into the security filter chain. This ensures that our filter runs early in the process to check for a valid JWT on incoming requests *before* Spring tries to apply the authorization rules.
    *   **Analogy:** Adding a specific instruction in the rulebook: "Step 1: Check for a valid JWT ID card using the procedure defined by `JwtRequestFilter`."

8.  **Password Encoder Bean (`PasswordEncoder`):** Although part of authentication (Chapter 3), the `SecurityConfig` often defines the `PasswordEncoder` (like `BCryptPasswordEncoder`) as a `@Bean`. This makes the specific password hashing algorithm available throughout the application, ensuring consistency when checking passwords during login and potentially when users are created.
    *   **Analogy:** Specifying the official, secure method for verifying ID card credentials (passwords) used by all guards.

## How It Solves the Use Case: Applying the Rules

Let's see how a typical `SecurityConfig` class (simplified from the `auth` service) addresses our use case requirements:

```java
// File: auth/src/main/java/ru/emiren/auth/Config/SecurityConfig.java

@Configuration // Marks this as a configuration class
@EnableWebSecurity // Enables Spring web security features
public class SecurityConfig {

    // Inject our custom JWT filter and entry point (from Chapter 3)
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    // Defines the main security rules
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF protection (for stateless JWT API)
            .csrf(AbstractHttpConfigurer::disable)

            // 2. Configure CORS (allow frontend origin)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // 3. Define Authorization Rules
            .authorizeHttpRequests(auth -> auth
                // Use Case 1: Allow anyone to access login/register
                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                // Use Case 3: Require ADMIN role for DELETE requests (example)
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                // Use Case 2: Require any authenticated user for other /api/** paths
                .requestMatchers("/api/**").authenticated()
                // Allow access to static resources (CSS, JS) if needed
                .requestMatchers("/css/**", "/js/**").permitAll()
                // Secure everything else by default (optional, depends on strategy)
                .anyRequest().authenticated()
            )

            // 4. Configure session management to be STATELESS
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Handle authentication errors with our custom entry point
            .exceptionHandling(exc -> exc.authenticationEntryPoint(jwtAuthenticationEntryPoint))

            // 5. Add our custom JWT filter BEFORE the standard authentication filter
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Build the security filter chain
    }

    // 6. Defines the CORS configuration details
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests specifically from our frontend origin
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        // Allow common HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Allow credentials (like cookies or Authorization headers)
        configuration.setAllowCredentials(true);
        // Allow specific headers needed for auth and content type
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this configuration to all paths ("/**")
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // 7. Defines the password encoder bean (used in Chapter 3)
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCrypt for strong password hashing
        return new BCryptPasswordEncoder();
    }
}
```

**Explanation:**

1.  **`.csrf(disable())`**: Turns off CSRF protection, suitable for our stateless JWT approach.
2.  **`.cors(...)`**: Enables CORS and points to the `corsConfigurationSource` bean for detailed rules.
3.  **`.authorizeHttpRequests(...)`**: This block defines the access rules:
    *   `/api/auth/login` and `/api/auth/register` are open to everyone (`permitAll`).
    *   `DELETE` requests to `/api/users/**` require the "ADMIN" role (`hasRole("ADMIN")`).
    *   All other requests under `/api/**` require the user to be logged in (`authenticated`).
    *   Static resources like CSS/JS are also permitted (`permitAll`).
    *   Any other request not specifically mentioned still requires authentication (`anyRequest().authenticated()`).
4.  **`.sessionManagement(STATELESS)`**: Ensures no server-side sessions are used.
5.  **`.addFilterBefore(...)`**: Inserts our `JwtRequestFilter` into the chain *before* Spring's default `UsernamePasswordAuthenticationFilter`. This means our JWT check runs first for requests that might have a token.
6.  **`corsConfigurationSource()` Bean:** Defines the specific CORS rules. It allows the origin `http://localhost:5173`, specific HTTP methods, credentials, and headers. This allows our frontend to communicate with the backend.
7.  **`passwordEncoder()` Bean:** Provides the `BCryptPasswordEncoder` instance needed for securely handling passwords during login.

This single configuration class sets up the core security posture for the service. Similar `SecurityConfig` classes exist in `fqw`, `email`, `protocol`, and `support` services, potentially with slightly different authorization rules based on their specific needs, but generally following the same pattern (disabling CSRF, enabling CORS, setting stateless sessions). The `auth` service's configuration is the most complex as it also wires in the `JwtRequestFilter` and `JwtAuthenticationEntryPoint`. Other services rely on the JWT validation performed by this filter (or a similar mechanism if an API Gateway is used).

## Under the Hood: The Security Filter Chain

When a web request arrives at our service, it doesn't immediately hit the [REST API Controller](02_rest_api_controllers_.md). It first goes through a series of **Security Filters** defined by our `SecurityConfig`. Think of it like passing through multiple security checkpoints before reaching your destination office.

1.  **Request Arrives:** e.g., `GET /api/fqw/students` with an `Authorization: Bearer <token>` header.
2.  **CORS Filter:** Checks if the request origin is allowed (based on `corsConfigurationSource`). Handles preliminary `OPTIONS` requests if necessary.
3.  **`JwtRequestFilter` (Our Custom Filter):** This filter runs because we added it with `.addFilterBefore()`. It extracts the JWT from the header, validates it, and if valid, loads the user details and sets the authentication context (tells Spring Security who the user is for this request), as detailed in [Chapter 3: User Authentication & Authorization](03_user_authentication___authorization_.md).
4.  **Authorization Filter:** This filter uses the rules defined in `.authorizeHttpRequests()`. It checks the request path (`/api/fqw/students`) and the user's authentication status (set by `JwtRequestFilter`). In this case, the rule is `.requestMatchers("/api/**").authenticated()`. Since the JWT was valid, the user is authenticated, and access is granted.
5.  **Other Filters:** Several other built-in Spring Security filters might run.
6.  **Controller:** If all security checks pass, the request finally reaches the intended `ApplicationProgrammingInterfaceController` method in the `fqw` service.

If at any point a filter denies access (e.g., invalid JWT, insufficient role), the request is stopped, and an error response (like 401 Unauthorized or 403 Forbidden) is sent back immediately. The request never reaches the controller.

**Sequence Diagram: Request Through Filter Chain**

```mermaid
sequenceDiagram
    participant Client
    participant Filters as Spring Security Filter Chain
    participant JWTFilter as JwtRequestFilter (inside Chain)
    participant AuthzFilter as Authorization Filter (inside Chain)
    participant Controller as Target Controller

    Client->>+Filters: GET /api/fqw/students (with Auth Header)
    Note over Filters: Request enters chain
    Filters->>Filters: CORS Filter checks origin (Passes)
    Filters->>+JWTFilter: Process Request
    JWTFilter->>JWTFilter: Validate JWT (Success)
    JWTFilter->>Filters: Set SecurityContext (User is authenticated)
    Filters-->>-JWTFilter: Continue chain
    Filters->>+AuthzFilter: Process Request
    AuthzFilter->>AuthzFilter: Check rules for /api/fqw/students (Needs 'authenticated')
    AuthzFilter->>Filters: User is authenticated (Access Granted)
    Filters-->>-AuthzFilter: Continue chain
    Note over Filters: Other filters run...
    Filters->>+Controller: Forward Request
    Controller-->>-Filters: Process request, return response data
    Filters-->>-Client: Send successful response (200 OK + data)

```

This filter chain mechanism is the core of how Spring Security enforces the rules defined in `SecurityConfig`.

## Conclusion

We've reached the end of our tutorial journey! In this chapter, we learned about **Spring Security Configuration**:

*   It acts as the central **rulebook** defining access control (`authorizeHttpRequests`), CORS policy, session management, and other security aspects for our microservices.
*   Key annotations like `@Configuration` and `@EnableWebSecurity` enable these features.
*   The `SecurityFilterChain` bean is where we configure the sequence of security checks using `HttpSecurity`.
*   We configure rules to `permitAll`, require `authenticated` users, or demand specific roles like `hasRole("ADMIN")`.
*   We enable **CORS** to allow frontend-backend communication across different origins.
*   We disable **CSRF** protection and configure **stateless sessions** for our JWT-based authentication.
*   We integrate our custom **`JwtRequestFilter`** to handle token validation within the security chain.

Understanding `SecurityConfig` is crucial because it determines how our application protects its resources and ensures that only authorized users can perform specific actions, tying together many of the concepts we've explored throughout these chapters.

From the user-facing [Hub Service Representation](01_hub_service_representation_.md) to the backend details of the [Repository Layer](08_repository_layer_.md) and the security glue of `SecurityConfig`, you now have a foundational understanding of how the different parts of the `microservices` project work together. Keep exploring the code and happy coding!

---

Generated by [AI Codebase Knowledge Builder](https://github.com/The-Pocket/Tutorial-Codebase-Knowledge)