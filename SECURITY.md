# Security Documentation

## Overview
This document explains the security features and design decisions implemented in the User Management System.

## Security Features

### 1. JWT-Based Authentication
- **Implementation**: Stateless authentication using JSON Web Tokens
- **Benefits**:
  - No server-side session storage required
  - Scalable across multiple servers
  - Token contains user information and expiration time
- **Token Structure**: Includes username and role information
- **Expiration**: Configurable (default 24 hours)

### 2. Password Security
- **Hashing Algorithm**: BCrypt with default strength (10 rounds)
- **Salt**: Automatically generated per password
- **Storage**: Only hashed passwords are stored in the database
- **Validation**: Passwords are validated against hashed values

### 3. Role-Based Access Control (RBAC)
- **Roles**: ADMIN and EMPLOYEE
- **Authorization Levels**:
  - ADMIN: Full CRUD access to user management
  - EMPLOYEE: Read-only access to user information
- **Implementation**: Spring Security's `@PreAuthorize` annotations

### 4. CSRF Protection Decision
**Status**: Disabled for JWT authentication

**Reasoning**:
- JWT tokens are sent in the Authorization header, not cookies
- Browsers don't automatically send custom headers in cross-site requests
- CSRF attacks rely on browsers automatically sending credentials (cookies)
- JWT in Authorization headers require explicit JavaScript code to add
- This makes the application immune to CSRF attacks by design

**Alternative Consideration**:
If cookies were used for JWT storage, CSRF protection would be required.

### 5. CORS Configuration
- **Current**: Allows all origins with credentials (development)
- **Production Recommendation**: Restrict to specific trusted origins
- **Configuration**: Externalized for environment-specific settings
- **Headers**: Configured to expose Authorization header for token access

### 6. Input Validation
- **Framework**: Jakarta Bean Validation (JSR-380)
- **Validation Points**:
  - Email format validation
  - Password minimum length (6 characters)
  - Required field validation
  - Username length constraints (3-50 characters)

### 7. Exception Handling
- **Global Handler**: Catches and processes all exceptions
- **Error Responses**: Structured, consistent format
- **Sensitive Information**: Error messages don't expose internal details
- **HTTP Status Codes**: Proper status codes for different error types

### 8. Environment-Based Configuration
- **Secrets Management**: Environment variables for sensitive data
- **Database Credentials**: Externalized from code
- **JWT Secret**: Configurable via environment variable
- **Benefits**: Different configurations for dev/staging/production

## Security Best Practices

### For Development
1. Use the provided `.env.example` as a template
2. Generate a strong JWT secret: `openssl rand -base64 64`
3. Don't commit `.env` file to version control
4. Use weak passwords only in development

### For Production
1. **Environment Variables**:
   ```bash
   export DB_USERNAME=prod_user
   export DB_PASSWORD=strong_password
   export JWT_SECRET=your_generated_secure_key
   ```

2. **CORS Configuration**:
   Update `SecurityConfig.java` to whitelist specific origins:
   ```java
   configuration.setAllowedOrigins(Arrays.asList("https://yourdomain.com"));
   ```

3. **HTTPS**: Always use HTTPS in production
4. **Database Security**:
   - Use strong database passwords
   - Restrict database access to application servers only
   - Regular security updates

5. **JWT Token Management**:
   - Store tokens securely on the client (not in localStorage if possible)
   - Implement token refresh mechanism
   - Consider shorter expiration times
   - Implement token revocation if needed

6. **Rate Limiting**: Consider adding rate limiting to prevent brute force attacks
7. **Monitoring**: Implement security event logging and monitoring
8. **Updates**: Keep all dependencies updated for security patches

## Security Audit Checklist

- [x] Passwords are hashed using BCrypt
- [x] JWT tokens are properly signed and validated
- [x] Role-based authorization is implemented
- [x] Input validation is in place
- [x] Sensitive data is externalized to environment variables
- [x] CSRF protection decision is documented
- [x] Exception handling doesn't expose sensitive information
- [x] CORS is configured (needs production hardening)
- [ ] Rate limiting (recommended for production)
- [ ] Security headers (recommended: Helmet.js equivalent for Spring)
- [ ] Token refresh mechanism (optional enhancement)
- [ ] Account lockout after failed attempts (optional enhancement)

## Known Limitations

1. **No Password Strength Requirements**: Beyond minimum length
   - **Recommendation**: Add password complexity validation

2. **No Token Refresh**: Tokens expire after set time
   - **Recommendation**: Implement refresh token mechanism

3. **No Rate Limiting**: Susceptible to brute force
   - **Recommendation**: Add Spring Security rate limiting or use API gateway

4. **Permissive CORS in Default Config**: Allows all origins
   - **Recommendation**: Update for production deployment

## Incident Response

If a security issue is discovered:
1. Change JWT secret immediately
2. Force re-authentication for all users
3. Review logs for suspicious activity
4. Update affected code
5. Deploy patches promptly
6. Notify affected users if needed

## Contact

For security concerns or to report vulnerabilities, please contact the security team or open a confidential issue.
