package com.tuvarna.delivery.utils;

public interface Messages {
    String USER_SUCCESSFULLY_REGISTERED = "Your Account has been created successfully.";
    String PASSWORDS_DONT_MATCH = "Password don't match";
    String USER_NOT_FOUND_WITH_USERNAME = "User not found with username: ";
    String USER_EXISTS = "Username already exists!";
    String INVALID_JWT_TOKEN = "{\"message\": \"Invalid JWT Token\"}";
    String EXPIRED_JWT_TOKEN = "{\"message\": \"Session expired. You have been signed out\"}";
    String UNSUPPORTED_JWT_TOKEN = "{\"message\": \"Unsupported JWT Token\"}";
    String JWT_CLAIM_EMPTY = "{\"message\": \"JWT claim string is empty\"}";
    String INCORRECT_CREDENTIALS = "Incorrect email or password. Please try again.";
    String NOT_PERMITTED = "User has insufficient permissions to access";
}
