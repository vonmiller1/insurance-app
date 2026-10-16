# Insurance Management System

A web application for managing insurance operations across administrators, employees, agents, and customers. The system combines an Angular client with a Spring Boot REST API and MySQL persistence.

## Features

- Role-based access for administrators, employees, agents, and customers
- Customer, employee, and agent administration
- Insurance product and plan management
- Customer policy purchases and claims
- Payment processing and transaction records
- Customer document uploads
- Customer and agent reporting
- Account profiles, authentication, and password reset workflows

Payment processing uses Razorpay, and document uploads use Cloudinary. These integrations require credentials and accounts configured outside the repository.

## Technology

| Layer | Technologies |
| --- | --- |
| Frontend | Angular 19, TypeScript, Bootstrap |
| Backend | Java 17, Spring Boot 3.4, Spring Security, Spring Data JPA |
| Database | MySQL |
| Integrations | Razorpay, Cloudinary, Spring Mail |

## Repository layout

```text
backend/
  insurance-management/   Spring Boot API and Maven wrapper
frontend/
  insurance-mgmt/         Angular application
```

## Prerequisites

- Java 17
- Node.js and npm
- MySQL
- Razorpay and Cloudinary credentials for payment and document-upload features
- SMTP credentials if email-based workflows are required

## Configuration

Create a MySQL database for the application, then configure the backend's local Spring Boot settings in `backend/insurance-management/src/main/resources/application.properties`. This file is not included in the repository. At minimum, configure the MySQL datasource (`spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password`), along with the `cloudinary.cloud-name`, `cloudinary.api-key`, `cloudinary.api-secret`, `razorpay.key-id`, and `razorpay.key-secret` properties.

Configure SMTP settings with the `spring.mail.*` properties if you use email workflows. Keep credentials in local configuration or environment variables; do not commit secrets.

The frontend currently sends API requests to `http://localhost:8080`. Run the backend on that address when using the frontend locally.

## Run locally

### 1. Start the backend

From the repository root:

```powershell
cd backend\insurance-management
.\mvnw.cmd spring-boot:run
```

The API uses Spring Boot's default port, `8080`.

### 2. Start the frontend

In a second terminal, from the repository root:

```powershell
cd frontend\insurance-mgmt
npm install
npm start
```

Open [http://localhost:4200](http://localhost:4200) in a browser.

## Build and test

Build and test the backend:

```powershell
cd backend\insurance-management
.\mvnw.cmd verify
```

Build the frontend:

```powershell
cd frontend\insurance-mgmt
npm run build
```

Run frontend unit tests:

```powershell
npm test
```

## Security

Use test credentials for third-party services during development. Never commit database passwords, API keys, SMTP credentials, or other secrets to source control.
