# Expense Tracker

A simple Java + Gradle application for managing personal expenses(currently for adding budgets, but will be continude to bve a full expense tracker).  
You can add, update, list, and remove budget—all stored in memory or persisted depending on configuration.

This project demonstrates clean layering (Controller → Service → Repository), use of Gradle build tooling, and IntelliJ-friendly setup.  
It can be used as a base project for practicing Java development, Gradle builds, and testing.

---

## Table of Contents

1. [Description](#description)  
2. [Prerequisites](#prerequisites)  
3. [Getting Started](#getting-started)  
   - [Clone the repository](#clone-the-repository)  
   - [Build & Run (IntelliJ IDEA)](#build--run-intellij-idea)  
4. [Features](#features)  
5. [Configuration](#configuration)  
6. [Project Structure](#project-structure)  
7. [Testing](#testing)  

---

## Description

This example implements basic **CRUD operations** for an **Expense Tracker**:

- Find a budget by ID  
- List all budgets  
- Add a new budget  
- Update a budget  
- Delete a budget  

It provides a simple foundation for learning how to structure a Java project with Gradle, keep business logic separate from persistence, and write maintainable code.

---

## Prerequisites

- Java 17+ (recommended)  
- Gradle (or use the included `gradlew`)  
- IntelliJ IDEA (or any Java IDE of choice)

---

## Getting Started

### Clone the repository

Via IntelliJ IDEA:

Via IntelliJ IDEA

1. Open IntelliJ IDEA.
2. From the Welcome screen (or File menu), choose **Get from Version Control…**
3. In the dialog that appears, paste `https://github.com/LauraLiisParm/expense-tracker.git` into the **URL** field.
4. Select your desired local directory and click **Clone**.
5. Once the clone completes, IntelliJ will open the project—allow it to import/reload the Gradle settings.

Via command line:

```bash
git clone https://github.com/LauraLiisParm/expense-tracker.git
cd expense-tracker
```


### Build & Run (IntelliJ IDEA)

1. **Open the project**
    - In IntelliJ IDEA, select **File ▸ Open…** and choose the project’s root folder (containing `build.gradle`).
    - IntelliJ will automatically reload the Gradle project. If you encounter any issues, click the “Refresh” icon in the Gradle tool window to force a manual reload.

2. **Run the application**
    - In the **Project** tool window, navigate to `src/main/java/eu/itcrafters/petshop/PetshopApplication.java`.
    - Click the green ▶︎ icon next to the `main` method, or right-click the file and choose **Run 'PetshopApplication'**.

3. **Verify startup**
    - The console should show Spring Boot starting on port 8080.
    - Open your browser to Swagger UI page `http://localhost:8080/swagger-ui/index.html` to confirm the server is running.

4. **Stop the server**
    - Click the red ■ icon in the Run tool window, or press **Ctrl + F2** (Windows/Linux) or **⌘ + F2** (macOS).
  


## Swagger UI

After startup, you can browse your OpenAPI docs at: `http://localhost:8080/swagger-ui/index.html`

---
## Configuration

All runtime settings live in `src/main/resources/application.properties`.

---

## Database Initialization

On startup, Spring Boot will automatically run any `schema.sql` and `data.sql` files found on the classpath (i.e. in `src/main/resources`) to build and seed your HSQLDB schema.

### schema.sql

- Defines your tables, constraints, indexes, etc.

### data.sql

- Populates your newly created tables with initial or sample data.
- Executed immediately after `schema.sql`, so all referenced tables already exist.
- Use it to insert lookup values, demo rows, or any seed data your application needs on startup.

## Available Endpoints

| Method | Path               | Description                              |
| ------ | ------------------ | ---------------------------------------- |
| GET    | `/budget/{petId}`     | Retrieve a single budget by its ID          |
| GET    | `/budgetss`            | List all budgets                     |
| POST   | `/budget`             | Create a new budget or add to existing one  |
| PUT    | `/budget/{petId}`     | Update an existing budget’s details         |
| DELETE | `/budget/{petId}`     | Delete a budget by its ID                   |

---

## Project Structure

```plaintext
## Project Structure

```plaintext
expense-tracker/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/expense-tracker/
│   │   │       ├── controller/          # REST controllers & DTOs
│   │   │       ├── service/             # Business logic
│   │   │       ├── repository/          # Persistence layer
│   │   │       ├── model/               # Domain models (Expense, Category, etc.)
│   │   │       ├── mapper/              # Converters / DTO ↔ entity mappers
│   │   │       └── ExpenseTrackerApplication.java
│   │   └── resources/
│   │       ├── application.properties   # Spring Boot configuration
│   │       ├── schema.sql               # DDL for HSQLDB schema
│   │       └── data.sql                 # Initial seed data
│   └── test/
│       └── java/
│           └── com/example/expense-tracker/
│               └── ExpenseTrackerApplicationTests.java
├── build.gradle                         # Gradle build script
├── settings.gradle                      # Gradle settings
├── gradlew / gradlew.bat                # Gradle wrapper scripts
└── .gitignore                           # Git ignore file
```

---



