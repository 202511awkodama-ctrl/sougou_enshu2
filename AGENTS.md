# AGENTS.md

## Repository objective

Build a runnable one-hour MVP called "Worklog Lite", a mini attendance / work log app.

Prioritize:
1. A working backend
2. Correct CRUD behavior
3. Simple server-rendered UI
4. Fast local demo

UI may be simple. Backend correctness matters more than visual polish.

## Tech choices

Use:
- Java 21
- Spring Boot
- Maven
- Spring Web
- Spring Data JPA
- Thymeleaf
- H2 Database
- Bean Validation
- Bootstrap CDN for minimal styling

Avoid:
- Authentication
- Docker
- External databases
- SPA frameworks
- Complex role management
- Over-engineered architecture
- Unnecessary dependencies

## Working rules

- Default to implementation. Do not stop at a plan unless truly blocked.
- If the repository is empty, create a new Maven Spring Boot project.
- If a Maven wrapper exists, use `./mvnw`; otherwise use `mvn`.
- Make reasonable assumptions and continue.
- Keep the feature set small enough to complete in one session.
- Use Japanese labels in the UI.
- Use English class, method, and package names.
- Prefer constructor injection.
- Keep controllers, services, repositories, and entities simple.
- Add only focused tests that help verify the MVP.
- Before finishing, run a build or tests:
  - Prefer `./mvnw test`
  - Otherwise `mvn test`
  - If tests cannot run, run package/build and clearly report the blocker.

## Final response

At the end, report:
- What was implemented
- Commands run
- Whether tests/build passed
- How to start the app
- Main URLs
- Any remaining limitations