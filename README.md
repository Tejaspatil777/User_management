# User Management System

## Overview
The User Management System is a secure backend application built using Spring Boot, Hibernate, and MySQL. It provides CRUD operations to create, update, delete, and manage users efficiently. The system implements JWT-based authentication and role-based authorization (such as ADMIN and EMPLOYEE) to ensure secure access control.

The project focuses on building secure REST APIs, proper exception handling, and seamless integration with a React Native frontend to manage the complete user lifecycle.

## Features

- **User Registration & Authentication**: Secure user registration with JWT token generation
- **JWT-Based Security**: Stateless authentication using JSON Web Tokens
- **Role-Based Authorization**: ADMIN and EMPLOYEE roles with different access levels
- **CRUD Operations**: Complete user management functionality
- **Exception Handling**: Comprehensive error handling with meaningful messages
- **Input Validation**: Server-side validation for all user inputs
- **CORS Support**: Configured for React Native frontend integration
- **MySQL Integration**: Persistent data storage with Hibernate ORM

## Technology Stack

- **Spring Boot 3.1.5**: Main framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database access layer
- **Hibernate**: ORM for database operations
- **MySQL**: Relational database
- **JWT (JSON Web Tokens)**: Secure authentication
- **Maven**: Build and dependency management
- **Lombok**: Reduce boilerplate code
- **JUnit 5 & Mockito**: Testing framework

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Tejaspatil777/User_management.git
   cd User_management
   ```

2. **Configure MySQL Database**
   
   Create a MySQL database or update the `application.properties` file:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/user_management?createDatabaseIfNotExist=true
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### User Management (Requires Authentication)
- `GET /api/users` - Get all users (ADMIN, EMPLOYEE)
- `GET /api/users/{id}` - Get user by ID (ADMIN, EMPLOYEE)
- `GET /api/users/username/{username}` - Get user by username (ADMIN, EMPLOYEE)
- `PUT /api/users/{id}` - Update user (ADMIN only)
- `DELETE /api/users/{id}` - Delete user (ADMIN only)

For detailed API documentation with request/response examples, see [API_DOCUMENTATION.md](API_DOCUMENTATION.md)

## Usage Example

### 1. Register a new admin user
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

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### 3. Access protected endpoints
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Security Features

1. **Password Encryption**: BCrypt hashing for secure password storage
2. **JWT Authentication**: Token-based authentication with expiration
3. **Role-Based Access Control**: Different permissions for ADMIN and EMPLOYEE
4. **Exception Handling**: Global exception handler for consistent error responses
5. **Input Validation**: Comprehensive validation for all user inputs
6. **CORS Configuration**: Configured for frontend integration

## Testing

Run the test suite:
```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   ├── java/com/usermanagement/
│   │   ├── config/              # Security and application configuration
│   │   ├── controller/          # REST API controllers
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── exception/           # Custom exceptions and handlers
│   │   ├── model/               # JPA entities
│   │   ├── repository/          # Data access layer
│   │   ├── security/            # JWT and authentication components
│   │   └── service/             # Business logic layer
│   └── resources/
│       └── application.properties
└── test/                        # Unit and integration tests
```

## React Native Integration

This backend is designed to work seamlessly with React Native applications. The CORS configuration allows cross-origin requests, and all responses are in JSON format.

### Example React Native Code
```javascript
const login = async (username, password) => {
  const response = await fetch('http://your-server:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  });
  const data = await response.json();
  // Store token and use for authenticated requests
  await AsyncStorage.setItem('token', data.token);
};
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is open source and available under the [MIT License](LICENSE).

## Contact

For any queries or support, please open an issue in the GitHub repository.
