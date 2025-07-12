# 🎬 Cinema Web App – Microservice Showcase

Welcome to my **Cinema Web App**, a full-stack **microservices-based system** built with Spring Boot and Docker.  
This is a **showcase project** created to demonstrate my knowledge and skills in designing and building microservice architectures.

> ⚠️ **Disclaimer:** This project is meant purely for educational and demonstration purposes.  
> All API keys, secrets, and environment variables are **intentionally exposed** within the code to simplify the deployment and highlight architectural decisions.  
> ⚠️ **Do not use this project in production as-is.**

---

## 🎯 Project Goals

The primary aim of this project is to showcase:

- 🔁 **Microservice communication** (RESTful APIs, internal service interaction)
- 🔐 **JWT-based authentication**
- 🧩 **Service separation and responsibilities**
- 🛠️ **Code organization and shared utilities**
- 🐳 **Docker-based containerization**

---

## 🧱 Microservices Overview

The application is composed of **4 main microservices** and **1 shared utility module**:

### 1️⃣ **User Management Service**
- Handles user registration, login, and authentication.
- Implements **JWT-based security** for protected endpoints.
- Role-based access control can be added if needed.

### 2️⃣ **Cinema Service**
- Manages cinema halls, movies, showtimes, and seat arrangements.
- Responsible for creating and updating show details and availability.

### 3️⃣ **Payment Service**
- Handles client payments for booking tickets.
- Can be extended with integration to external payment gateways.

### 4️⃣ **Notification Service**
- Sends notifications (e.g., confirmation messages) to users after key actions like booking or payment.
- Simple messaging simulation (email/SMS logic can be plugged in).

### 🧰 **Utils Module**
- A shared Java module that contains common DTOs, enums, and utility classes.
- Used across all services to ensure consistency and avoid duplication.
