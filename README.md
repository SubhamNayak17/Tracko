# 🚀 Tracko — AI-Powered Courier Logistics Tracking System

A production-ready Spring Boot backend for managing courier logistics with AI-powered shipment tracking.

## 🛠️ Tech Stack
Java 25 • Spring Boot 4.0.6 • PostgreSQL 17 • JWT Security • Gemini AI • Docker • Swagger

## 🌟 Key Features
- 🤖 **AI Tracking** — Ask in plain English "Where is my package?"
- 🔐 **JWT Security** — Role based access (ADMIN/USER)
- 📦 **7 Core Entities** — Customer, Shipment, Payment, Package, Warehouse, DeliveryAgent, TrackingHistory
- 📄 **60+ REST APIs** — Full CRUD with pagination and sorting
- ✅ **21 Unit Tests** — JUnit 5 + Mockito
- 🐳 **Dockerized** — Run with one command
- 📖 **Swagger Docs** — Auto generated API documentation

## 🚀 Quick Start

### With Docker
```bash
git clone https://github.com/SubhamNayak17/Tracko.git
cd Tracko
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Add your Gemini API key in application.properties
docker-compose up --build
```

### Without Docker
```bash
git clone https://github.com/SubhamNayak17/Tracko.git
cd Tracko
# Create PostgreSQL database: CREATE DATABASE courier_db;
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Update credentials in application.properties
mvn spring-boot:run
```

App → `http://localhost:8080`
Swagger → `http://localhost:8080/swagger-ui/index.html`

## 🔐 Authentication
```bash
# Register
POST /auth/register
{ "name": "Admin", "email": "admin@tracko.com", "password": "admin123", "role": "ADMIN" }

# Login → get JWT token
POST /auth/login
{ "email": "admin@tracko.com", "password": "admin123" }

# Use token in all requests
Authorization: Bearer <your_token>
```

## 🤖 AI Features
```bash
# Natural language tracking
POST /api/ai/track
{ "query": "Where is my package TRK-20260603-6d884089?" }

# Business summary report
GET /api/ai/summary
```

## 🔄 Shipment Status Flow
CREATED → IN_TRANSIT → OUT_FOR_DELIVERY → DELIVERED

↘ CANCELLED
## 🧪 Tests
```bash
mvn test
# 21 tests — CustomerService, ShipmentService, DeliveryAgentService
```

## 📁 Structure
src/main/java/com/logistics/courier_tracking/

├── controller/    # REST endpoints

├── service/       # Business logic

├── repository/    # Database operations

├── entity/        # JPA entities

├── security/      # JWT filter & config

├── exception/     # Global error handling

├── dto/           # Response wrappers

└── enums/         # Status types
## 👨‍💻 Author
**Subham Nayak** • [GitHub](https://github.com/SubhamNayak17)