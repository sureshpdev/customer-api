# Customer Management API

This project provides a RESTful API for managing customer data. It is built with Spring Boot and exposes various endpoints to create, read, update, and delete customer information. It uses an H2 database for storage, and the application is ready to be tested with sample requests.

## Table of Contents
1. [How to Build and Run the Application]
2. [Sample Requests]
3. [Accessing the H2 Database Console]
4. [Assumptions]

## How to Build and Run the Application

### Prerequisites
- Java 17 or higher
- Maven

### Steps to Build and Run:
1. **Clone the repository:**
    git clone https://github.com/Suresh-548/customer-api.git
    cd customer-api

2. **Build the project:**
    mvn clean install

3. **Run the application:**
    mvn spring-boot:run

    The application will start on `http://localhost:8080`.

## Sample Requests

Here are some sample requests to test the endpoints.

### 1. **Retrieve a customer by ID**
    - **Method:** `GET`
    - **Endpoint:** `/customers/{id}`
      curl -X GET http://localhost:8080/customers/1

### 2. **Get customer by name (optional query parameter)**
    - **Method:** `GET`
    - **Endpoint:** `/customers/by-name?name={name}`
      curl -X GET "http://localhost:8080/customers/by-name?name=Suresh"

### 3. **Get customer by email (optional query parameter)**
    - **Method:** `GET`
    - **Endpoint:** `/customers/by-email?email={email}`
      curl -X GET "http://localhost:8080/customers/by-email?email=test.demo@gmail.com"

### 4. **Create a new customer**
    - **Method:** `POST`
    - **Endpoint:** `/customers`
      curl -X POST http://localhost:8080/customers \
      -H "Content-Type: application/json" \
      -d '{
           "name": "Suresh",
           "email": "test.demo@gmail.com",
           "annualSpend":5000,
           "lastPurchaseDate": "2025-05-05"
          }'

### 5. **Update an existing customer**
    - **Method:** `PUT`
    - **Endpoint:** `/customers/{id}`
    - **Sample Request:**
      curl -X PUT http://localhost:8080/customers/1 \
      -H "Content-Type: application/json" \
      -d '{{
           "name": "Test",
           "email": "test.demo@gmail.com",
           "annualSpend":1000,
           "lastPurchaseDate": "2025-05-05"
          }'

### 6. **Delete a customer**
    - **Method:** `DELETE`
    - **Endpoint:** `/customers/{id}`
      curl -X DELETE http://localhost:8080/customers/1

## Accessing the H2 Database Console

The application uses an H2 database, which is an in-memory database by default. To access the H2 database console:

1. **Start the application** using `mvn spring-boot:run`.
2. **Open the H2 console** by visiting the following URL in your browser:
    http://localhost:8080/h2-console

3. **Configure the connection**:
    - **JDBC URL**: `jdbc:h2:mem:testdb`
    - **Username**: `sa`
    - **Password**: (Leave this field blank)

4. You can now run SQL queries directly against the H2 database.

## Assumptions

- The API expects a customer to have the following fields:
    - `name` (String)
    - `email` (String)
    - `annualSpend` (BigDecimal)
    - `lastPurchaseDate` (LocalDate)

- If a customer with the same email already exists, a new customer will not be created, and an error will be returned.

- The H2 database is used in-memory, meaning the data is lost every time the application is restarted.

- All endpoints return standard HTTP response codes:
    - `200 OK` for successful operations.
    - `201 Created` when a new customer is successfully created.
    - `400 Bad Request` for invalid input or missing required fields.
    - `404 Not Found` when a customer is not found.

## Conclusion

This README should give you the basic information to run and test the Customer Management API. If you need to make modifications, feel free to adjust the configurations and endpoints as required.

For further questions or issues, please open an issue in the repository.
