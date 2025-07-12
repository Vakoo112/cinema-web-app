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
- On successful login, a **JWT token** is generated and returned to the client.
- All other services are built to rely on this token — **they expect the `userId` to be extracted from it beforehand**.
- In a real-world system, this extraction would typically be done via an **API Gateway** or **auth middleware**.
- 🔒 **In this demo**, no gateway is implemented to keep the architecture flexible and focused on microservice communication.
- Instead, each secured endpoint directly **expects a valid `userId`** (passed via header or param), simulating post-token-validation behavior.

<img width="1534" height="545" alt="image" src="https://github.com/user-attachments/assets/e8bd1418-346e-47bb-8e80-c5971deb5cb5" />


Each role is associated with a group that defines its access level:
Permissions are enforced at the endpoint level, ensuring that users can only access resources their permits.
<img width="815" height="292" alt="image" src="https://github.com/user-attachments/assets/d5f7c475-1f1e-45c7-8664-4c4f41110b07" />


🛡️ Admin Group (group_id = 1)

Can access all endpoints across all services.

Permissions include create, update, delete, and full management of users, movies, shows, seats, and payments.

👤 User Group (group_id = 2)

Has read-only and booking permissions.

Can view available movies, showtimes, and seat arrangements.

Can book tickets but cannot modify or delete data.

<img width="657" height="98" alt="image" src="https://github.com/user-attachments/assets/7b74e1b4-4c5f-45a4-adda-573311aadfa5" />

ROLE CONTROLLER 

<img width="1489" height="701" alt="image" src="https://github.com/user-attachments/assets/699caa81-2b25-4082-ae06-29e49c73165f" />

🔐 Session Controller

Handles login and logout.

<img width="1477" height="131" alt="image" src="https://github.com/user-attachments/assets/52904f3f-6917-4cb2-a39b-214a391c6fca" />

On successful login:A JWT token is generated and sent back to the frontend. This token must be included in future requests using the Authorization: Bearer <token> header.Since there's no API Gateway in this demo:

The frontend is responsible for extracting the user ID from the token and sending it as X-User-Id in requests.On logout: The token is deleted on the client side (or invalidated if tracked server-side).






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


