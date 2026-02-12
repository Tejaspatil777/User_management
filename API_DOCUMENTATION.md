# User Management System - API Documentation

## Overview
This is a secure backend application built with Spring Boot that provides RESTful APIs for user management with JWT-based authentication and role-based authorization.

## Base URL
```
http://localhost:8080
```

## Authentication
Most endpoints require a JWT token. Include it in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

## Roles
- **ADMIN**: Full access to all operations
- **EMPLOYEE**: Read-only access to user information

---

## API Endpoints

### 1. Authentication Endpoints

#### Register a New User
**POST** `/api/auth/register`

**Request Body:**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "role": "EMPLOYEE"
}
```

**Response (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "role": "EMPLOYEE"
}
```

#### Login
**POST** `/api/auth/login`

**Request Body:**
```json
{
  "username": "john_doe",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "role": "EMPLOYEE"
}
```

---

### 2. User Management Endpoints

#### Get All Users
**GET** `/api/users`

**Authorization:** ADMIN or EMPLOYEE

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "EMPLOYEE",
    "active": true,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

#### Get User by ID
**GET** `/api/users/{id}`

**Authorization:** ADMIN or EMPLOYEE

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "EMPLOYEE",
  "active": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### Get User by Username
**GET** `/api/users/username/{username}`

**Authorization:** ADMIN or EMPLOYEE

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "EMPLOYEE",
  "active": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

#### Update User
**PUT** `/api/users/{id}`

**Authorization:** ADMIN only

**Request Body:**
```json
{
  "email": "newemail@example.com",
  "firstName": "John",
  "lastName": "Smith",
  "role": "ADMIN",
  "active": true
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "newemail@example.com",
  "firstName": "John",
  "lastName": "Smith",
  "role": "ADMIN",
  "active": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-16T14:20:00"
}
```

#### Delete User
**DELETE** `/api/users/{id}`

**Authorization:** ADMIN only

**Response (200 OK):**
```json
{
  "message": "User deleted successfully"
}
```

---

## Error Responses

### 400 Bad Request - Validation Error
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "email": "Email should be valid",
    "password": "Password must be at least 6 characters"
  },
  "path": "/api/auth/register"
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid username or password",
  "path": "/api/auth/login"
}
```

### 403 Forbidden
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Access denied",
  "path": "/api/users/1"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 999",
  "path": "/api/users/999"
}
```

### 409 Conflict
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "Username is already taken",
  "path": "/api/auth/register"
}
```

---

## Setup and Configuration

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Configuration
Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_management?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### JWT Configuration
The JWT secret and expiration time can be configured in `application.properties`:
```properties
jwt.secret=your-secret-key-here
jwt.expiration=86400000  # 24 hours in milliseconds
```

### Running the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

---

## Testing with cURL

### Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@example.com",
    "password": "admin123",
    "firstName": "Admin",
    "lastName": "User",
    "role": "ADMIN"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### Get all users (with JWT token)
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

## Security Features

1. **JWT Authentication**: Secure token-based authentication
2. **Password Encryption**: BCrypt password hashing
3. **Role-Based Access Control**: ADMIN and EMPLOYEE roles
4. **CORS Configuration**: Configured for React Native frontend integration
5. **Exception Handling**: Comprehensive error handling and validation
6. **Session Management**: Stateless authentication

---

## Integration with React Native

The API is configured to work seamlessly with React Native applications:

### Example React Native Setup
```javascript
// Configure base URL
const API_BASE_URL = 'http://your-server-ip:8080';

// Login function
const login = async (username, password) => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    });
    const data = await response.json();
    // Store token securely (e.g., AsyncStorage)
    await AsyncStorage.setItem('token', data.token);
    return data;
  } catch (error) {
    console.error('Login error:', error);
  }
};

// Authenticated API call
const getUsers = async () => {
  const token = await AsyncStorage.getItem('token');
  const response = await fetch(`${API_BASE_URL}/api/users`, {
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  return await response.json();
};
```
