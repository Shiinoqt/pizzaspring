# PizzaSpring

PizzaSpring is a backend web application built using Java and Spring Boot. It provides a RESTful API designed to manage a pizza ordering system, allowing for the administration of pizza menus, tracking of inventory, and processing of customer orders.

## Features

- Pizza Menu Management: Create, read, update, and delete pizza varieties, sizes, and toppings.
- Order Processing: Handle customer orders, calculate totals, and track the status of orders.
- Customer Management: Manage customer profiles and order histories.
- Database Integration: Persistence layer using Spring Data JPA and Hibernate.

## Tech Stack

- Java 17 or higher
- Spring Boot
- Spring Data JPA
- Database (MySQL / PostgreSQL / H2)
- Maven (Dependency Management)

---

## Prerequisites

Ensure you have the following installed before running the application:
- JDK 17+
- Maven 3.x
- Git

---

## Getting Started

### 1. Clone the Repository
```bash
git clone [https://github.com/Shiinoqt/pizzaspring.git](https://github.com/Shiinoqt/pizzaspring.git)
cd pizzaspring

```

### 2. Database Configuration

Open the configuration file located at `src/main/resources/application.properties` (or `application.yml`) and adjust the database credentials to match your local setup:

```properties
server.port=8080

# Database Configuration (Example for MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/pizza_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password

# JPA / Hibernate Properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

```

### 3. Run the Application

You can start the server using the Maven wrapper included in the project:

On Windows:

```bash
mvnw.cmd spring-boot:run

```

On Linux/macOS:

```bash
./mvnw spring-boot:run

```

The application will launch on `http://localhost:8080`.

---

## API Endpoints

### Pizza Management

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/pizzas | Retrieve all available pizzas |
| GET | /api/pizzas/{id} | Retrieve details of a specific pizza |
| POST | /api/pizzas | Add a new pizza to the menu |
| PUT | /api/pizzas/{id} | Update an existing pizza's details |
| DELETE | /api/pizzas/{id} | Remove a pizza from the menu |

### Order Management

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | /api/orders | Place a new pizza order |
| GET | /api/orders/{id} | Fetch details of a specific order |
| PATCH | /api/orders/{id}/status | Update the fulfillment status of an order |

---
