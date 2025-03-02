[![Test with Testcontainers](https://github.com/AriiSib/task-tracker/actions/workflows/ci.yml/badge.svg)](https://github.com/AriiSib/task-tracker/actions/workflows/ci.yml)

# Task Tracker

Task Tracker is a web application for task management that helps users efficiently organize their daily activities. The
application supports task creation, editing, and deletion, as well as organizing tasks using a Kanban board.

## Application Features

### Task Management

- **Add Task**: Create new tasks by specifying a title, description, due date, and status.
- **Edit Task**: Modify existing tasks, including their title, description, and due date.
- **Delete Task**: Remove tasks from the list.
- **Add Tags**: Add a tags to each task to help characterise it.

### User Management

- **Registration**: Register new users by providing first name, last name, username, and password.
- **Login**: Log in to the system for already registered users.

## Technical Features

- **Testing**: Use JUnit and Testcontainers to test services interacting with the database.
- **Logging**: Implement a logging system using Logback.
- **Deployment**: Integrate with Docker for convenient application deployment.
- **CI/CD**: Automatic testing and building using [GitHub Actions](https://github.com/AriiSib/task-tracker/actions).

## Stack

- **Java 21**
- **Tomcat 10.1.24**
- **Hibernate 6**
- **Liquibase**
- **PostgreSQL 16**
- **JSP/JSTL**
- **Jakarta EE 10**
- **MapStruct**
- **Lombok**
- **Jackson**
- **JUnit 5**
- **Testcontainers**
- **Logback**
- **Maven 3.9.6**
- **Docker**
- **GitHub Actions**

# Installation

### Option 1: Using Docker

1. Import the project into your IDE:
   ```bash
   https://github.com/AriiSib/task-tracker.git
   ```

2. Build the WAR file:
   ```bash
   mvn clean install
   ```

3. Run the containers:
   ```bash
   docker-compose up
   ```

4. The application will be available at: http://localhost:8080/task-tracker

### Option 2: Local Deployment

1. Import the project into your IDE:
   ```bash
   https://github.com/AriiSib/task-tracker.git
   ```

2. Generate the WAR file:
   ```bash
   mvn clean install
   ```

3. Configure Tomcat in your IDE and specify the project path:
    - Application context: /task-tracker
    - Port: 8080

4. Connect PostgreSQL like this, for example:
   ```bash
   docker run --name test_tracker_db -e POSTGRES_DB=postgres -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres:16-alpine
   ```

5. To perform database migrations, run:
   ```bash
   mvn liquibase:update
   ```

6. The application will be available at: http://localhost:8080/task-tracker