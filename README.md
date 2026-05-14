

# 🍕 PizzaSpring

A **Spring Boot** REST API for managing a pizza ordering system.

## ✨ Features

- **Pizza** management (CRUD)
- **Customer** (Cliente) management
- **Order** (Ordine) management with priority pricing
- **Rider** management
- MySQL database integration
- OpenAPI / Swagger documentation
- Validation and DTO mapping
- JPA + Hibernate

## 🛠 Technologies

- **Java 17**
- **Spring Boot 3** (Spring Web, Data JPA, Validation)
- **MySQL**
- **Lombok** + **MapStruct**
- **SpringDoc OpenAPI** (Swagger UI)

## 🚀 Getting Started

### Prerequisites

- Java 17
- Maven
- MySQL database

### Configuration

1. Clone the repository:
   ```bash
   git clone https://github.com/Shiinoqt/pizzaspring.git
   cd pizzaspring
   ```

2. Set up environment variables or `application.properties`:
   ```properties
   DB_URL=jdbc:mysql://localhost:3306/pizzaspring
   DB_USER=your_username
   DB_PASS=your_password
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📡 API Endpoints

The API is available at: `http://localhost:8080/api`

**Swagger UI**: [http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)

## Project Structure

```
src/main/java/com/spring/pizzaspring/
├── controller/      # REST Controllers
├── service/         # Business logic
├── repository/      # JPA Repositories
├── model/           # Entities
├── dto/             # Data Transfer Objects
├── mapper/          # MapStruct mappers
├── config/          # Configuration
└── exceptions/      # Custom exceptions
```
