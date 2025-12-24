# ASSIGNMENT: EMPLOYEE PERFORMANCE MANAGEMENT SYSTEM

## Tech Stack

-   Java 21
-   Spring Boot
-   Spring Data JPA
-   MySQL Database

## APIs

GET /employees  
GET /employees/{id}

### API Usage Examples

#### Get all employees

```bash
curl -X GET http://localhost:8080/employees
```

#### Get employee by ID

```bash
curl -X GET http://localhost:8080/employees/1
```

## Features

-   Dynamic filtering using Specifications
-   DTO-based responses
-   Clean layered architecture

## Project Setup

1. **Clone the repository**
    ```bash
    git clone https://github.com/Gauravjain007/emp-perf-management.git
    cd emp_perf_management
    ```
2. **Configure MySQL**
    - Create a database (e.g., `emp_perf_db`)
    - Update `src/main/resources/application.properties` with your DB credentials:
        ```
        spring.datasource.url=jdbc:mysql://localhost:3306/emp_perf_db
        spring.datasource.username=<your-username>
        spring.datasource.password=<your-password>
        ```
3. **Run DDLs and DMLs**

    - Execute the SQL scripts below to set up tables and seed data.

4. **Build and run the application**
    ```bash
    ./mvnw spring-boot:run
    ```
    or
    ```bash
    mvn spring-boot:run
    ```

## Testing

To run unit tests:

```bash
mvn test
```

## Environment Variables / Configuration

-   Database connection: Set in `src/main/resources/application.properties`

## DATABASE CHANGES

### DDLs

```sql
-- Create department table
CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `budget` decimal(15,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

-- Create employee table (references department and itself)
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `department_id` int DEFAULT NULL,
  `date_of_joining` date DEFAULT NULL,
  `salary` decimal(10,2) DEFAULT NULL,
  `manager_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `department_id_idx` (`department_id`),
  KEY `manager_id_idx` (`manager_id`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `employee_ibfk_2` FOREIGN KEY (`manager_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB;

-- Create project table (references department)
CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `department_id_idx` (`department_id`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB;

-- Create performance_review table (references employee)
CREATE TABLE `performance_review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` int NOT NULL,
  `review_date` date NOT NULL,
  `score` int DEFAULT NULL,
  `review_comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `review_date_idx` (`review_date` DESC),
  KEY `employee_id_idx` (`employee_id`),
  CONSTRAINT `performance_review_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB;

-- Create employee_project (many-to-many, references employee and project)
CREATE TABLE `employee_project` (
  `employee_id` int NOT NULL,
  `project_id` int NOT NULL,
  `assigned_date` date DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employee_id`,`project_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `employee_project_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `employee_project_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB;
```

### DMLs

```sql
INSERT INTO department (`name`, budget) VALUES
('IT', 4500000),
('HR', 1000000),
('Accounts', 30000000);


INSERT INTO employee(`name`, email, department_id, date_of_joining, salary, manager_id) VALUES
('Gaurav', 'gaurav@gmail.com', 1, NOW(), 200000, null),
('Hitesh', 'hitesh@gmail.com', 1, NOW(), 250000, null),
('Sayak', 'sayak@gmail.com', 3, NOW(), 100000, null),
('Hiren', 'hiren@gmail.com', 2, NOW(), 300000, null),
('Vijay', 'vijay@gmail.com', 3, NOW(), 120000, null);

INSERT INTO project(`name`, start_date, end_date, department_id) VALUES
('Treasury', NOW(), null, 1),
('Platform', '2025-01-01', '2025-10-01', 1),
('Payables', '2025-10-01', null, 1),
('Relations', NOW(), null, 2),
('Cash Accounting', '2025-05-01', null, 3),
('Payments', '2025-02-10', '2025-11-20', 3);

INSERT INTO employee_project (employee_id, project_id, assigned_date, `role`) VALUES
(1, 1, NOW(), 'Developer'),
(1, 2, NOW(), 'Developer'),
(2, 2, NOW(), 'Manager'),
(3, 5, NOW(), 'Accountant'),
(5, 5, NOW(), 'CA'),
(4, 4, NOW(), 'HR');

INSERT INTO performance_review (employee_id, review_date, score, review_comments) VALUES
(1, '2025-01-01', 8, 'Great'),
(1, '2025-06-01', 7, 'Good'),
(1, '2025-12-01', 9, null),
(2, '2025-01-01', 4, 'Avg'),
(3, '2025-06-01', 3, 'Bad'),
(3, '2025-12-01', 9, 'Nice');
```
