# careconnect-backend
Backend service for CareConnect – a healthcare platform for medical records, appointments, and healthcare discovery.
# CareConnect

CareConnect is a healthcare product that securely centralizes patient medical history and assists users in discovering nearby healthcare services and booking appointments.

⚠️ CareConnect does not provide medical diagnosis or emergency dispatch. It focuses on secure data storage, discovery, and appointment coordination.

---

## Problem Statement
Medical records are often scattered across hospitals, making it difficult for patients and doctors to access complete medical history quickly. CareConnect solves this by acting as a single source of truth for patient health records and healthcare access.

---

## Core Features (Phase 1)
- Secure signup and login (JWT-based)
- Patient profile and medical history
- Doctor access to authorized patient records
- Appointment booking and history
- Role-based access control

---

## Tech Stack
- Java 17
- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate
- MySQL / PostgreSQL
- REST APIs
- Swagger (OpenAPI)
- Docker

---

## Architecture
- Monolithic backend (designed for future microservice decomposition)
- Modular services:
  - Authentication & User Management
  - Medical Records
  - Appointment Management
  - Healthcare Discovery

Detailed HLD, LLD, API design, and user flows are documented in `/docs`.

---

## Project Phases
- Phase 0: Prototype & design
- Phase 1: Core backend & authentication
- Phase 2: Recommendations & emergency discovery
- Phase 3: System design & scalability

---

## Getting Started
Instructions will be added once Phase 1 backend is ready.

---

## Author
Shweta Chandankhede  
Backend Engineer (Java & Spring Boot)
