# 🍕 PizzaSpring

A **Spring Boot** REST API for managing a modern pizza ordering and delivery system.

![Java](https://img.shields.io/badge/Java-17-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-brightgreen?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)

## ✨ Features

- **Full CRUD** operations for Pizzas
- **Customer** (Cliente) management
- **Order** (Ordine) management with priority pricing logic
- **Rider** (delivery) management
- MySQL database integration with JPA/Hibernate
- Robust validation using Jakarta Validation
- DTO pattern with **MapStruct** mapping
- **OpenAPI / Swagger** documentation
- Exception handling
- Spring Security (basic setup)

## 🛠 Technologies

- **Java 17**
- **Spring Boot 3.4** (Web, Data JPA, Validation, Security)
- **MySQL**
- **Lombok** + **MapStruct**
- **SpringDoc OpenAPI** (Swagger UI)
- Maven

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL 8.0+

### 1. Clone the repository

```bash
git clone https://github.com/Shiinoqt/pizzaspring.git
cd pizzaspring

```

### 2. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE pizzaspring;

```

### 3. Configuration

Set up your credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pizzaspring?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password

# Optional: Hibernate settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

### 4. Run the application

```bash
./mvnw spring-boot:run

```

The app will start at `http://localhost:8080`.

## 📡 API Documentation

* **Base URL:** `http://localhost:8080/api`
* **Swagger UI:** `http://localhost:8080/api/swagger-ui.html`
* **OpenAPI JSON:** `http://localhost:8080/api/v3/api-docs`

## 📁 Project Structure

```text
src/main/java/com/spring/pizzaspring/
├── PizzaspringApplication.java
├── controller/        # REST Controllers
├── security/          # Security
├── service/           # Business logic
├── repository/        # JPA Repositories
├── model/             # JPA Entities
├── dto/               # Data Transfer Objects
├── mapper/            # MapStruct mappers
├── config/            # Misc.
├── component/         # Utility components
└── exceptions/        # Custom exceptions

```
