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

🏟️ Cinema Hall Management
This service provides endpoints for managing cinema halls — including creation, update, and deletion.
<img width="1494" height="322" alt="image" src="https://github.com/user-attachments/assets/32af640a-b7c5-44a8-8ca0-75199cb74681" />

🔐 Access Control
To create, update, or delete a hall, the user must have the ADD_HALL permission.

This permission is granted to users with the Admin role.

Ordinary users (group_id = 2) do not have access to these endpoints.

✅ This ensures only authorized administrators can manage cinema infrastructure, while regular users can only view available halls and shows.

🎫 Seat Management
This service also includes endpoints to add, update, or manage seat configurations for each show or hall.
<img width="1506" height="559" alt="image" src="https://github.com/user-attachments/assets/208245af-4abd-4479-82b4-43ccbab83554" />

🔐 Access Control
To add or modify seats, the user must have the ADD_SEAT permission.

This permission is granted to users with the Admin role.

Regular users do not have access to these endpoints and can only view seat availability when booking tickets.

✅ This ensures that only administrators can manage the seating layout, while users interact only with available seat data during booking.


🎬 Movie Management
The Cinema Service includes endpoints for adding, updating, and deleting movies shown in the cinema.

<img width="1485" height="372" alt="image" src="https://github.com/user-attachments/assets/82ec2e25-b0bc-4efd-9485-fac8dba5d43d" />

🔐 Access Control
To add, update, or delete a movie, the user must have the ADD_MOVIE permission.

This permission is available to users with the Admin role.

Regular users do not have permission to modify movie data — they can only view available movies for booking purposes.

⭐ Movie Ratings
Each movie has a rating field that is automatically updated every 12 hours. A scheduled background job fetches the latest rating from the OMDb API and updates the movie data accordingly.

📅 Movie Show Management
The Cinema Service also provides endpoints to create, update, and delete movie showtimes.

<img width="1512" height="287" alt="image" src="https://github.com/user-attachments/assets/28db8644-f6bd-4774-9525-c33c5cb86413" />

🔐 Access Control
To add or modify movie shows, the user must have the ADD_MOVIE_SHOW permission.

This permission is assigned to users with the Admin role.  

There is also shceduled job that update movieshow if its finished.

🎟️ Seat Booking Management
The Seat Booking Controller manages the booking lifecycle for seats within movie shows.

<img width="1477" height="277" alt="image" src="https://github.com/user-attachments/assets/b4e59d07-4154-412d-bfaf-8d0c03df97d0" />

🔐 Access Control
All endpoints except for checking free seats require the EDIT_BOOKING permission.

Only authorized users (with this permission) can create, update, or cancel bookings.

The pending endpoint is publicly accessible (no permission needed) and is called by the frontend when a user wants to start booking seats for a specific movie show.

⏳ Booking Flow & Concurrency Handling
When a user requests free seats (pending), the system:

Locks those seats temporarily so other users cannot book them simultaneously.

The booking remains in a pending state until the payment process completes.

If the payment fails or the user aborts the booking:

A background job automatically reverts seats from pending back to available/unpaid.

Users can always view available seats for a given movie show (movieShowId).

💰 Pricing & Payment Integration (Future Improvement)
Currently, seat prices are returned as a long datatype (simple value).

Ideally, the price should be a separate entity, returning an ID and amount.

This ID would then be passed to the Payment Service to ensure consistent and reliable payment processing.

✅ This mechanism helps prevent double booking and keeps seat availability accurate during concurrent booking attempts.




### 3️⃣ **Payment Service**
- Handles client payments for booking tickets.
- Can be extended with integration to external payment gateways.
- 
💳 Stripe Payment Integration (Showcase)
For this demo, the Payment Service uses a simple Stripe API integration with two main endpoints.

<img width="1502" height="107" alt="image" src="https://github.com/user-attachments/assets/2c1297c8-17da-485b-9cc5-7225a165ae58" />

This endpoint initiates a Stripe checkout session.

seatBookingId: The ID of the pending seat booking generated by the Cinema Service.

amount: The total amount to pay —Currently, this is a simple long value generated by the Cinema Service for the showcase.Ideally, this should be the ID of an entity representing the calculated price sum for booked seats.

currency: A free-to-choose string (e.g., "USD", "EUR").For this demo, currency choice is flexible for testing purposes; a real-world app would fix or restrict this to supported currencies.

Payment Flow:
After sending the checkout request, the service returns a Stripe-generated payment URL where the user completes the payment.

📸 [Add screenshot of the Stripe checkout session URL response here]

Once the payment is completed, Stripe triggers a webhook back to the Payment Service.

The webhook endpoint: /external/v1/webhook/stripe

Validates the payment success. Saves payment details in the payment database. Updates the seat booking status in the Cinema Service accordingly. KEEP IN MIND THIS SERVICE MUST HAVE GOOD FAILED CASE HANDLING BUT I FULLY DEVELOPED SUCCESSEFUL PAYMENT CASE!


4️⃣ Notification Service
Sends notifications (e.g., confirmation messages) to users after key actions like booking or payment.

Implements a Kafka consumer that listens for messages sent by other services (e.g., Payment Service).

When a payment is successfully completed:

The service consumes the message.

Saves a notification record using a unique template code.

Sends the notification to the user who made the payment (simulated email/SMS).

Exposes simple endpoints for managing or viewing notifications (for demo/testing purposes).

⚠️ Important: Notifications are sent only to users in the USER group (group_id = 2), since only users—not admins—purchase tickets.

### 🧰 **Utils Module**
- A shared Java module that contains common DTOs, enums, and utility classes.
- Used across all services to ensure consistency and avoid duplication.
- See all in source code.


🚀 Final Demo – End-to-End Ticket Booking Flow
This section demonstrates a full cycle: selecting seats → making payment → receiving notification.


🪑 Step 1: Pending Seat Booking
Endpoint: POST localhost:8080/external/v1/seat-booking/pending  X-User-Id: 11

{
  "seatIds": [21, 22, 23],
  "movieShowId": 1
}

This triggers the Cinema Service, which: Validates the seats and show. Creates a pending booking in the database. Returns a seatBookingId (e.g., 1452).

<img width="1304" height="42" alt="image" src="https://github.com/user-attachments/assets/02b26850-31d7-4461-83c9-a094472328b8" />
As you can see it on pending status and does not have unique code and does not have payment id 

💳 Step 2: Create Payment Session

POST localhost:4242/external/v1/payments/checkout-session X-User-Id: 11

{
  "seatBookingId": 1453,
  "amount": 345,
  "currency": "USD"
}

Creates a Stripe checkout session. Returns a payment URL. As i said before we use seatBookingId which we temproaly triggered amount must be id but its test case and curreny also

<img width="1297" height="96" alt="image" src="https://github.com/user-attachments/assets/6ee73628-8269-436a-b4bd-7c0bf3490c25" />

Then comes stripe payment :

<img width="465" height="714" alt="image" src="https://github.com/user-attachments/assets/1466eec4-284e-44b8-844f-f293ef9b58c1" />

use Test Card : 4242 4242 4242 4242

🔄 Step 3: Post-Payment Automation
Stripe triggers a webhook automatically to the Payment Service.

The Payment Service: Marks the payment as successful in its DB. Sends a Kafka message to the Cinema Service.

The Cinema Service: Updates the corresponding seat booking to PAID. Assigns the paymentId to the booking.

<img width="1313" height="48" alt="image" src="https://github.com/user-attachments/assets/75281fbf-404a-45f8-b5bf-77bedf2cc637" />

and payment id : <img width="1519" height="12" alt="image" src="https://github.com/user-attachments/assets/d343ae2d-a7fd-439e-bbe4-4c4cfbd883e0" />

📬 Step 4: Notification Delivery
The Notification Service receives the Kafka message.

It: Creates a notification entry for the user with ID 11. Uses a unique template code for consistency. (Simulated) sends a confirmation message.

<img width="1355" height="19" alt="image" src="https://github.com/user-attachments/assets/2ef157b5-3be5-4148-b604-e5ff51f1c748" />


1️⃣ Universal steps for every service (e.g., User Management (UM), Cinema Service, Payment Service, Notification Service)
Each service has:

A Dockerfile .env file for configuration Maven project to build the JAR

From the root folder of each service, run: mvn clean package -DskipTests This will generate the .jar file in the target/ directory. From the same service directory where the Dockerfile and target/ folder are, run: docker build -t <service-name> . 

Replace <service-name> with your desired image name, for example: ticket-um, cinema-service, payment-service, notification-service

Run the container exposing the service port and load environment variables from the .env file: docker run -d -p <host-port>:<container-port> --name <container-name> --env-file .env <service-name>

Example for User Management: docker run -d -p 9090:9090 --name ticket-um --env-file .env ticket-um

In the root folder of your project, you already have a docker-compose.yml for Kafka and your Oracle DB. To start Kafka and DB containers, run: docker-compose up -d

3️⃣ Summary of ports
Service	Port (Container)	Port (Host)	Image Name
User Management	9090	9090	ticket-um
Cinema Service	8080	8080	cinema-service
Payment Service	4242	4242	payment-service
Notification Service	4040	4040	notification-service



# 🚨 IMPORTANT NOTE 🚨

**THIS MICROSERVICE PROJECT IS A FULLY FUNCTIONAL SHOWCASE DEMONSTRATION!**

---

## THE MAIN FOCUS HERE IS THE **CODE QUALITY AND BUSINESS LOGIC** —  
HOW THE MICROSERVICES ARE FULLY DEVELOPED, CLEANLY STRUCTURED,  
AND COMMUNICATE VIA KAFKA AND REST.

---

Docker images and `.env` files are provided to simplify deployment and testing, but  
they are just a supporting part of the project.

---

### ⚠️ THIS IS A BASIC SHOWCASE DESIGNED TO DEMONSTRATE INDUSTRY-LEVEL MICROSERVICE PRINCIPLES,  
CLEAN ARCHITECTURE, AND MAINTAINABLE CODE — NOT A PRODUCTION-READY SOLUTION.



