package com.freitas.hubspot_consumer.exception;

public class ExceptionMessage {
    public static final String FAIL_CALL_BACK = "Failed to get access token. Please generate a new authorization and try again.";
    public static final String ERROR_CALL_BACK = "An unexpected error has occurred, please contact support.";
    public static final String WITHOUT_TOKEN = "It is necessary to generate an access token";
    public static final String FAIL_CREATE_CONTACT = "Failed to create contact. Please try again or contact support.";
    public static final String EMAIL_MANDATORY = "Email is mandatory";
    public static final String EMAIL_INVALID = "Email should be valid";
    public static final String FIRSTNAME_MANDATORY = "First name is mandatory";
    public static final String LASTNAME_MANDATORY = "Last name is mandatory";
    public static final String PHONE_INVALID = "Phone number is invalid. Example: (11)999998888";
}
