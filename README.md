# Voting App

A simple voting application built with Spring Boot, Thymeleaf, and MySQL.

## Description

This application allows users to register as voters, receive an OTP (One-Time Password) via email for verification, and cast their votes for one of two parties. The application also includes an admin panel to view the voting results in real-time.

## Features

- Voter registration with NID number, full name, and email.
- OTP verification via email.
- Voting functionality for two parties.
- Admin panel to view real-time voting results.
- Built with Spring Boot, Thymeleaf, and MySQL.

## Prerequisites

- Java 17
- Maven
- MySQL
- An email account for sending OTPs (e.g., Gmail)

## Setup

1. **Clone the repository:**

    ```sh
    git clone https://github.com/yourusername/voting-app.git
    cd voting-app
    ```

2. **Create the database:**

    ```sql
    CREATE DATABASE voting_app;
    ```

3. **Update the database configuration:**

    Edit the  file with your MySQL database credentials and email account details:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/voting_app
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    spring.mail.username=your_email@gmail.com
    spring.mail.password=your_email_password
    ```

4. **Build the project:**

    ```sh
    ./mvnw clean install
    ```

5. **Run the application:**

    ```sh
    ./mvnw spring-boot:run
    ```

6. **Access the application:**

    - Open your browser and go to `http://localhost:8080` to access the voter registration page.
    - Go to `http://localhost:8080/admin` to access the admin panel.

## Usage

1. **Register as a voter:**

    - Fill in your full name, NID number, and email address.
    - Click "Send OTP" to receive an OTP via email.
    - Enter the OTP and click "Verify OTP".
    - Once verified, you can proceed to vote.

2. **Cast your vote:**

    - Select the party you want to vote for and click the corresponding button.

3. **View voting results:**

    - Access the admin panel to view real-time voting results.

## License

This project is licensed under the Apache License 2.0. See the LICENSE file for details.