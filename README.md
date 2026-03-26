# URL Shortener (Spring Boot)

A simple URL shortener web app built with Spring Boot, Thymeleaf, and MongoDB.

## Live Deployment
- **Production URL:** `https://<your-deployment-link-here>`

> Replace the placeholder above after deployment.

## Features
- Shorten long URLs into 7-character Base62 codes.
- Normalize URLs by adding `https://` when protocol is missing.
- Reuse existing short code when the same normalized URL is submitted again.
- Redirect short codes to their original URLs.
- Return 404 for invalid short codes.

## Tech Stack
- Java 17
- Spring Boot 3.3.x
- Spring Web + Thymeleaf
- Spring Data MongoDB
- JUnit 5 + Mockito

## Prerequisites
- JDK 17+
- Maven 3.9+
- MongoDB instance (local or hosted)

## Configuration
Set the following in `src/main/resources/application.properties`:

- `spring.data.mongodb.uri=<your-mongodb-uri>`
- `server.port=8080` (optional)

## Run Locally
```bash
mvn spring-boot:run
```
Then open: `http://localhost:8080`
Open: `http://localhost:8080`

## Build
```bash
mvn clean package
```

## Test
```bash
mvn test
```

## API / Routes
- `GET /` → Home page with URL input
- `POST /shorten` → Create or return an existing short URL
- `GET /{shortCode}` → Redirect to the original URL

## Project Structure
- `src/main/java/.../controller` → MVC controllers
- `src/main/java/.../service` → URL shortening business logic
- `src/main/java/.../repository` → MongoDB repository layer
- `src/main/java/.../model` → Data and API models
- `src/main/resources/templates` → Thymeleaf pages
- `src/test/java/...` → Unit tests
