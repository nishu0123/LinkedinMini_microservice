# LinkedIn Mini - Microservices Backend

A production-inspired LinkedIn backend built using Java and Spring Boot following microservices architecture.

The project focuses on backend system design, distributed systems, event-driven architecture, and clean code principles.

---

## Tech Stack

* Java 17/21
* Spring Boot
* Spring Security
* Spring Cloud
* Spring Cloud Gateway
* Spring Cloud OpenFeign
* Spring Cloud Netflix Eureka
* Apache Kafka
* Neo4j
* PostgreSQL
* Docker
* JWT Authentication
* Cloudinary / google could(planned)
* Mailtrap (Development Email)

---

## Microservices

* User Service
* Post Service
* Notification Service
* Uploader Service
* Connection Service
* API Gateway
* Eureka Discovery Server
* Common Contracts Module 

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
* Design Patterns
* Understanding the system end to end  in depth Designing -> development -> testing -> deployment -> maintainance 
