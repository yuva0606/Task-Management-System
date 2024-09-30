# Task Management System

## Overview
The **Task Management System** is a backend application built using Spring Boot, MySQL, Spring Security, and JWT for secure authentication and authorization. It allows users to register, log in, and manage their tasks through CRUD operations. Each user can create, view, update, and delete their tasks while ensuring that only authorized users can access their own tasks. The project implements role-based access control, where admins have full control over all user tasks. It efficiently handles user sessions using JWT tokens and follows best practices for secure and scalable backend development.

## Features
- **User Registration & Login**: Secure user authentication with JWT.
- **Role-Based Authorization**: Users can manage only their own tasks, and admins have full access to all tasks.
- **CRUD Operations**: Create, Read, Update, and Delete tasks for authenticated users.
- **JWT Authentication**: Token-based authentication for session management.
- **MySQL Integration**: Persistent data storage using MySQL.

## Technologies Used
- **Backend**: Spring Boot
- **Security**: Spring Security, JWT
- **Database**: MySQL
- **Build Tool**: Maven

## Endpoints

### User Authentication
- `POST /register` – Register a new user.
- `POST /login` – Authenticate user and generate a JWT.
- `POST /jwt` – Create a new JWT using a refresh token.
- `POST /changePassword` – Change existing password.

### Task Management
- `GET /tasks` – Get all tasks for the logged-in user.
- `GET /tasks/pagination` – Get all tasks for the logged-in user with pagination.
- `GET /tasks/{id}` – Get a specific task by ID (only if the task belongs to the logged-in user).
- `POST /tasks` – Create a new task.
- `PUT /tasks` – Update an existing task (only if the task belongs to the logged-in user).
- `DELETE /tasks/{id}` – Delete a task (only if the task belongs to the logged-in user).

### Admin Endpoints
- `GET /admin/tasks` – Get all tasks (Admin only).
- `DELETE /admin/tasks/{id}` – Delete any task (Admin only).

### Forgot Password Endpoints
- `POST /forgotPassword/email/{email}` - verify the email and sends forgot password otp.
- `POST /forgotPassword/otp` - verify the otp.
- `POST /forgotPassword/updatePassword` - change the forgotten password.

## Getting Started

### Prerequisites
To run this project, you need to have the following installed:
- **Java 17+**
- **Maven**
- **MySQL**

### Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yuva0606/Task-Management-System.git
   cd task-management-system
   ```

2. **Set up the database**:
   - Create a new MySQL database with the name `task_management`.
   - Update the `application.properties` file with your database configuration:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/task_management
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     ```

3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

4. **Access the API**:
   The API will be available at `http://localhost:8080`.

### Example Request 
Sample JSON for Creating a Task:
```json
{
    "taskId": "Doesn't matter, pass any ID (ID is auto-generated)",
    "title": "Complete Documentation",
    "description": "Finish the project documentation by Friday",
    "dueDate": "2024-09-30",
    "priority": "high"
}
```


## Author
- **Yuva Shankar**
