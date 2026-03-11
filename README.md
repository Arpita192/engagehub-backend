# User Engagement Platform

## Overview

The User Engagement Platform is a Spring Boot based backend application designed to manage user registration, authentication, consent management, activity tracking, dialer validation, and notifications.

The system ensures that user interactions comply with consent rules before allowing outbound dialer operations. It also caches frequently accessed data using Redis to improve performance.

---

# Tech Stack

* Java 17
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* Redis (Caching + Dialer storage)
* Kafka (Event publishing)
* PostgreSQL / MySQL
* Maven
* Lombok

---

# Key Features

## 1. User Registration

Users can register using name, email, mobile number, and password.

During registration:

* User is stored in the database
* Password is encrypted
* Default role and status are assigned
* Consent API is triggered
* Kafka event is produced

---

## 2. Authentication

JWT based authentication is implemented.

Features:

* Access Token generation
* Refresh Token support
* Secure login
* Token validation

The JWT token stores the user email which is later used to identify the user from the SecurityContext.

---

## 3. User Consent Management

Consent determines whether the platform can contact a user.

Two types of consent exist:

### Explicit Consent

* Provided directly by the user
* Stored in the user consent table
* Valid for 7 days

### Implicit Consent

* Generated from user activity
* Stored in the implicit consent table
* Valid for 7 days from the activity date

---

# Dialer Service

The Dialer Service determines whether a dialer call can be executed.

### Dialer Validation Logic

Steps performed:

1. Extract user email from JWT token.
2. Fetch the user from the database.
3. Fetch the requested activity.
4. Check explicit consent validity.
5. If explicit consent is not valid, check implicit consent.
6. Determine final status (VALID or INVALID).
7. Store the result in Redis.

### Redis Storage

Dialer results are cached in Redis using a structured key:

```
dialer:user:{userId}:activity:{activityId}
```

Example:

```
dialer:user:35:activity:1
```

Stored object contains:

* activityId
* consentType (EXPLICIT / IMPLICIT / null)
* status (VALID / INVALID)
* mobile number

---

# Notification Service

A Notification Service is implemented to store user notifications.

Features:

* Notification events are saved in a database table
* Can be used for tracking communication history
* Allows future integrations with messaging or alert systems

---

# Redis Caching

Redis is used for:

1. User caching
2. Dialer validation storage

Benefits:

* Faster data access
* Reduced database load
* Improved system performance

---

# Kafka Integration

Kafka is used for event driven communication.

Events are produced during:

* User registration
* User login

This enables asynchronous processing for analytics or downstream services.

---

# API Testing

APIs can be tested using Postman.

Example Dialer Request

Header:

Authorization: Bearer <JWT_TOKEN>

Body:

```
{
  "activityName": "LoanCall"
}
```

Response Example

```
{
  "status": "success",
  "message": "successful dialer operation"
}
```

---

# Project Structure

```
controller
service
service/implementation
repository
entity
dto
config
security
kafka
exception
```

---

# Running the Project

1. Clone the repository

```
git clone <repository-url>
```

2. Navigate to the project

```
cd user-engagement-platform
```

3. Run the application

```
mvn spring-boot:run
```

---

# Future Improvements

* Add notification delivery system
* Implement monitoring and logging
* Add API rate limiting
* Improve Redis TTL management

---

# Author

Arpita Mishra
