# Insurance Management System

An insurance management application with a Java and Spring Boot backend and an Angular frontend. It supports insurance workflows such as user authentication, policy management, claims, payments, reporting, and document handling.

## Project structure

```text
backend/
  insurance-management/   Spring Boot application
frontend/
  insurance-mgmt/         Angular application
```

## Technology stack

- **Backend:** Java 17, Spring Boot 3, Spring Security, Spring Data JPA, Maven
- **Frontend:** Angular 19, TypeScript, Bootstrap
- **Database:** MySQL
- **Integrations:** Razorpay payments, Cloudinary storage, and email

## Prerequisites

- Java 17
- Node.js and npm
- MySQL

Configure the backend's database and any required integration credentials in your local Spring configuration before starting the application. Do not commit credentials to the repository.

## Run the backend

From the repository root, run:

```powershell
cd backend\insurance-management
.\mvnw.cmd spring-boot:run
```

The backend uses port `8080` by default.

## Run the frontend

Open a separate terminal and run:

```powershell
cd frontend\insurance-mgmt
npm install
npm start
```

The Angular development server is available at `http://localhost:4200`.

## Build and test

Build the backend:

```powershell
cd backend\insurance-management
.\mvnw.cmd package
```

Build or test the frontend:

```powershell
cd frontend\insurance-mgmt
npm run build
npm test
```
