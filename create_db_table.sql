CREATE DATABASE voting_app;
USE voting_app;
CREATE TABLE Voter (
    nidNumber VARCHAR(255) PRIMARY KEY,
    fullName VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);