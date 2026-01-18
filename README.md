# JournalSystem_Messages

Message & conversation microservice for a larger “JournalSystem” project.  
Provides REST endpoints to create conversations, send messages, and fetch conversation history.

This service is designed to run as part of a microservices setup (Docker/Kubernetes) and uses MySQL for persistence.

---

## Features

- Create conversations between two users
- Fetch all conversations for a user
- Send messages in a conversation
- Fetch messages for a conversation
- MySQL persistence using Spring Data JPA (Hibernate)
- Containerized with Docker and deployable with Kubernetes
- Unit-tested service logic (Mockito/JUnit)

---

## Tech Stack

- **Java 17**
- **Spring Boot** (REST API)
- **Spring Data JPA / Hibernate**
- **MySQL**
- **Docker**
- **Kubernetes (k3s)**
- **JUnit 5 + Mockito** (unit tests)

---

## Architecture (high level)

- `Controllers/` expose REST endpoints
- `Persistence/Models/` contains JPA entities (`ConversationDb`, `MessageDb`)
- `Persistence/Repositories/` contains Spring Data repositories
- `Persistence/Service/` contains business logic and rules

This service is meant to be consumed by a frontend and/or other backend services.

---

Kubernetes
  - This repo is deployed as part of the project’s Kubernetes setup.
  - See JournalSystem_Q8SFILES for the manifests used in deployment.

CI/CD
	-	On push to main, GitHub Actions:
	-	runs unit tests
	-	builds the JAR
	-	builds and pushes a Docker image (tagged with commit SHA)
	-	deploys to a VM (k3s) via SSH and updates the Kubernetes deployment
  
---

## Running locally (Docker)

### 1) Build and run MySQL + API (example)

If you already have a MySQL instance, skip the DB container and just set the datasource variables.

```bash
# Example: start MySQL
docker run --name message-db \
  -e MYSQL_ROOT_PASSWORD=12345AbcD \
  -e MYSQL_DATABASE=journalmessage \
  -e MYSQL_USER=journalmessage_user \
  -e MYSQL_PASSWORD=password123 \
  -p 3306:3306 \
  -d mysql:8.0
 ```
