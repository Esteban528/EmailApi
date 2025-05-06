# Email API Rest Service

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Gmail](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)

#### Description

This service is an API REST that sends emails to recipients. It works in a very simple way, so anyone can use it. Using an HTTP client you send an HTTP request to the defined endpoint and the server will do the rest as you have configured in the `.env` file.

## Installation

### Dependencies

Make sure you have the following dependencies installed:

- **curl:** [https://curl.se/download.html](https://curl.se/download.html)
- **Docker:** [https://docs.docker.com/engine/install/](https://docs.docker.com/engine/install/)
- **Docker Compose:** [https://docs.docker.com/compose/install/linux/](https://docs.docker.com/compose/install/linux/)

### Install
```bash
curl -sSL https://raw.githubusercontent.com/Esteban528/EmailApi/refs/heads/main/install.sh | bash

cd ~/EmailApiSpring
```

## Settings

Create a `.env` file in the project directory.
```.env
AUTH_USERNAME=user
AUTH_PASSWORD=pass
PORT=8080
EMAIL_HOST=smtp.example.com
EMAIL_PORT=587
EMAIL_SENDER_NAME=Example Sender
EMAIL_SENDER_EMAIL=example@domain.com
EMAIL_USER=email_user
EMAIL_PASSWORD=email_password
EMAIL_AUTH=true
EMAIL_STARTTLS=true
EMAIL_PROTOCOL=smtp
EMAIL_STARTTLS_REQUIRED=true
```

## Email Sending API Documentation

This API allows sending emails using Spring Boot. It uses HTTP Basic Authentication to secure access.

### 1. Security

* **Authentication:** [HTTP Basic Authentication](https://en.wikipedia.org/wiki/Basic_access_authentication). A username and password are required to access any endpoint except the root `/`. These values are configured using the environment variables `var.username` and `var.password`.
* **Authorization:** Only authenticated users can access the email sending endpoint.

### 2. Endpoints

#### 2.1. Send Email

* **Method:** `POST`
* **URL:** `/email/send`
* **Description:** Sends an email.
* **Request Body:**
    ```json
    {
      "email": "recipient@example.com",
      "subject": "Email Subject",
      "message": "Message Body"
    }
    ```
    * `email` (String, required): Recipient's email address.
    * `subject` (String, required): Email subject.
    * `message` (String, required): Email message body.
* **Response (Success):**
    * **Status Code:** 200 OK
    * **Body:** `true`
* **Responses (Error):**
    * **Status Code:** 500 Internal Server Error
    * **Body:** `"Sending mail error " + e.getMessage()` (In case of an error sending the email, such as connection problems with the SMTP server).
    * **Status Code:** 400 Bad Request
    * **Body:** `"Encoding not supported " + e.getMessage()` (In case of character encoding problems).

#### 2.2. Root

* **Method:** `GET /`
* **URL:** `/`
* **Description:** This endpoint does not perform any specific action, but it is allowed without authentication. It can serve as a basic check that the application is running.
* **Response:** Depends on the configuration and is not part of the email sending logic.


### 3. DTOs (Data Transfer Objects)

* **EmailDTO:**
    * `email` (String): Recipient's email address.
    * `subject` (String): Email subject.
    * `message` (String): Email message body.

### 4. Configuration

* Security configuration (username and password) is done using the environment variables `var.username` and `var.password`.
* An `InMemoryUserDetailsManager` is used, which means that users are stored in memory. For a production environment, it is recommended to use a more robust user storage system.

### 5. Libraries Used

* Spring Boot
* Spring Security
* Spring Mail

### 6. Example usage with cURL

```bash
curl -X POST -u user:password -H "Content-Type: application/json" -d '{
  "email": "recipient@example.com",
  "subject": "Email Test",
  "message": "This is a test message."
}' http://localhost:8080/email/send
```

**Note:** Replace `user` and `password` with the configured credentials and `http://localhost:8080` with the correct URL of your application.

