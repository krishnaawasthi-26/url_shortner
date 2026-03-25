# URL Shortner (Spring Boot)

A simple URL shortener website built with Java Spring Boot and divided into **3 clear development stages**.

## Stage 1: Front-end (clean, simple interface)
- Create a minimal landing page with:
  - App title and short description.
  - URL input box.
  - "Shorten URL" button.
  - Result section to display generated short link.
- Keep the UI lightweight and readable (single-card layout, simple colors, no clutter).

## Stage 2: Accept and store URL
- Add backend endpoint to receive submitted URL.
- Normalize input (prepend `https://` if protocol is missing).
- Generate a short code using Base62 characters.
- Store short code -> original URL mapping in memory (`ConcurrentHashMap`) for now.
- Return short URL and show it on the front end.

## Stage 3: Remaining core behavior
- Add redirect route (`/{shortCode}`) that resolves short code and redirects to original URL.
- Handle invalid/missing short code with `404 Not Found`.
- Add automated unit tests for service logic.
- Future extension ideas:
  - Persist data in MySQL/PostgreSQL.
  - Add expiration dates.
  - Track click analytics.
  - Add custom aliases.
  - Add authentication and rate limiting.

## Run locally
```bash
mvn spring-boot:run
```
Then open: `http://localhost:8080`

## Test
```bash
mvn test
```
