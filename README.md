# Spring Boot User Management

A RESTful user management API built with Spring Boot.

## Features

- Create a single user or batch create multiple users
- Get a user by ID
- Get all users
- Search a user by email
- Update a user
- Update user active status
- Delete a user
- Filter users by active status
- Request validation
- Duplicate email handling

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Jakarta Validation
- Lombok
- MySQL

## Project Structure

```text
controller/    — REST API endpoints
dto/           — Request and Response data transfer objects
entity/        — JPA entity classes
exception/     — Custom exception handling
repository/    — Data access layer
service/       — Business logic layer
```

## API Endpoints

| Method | URL                              | Description            |
|--------|----------------------------------|------------------------|
| POST   | `/api/users`                     | Create a user          |
| POST   | `/api/users/batch`               | Create multiple users  |
| GET    | `/api/users`                     | Get all users          |
| GET    | `/api/users/{id}`                | Get a user by ID       |
| GET    | `/api/users/search?email=`       | Search a user by email |
| GET    | `/api/users/status?status=`      | Filter users by status |
| PUT    | `/api/users/{id}`                | Update a user          |
| PATCH  | `/api/users/{id}/status?status=` | Update user status     |
| DELETE | `/api/users/{id}`                | Delete a user          |

## Request Body Example

**POST /api/users**

```json
{
  "fullName": "Jackie Zhan",
  "phone": "4021234567",
  "email": "jackie@example.com"
}
```

**POST /api/users/batch**

```json
[
  {
    "fullName": "Jackie Zhan",
    "phone": "4021234567",
    "email": "jackie@example.com"
  },
  {
    "fullName": "Matt Wang",
    "phone": "4029876543",
    "email": "matt@example.com"
  }
]
```
