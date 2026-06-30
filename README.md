# LinkedIn Mini - Microservices Backend

A production-inspired LinkedIn backend built using Java and Spring Boot following microservices architecture.

The project focuses on backend system design, distributed systems, event-driven architecture, and clean code principles.

---

## Tech Stack

Backend
- Java 17/21
- Spring Boot

Security
- Spring Security
- JWT

Microservices
- Eureka
- OpenFeign
- Gateway

Messaging
- Kafka

Databases
- PostgreSQL
- Neo4j

Storage
- Cloudinary

Containerization
- Docker

---

## Microservices

### User Service

Owns user identity and authentication. It manages user profiles, JWT authentication, refresh tokens, and exposes internal APIs that allow other microservices to retrieve user information.

### Post Service

Owns all post-related operations. It coordinates media uploads through the Uploader Service, persists posts, and publishes Kafka events whenever a new post is created.

### Notification Service

Consumes Kafka events and orchestrates notification delivery. It fetches the target audience from the Connection Service and delegates notification delivery to the appropriate notification strategy (Email, SMS, Push, etc.) using the Strategy Pattern.

### Uploader Service

Provides a dedicated media management service. It handles file uploads, integrates with Cloudinary, and returns publicly accessible URLs to other microservices.

### Connection Service

Owns the social graph of the application. It manages follower/following relationships and provides connection data required by services such as the Notification Service.

### API Gateway

Acts as the single entry point for all client requests. It performs request routing, authentication, and forwards requests to the appropriate microservice.

### Eureka Discovery Server

Provides service discovery for the microservices ecosystem. It enables services to register themselves and discover other services dynamically without hardcoding host addresses.

### Common Contracts Module

A shared library used across all microservices. It contains common DTOs, Kafka event models, enums, constants, and other shared contracts to ensure consistent communication and eliminate code duplication.

---

## Project Structure

```text
                          Client
                             │
                             ▼
                     API Gateway
                             │
      ----------------------------------------------------
      │          │           │            │              │
      ▼          ▼           ▼            ▼              ▼
 User Service  Post      Connection   Notification   Uploader
               Service      Service       Service      Service
                  │                           ▲
                  │                           │
                  └──────── Kafka ────────────┘

                    Eureka Discovery Server
                  (Service Registration)

                 Common Contracts Module
        (Shared DTOs, Events, Enums, Models)

```
## Crete Post / Create post Notification Flow

```text
User creates a post
        │
        ▼
API Gateway
        │
        ▼
Post Service
        │
        ├─────────────────────────────► Uploader Service
        │                                │
        │                                ▼
        │                         Upload Image
        │
        ▼
Save Post
        │
        ▼
Publish Kafka Event
        │
        ▼
==================== Apache Kafka ====================
        │
        ▼
Notification Service (Consumer)
        │
        ▼
Receive PostCreatedEvent
        │
        ▼
Call Connection Service
        │
        ▼
Fetch Followers
        │
        ▼
For each follower
        │
        ▼
Create NotificationRequest
        │
        ▼
NotificationStrategyOrchestrator
        │
        ├────────► Email Strategy
        │
        ├────────► SMS Strategy
        │
        └────────► Push Strategy
                     (Future)
        │
        ▼
Notification Delivered
```
---

## Features

### Authentication

* User Registration
* Login using JWT
* Refresh Token Authentication
* Secure Logout
* Request Interceptor for User Context

### User

* User Profile
* Follow / Unfollow
* Followers & Following Management

### Posts

* Create Post
* Delete Post
* Upload Images
* Cloudinary Integration
* User Feed (planned)
* Post Like/Dislike

### Notifications

* Event-driven notification system using Kafka
* Email Notifications
* Strategy Pattern based notification architecture
* Easily extensible for SMS, WhatsApp, Push Notifications

### Architecture

* Microservices
* Event Driven Communication using Kafka
* Service Discovery using Eureka
* API Gateway
* Feign Client Communication
* Shared DTOs using Common Contracts Module

---

## Design Principles

* SOLID Principles
* Strategy Design Pattern
* Constructor Dependency Injection
* Low Coupling
* High Cohesion
* Event-Driven Architecture

---


## Current Progress

* JWT Authentication
* Refresh Token Flow
* Kafka Integration
* Event-driven Notification System
* Email Notification
* Strategy Pattern Refactoring
* Feign Client Communication
* Eureka Service Discovery
* Cloudinary Integration

---

## Planned Features

* Redis Caching
* Notification Preferences
* Push Notifications
* Dead Letter Topic (DLT)
* Retry Mechanism
* Rate Limiting
* Recommendation Engine
* Real-time Notifications (WebSocket)
* Analytics

---

## Learning Objectives

This project is being developed to gain practical experience in:

* Backend System Design
* Microservices
* Distributed Systems
* Kafka
* Spring Boot Ecosystem
* Design patterns and SOLID principles
* End-to-end software development lifecycle from design to deployment

## Challenges & Learnings

During the development of this project I encountered and solved several production-like problems:

- Implemented JWT authentication across multiple services.
- Designed event-driven communication using Kafka.
- Refactored the notification module using the Strategy Pattern following the Open/Closed Principle.
- Shared DTOs and Kafka events through a Common Contracts module.
- Debugged Feign communication and service discovery issues.
- Integrated asynchronous email notifications.
- Managed Kafka consumer replay and event processing behaviour.